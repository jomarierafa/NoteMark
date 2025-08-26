package com.jvrcoding.notemark.core.data.note

import com.jvrcoding.notemark.core.data.database.dao.NotePendingSyncDao
import com.jvrcoding.notemark.core.data.database.entity.NotePendingSyncEntity
import com.jvrcoding.notemark.core.data.database.entity.SyncOperationType
import com.jvrcoding.notemark.core.data.database.mappers.toNote
import com.jvrcoding.notemark.core.data.database.mappers.toNoteEntity
import com.jvrcoding.notemark.core.domain.SessionStorage
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime

class NoteRepositoryImpl(
    private val localNoteDataSource: LocalNoteDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val applicationScope: CoroutineScope,
    private val notePendingSyncDao: NotePendingSyncDao,
    private val sessionStorage: SessionStorage
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
                notePendingSyncDao.upsertNotePendingSyncEntity(
                    NotePendingSyncEntity(
                        note = note.toNoteEntity(),
                        username = sessionStorage.get()?.username ?: "",
                        operationType = SyncOperationType.CREATE
                    )
                )
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
                val syncOperation = notePendingSyncDao
                    .getNotePendingSyncEntity(note.id!!)?.operationType ?: SyncOperationType.UPDATE
                notePendingSyncDao.upsertNotePendingSyncEntity(
                    NotePendingSyncEntity(
                        note = note.toNoteEntity(),
                        username = sessionStorage.get()?.username ?: "",
                        operationType = syncOperation
                    )
                )
                return@async Result.Success(Unit)
            }

            return@async remoteResult.asEmptyDataResult()
        }.await()
    }

    override suspend fun deleteNote(id: NoteId) {
        localNoteDataSource.deleteNote(id)

        val pendingSyncItem = notePendingSyncDao.getNotePendingSyncEntity(id)
        if((pendingSyncItem != null) && pendingSyncItem.operationType == SyncOperationType.CREATE) {
            notePendingSyncDao.deleteNotePendingSyncEntity(id)
            return
        }

        applicationScope.async {
            val remoteResult =  remoteNoteDataSource.deleteNote(id)
            if(remoteResult is Result.Error) {
                val now = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC"))
                notePendingSyncDao.upsertNotePendingSyncEntity(
                    NotePendingSyncEntity(
                        note = Note(
                            id = id,
                            title = "",
                            content = "",
                            createdAt = now,
                            lastEditedAt = now
                        ).toNoteEntity(),
                        username = sessionStorage.get()?.username ?: "",
                        operationType = SyncOperationType.DELETE
                    )
                )
                return@async
            }
            notePendingSyncDao.deleteNotePendingSyncEntity(id)
        }.join()

    }



    override suspend fun deleteAllNotes() {
        localNoteDataSource.deleteAllNotes()
    }

    override suspend fun getPendingNoteCount(): Int {
        val username = sessionStorage.get()?.username ?: return 0
        return notePendingSyncDao.getPendingNoteCount(username)
    }

    override suspend fun syncPendingNotes() {
        withContext(Dispatchers.IO) {
            val username = sessionStorage.get()?.username ?: return@withContext

            val createdNotes = async {
                notePendingSyncDao.getAllNotePendingSyncEntities(username, SyncOperationType.CREATE)
            }
            val updatedNotes = async {
                notePendingSyncDao.getAllNotePendingSyncEntities(username, SyncOperationType.UPDATE)
            }
            val deletedNotes = async {
                notePendingSyncDao.getAllNotePendingSyncEntities(username, SyncOperationType.DELETE)
            }

            val createJobs = createdNotes
                .await()
                .map {
                    launch {
                        val note = it.note.toNote()
                        when(remoteNoteDataSource.postNote(note)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    notePendingSyncDao.deleteNotePendingSyncEntity(it.noteId)
                                }.join()
                            }
                        }
                    }
                }

            val updatedJobs = updatedNotes
                .await()
                .map {
                    launch {
                        val note = it.note.toNote()
                        when(val result = remoteNoteDataSource.putNote(note)) {
                            is Result.Error -> {
                                if(result.error == DataError.Network.NOT_FOUND) {
                                    applicationScope.launch {
                                        notePendingSyncDao.deleteNotePendingSyncEntity(it.noteId)
                                    }.join()
                                }
                            }
                            is Result.Success -> {
                                applicationScope.launch {
                                    notePendingSyncDao.deleteNotePendingSyncEntity(it.noteId)
                                }.join()
                            }
                        }
                    }
                }

            val deleteJobs = deletedNotes
                .await()
                .map {
                    launch {
                        when(remoteNoteDataSource.deleteNote(it.noteId)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    notePendingSyncDao.deleteNotePendingSyncEntity(it.noteId)
                                }.join()
                            }
                        }
                    }
                }

            createJobs.forEach { it.join() }
            updatedJobs.forEach { it.join() }
            deleteJobs.forEach { it.join() }
        }
    }

}