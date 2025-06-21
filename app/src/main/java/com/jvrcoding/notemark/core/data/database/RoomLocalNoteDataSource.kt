package com.jvrcoding.notemark.core.data.database

import android.database.sqlite.SQLiteFullException
import com.jvrcoding.notemark.core.data.database.dao.NoteDao
import com.jvrcoding.notemark.core.data.database.mappers.toNote
import com.jvrcoding.notemark.core.data.database.mappers.toNoteEntity
import com.jvrcoding.notemark.core.domain.note.LocalNoteDataSource
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalNoteDataSource(
    private val noteDao: NoteDao
): LocalNoteDataSource {

    override suspend fun getNote(id: NoteId): Note {
        return noteDao.getNote(id).toNote()
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
            .map { noteEntities ->
                noteEntities.map { it.toNote() }
            }
    }

    override suspend fun upsertNote(note: Note): Result<NoteId, DataError.Local> {
        return try {
            val entity = note.toNoteEntity()
            noteDao.upsertNote(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertNotes(notes: List<Note>): Result<List<NoteId>, DataError.Local> {
        return try {
            val entities = notes.map { it.toNoteEntity() }
            noteDao.upsertNotes(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(id: NoteId) {
        noteDao.deleteNote(id)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}