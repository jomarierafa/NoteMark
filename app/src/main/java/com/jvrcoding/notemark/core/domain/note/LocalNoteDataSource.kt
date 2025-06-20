package com.jvrcoding.notemark.core.domain.note

import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias NoteId = String

interface LocalNoteDataSource {

    fun getNotes(): Flow<List<Note>>
    suspend fun upsertNote(note: Note):Result<NoteId, DataError.Local>
    suspend fun upsertNotes(notes: List<Note>): Result<List<NoteId>, DataError.Local>
    suspend fun deleteNote(id: String)
    suspend fun deleteAllNotes()
}