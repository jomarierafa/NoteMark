package com.jvrcoding.notemark.core.domain.note

import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getNote(id: NoteId): Note
    fun getNotes(): Flow<List<Note>>
    suspend fun fetchNotes(): EmptyResult<DataError>
    suspend fun createNote(note: Note): EmptyResult<DataError>
    suspend fun updateNote(note: Note): EmptyResult<DataError>
    suspend fun deleteNote(id: NoteId)
    suspend fun deleteAllNotes()
}