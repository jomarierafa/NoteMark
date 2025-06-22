package com.jvrcoding.notemark.core.data.note

import com.jvrcoding.notemark.core.domain.note.LocalNoteDataSource
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.core.domain.note.RemoteNoteDataSource
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.domain.util.EmptyResult
import com.jvrcoding.notemark.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val localNoteDataSource: LocalNoteDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val applicationScope: CoroutineScope
): NoteRepository {

    override suspend fun getNote(id: NoteId): Note {
        return localNoteDataSource.getNote(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return localNoteDataSource.getNotes()
    }

    override suspend fun fetchNotes(): EmptyResult<DataError> {
        return when(val result = remoteNoteDataSource.getNotes()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localNoteDataSource.upsertNotes(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun createNote(note: Note): EmptyResult<DataError> {
        val localResult = localNoteDataSource.upsertNote(note)
        if(localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        return applicationScope.async {
            val remoteResult = remoteNoteDataSource.postNote(note = note)
            if(remoteResult is Result.Error) {
                return@async Result.Success(Unit)
            }

            return@async remoteResult.asEmptyDataResult()
        }.await()
    }

    override suspend fun updateNote(note: Note): EmptyResult<DataError> {
        val localResult = localNoteDataSource.upsertNote(note)
        if(localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        return applicationScope.async {
            val noteWithId = note.copy(id = localResult.data)
            val remoteResult = remoteNoteDataSource.putNote( note = noteWithId)
            if(remoteResult is Result.Error) {
                return@async Result.Success(Unit)
            }

            return@async remoteResult.asEmptyDataResult()
        }.await()
    }

    override suspend fun deleteNote(id: NoteId) {
        localNoteDataSource.deleteNote(id)
        val remoteResult = applicationScope.async {
            remoteNoteDataSource.deleteNote(id)
        }.await()

        if(remoteResult is Result.Error) {
            // TODO(handle error here)
        }
    }

    override suspend fun deleteAllNotes() {
        localNoteDataSource.deleteAllNotes()
    }

}