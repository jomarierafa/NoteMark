package com.jvrcoding.notemark.core.domain.note

import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>
    suspend fun fetchNotes(): EmptyResult<DataError>
    suspend fun upsertNote(note: Note): EmptyResult<DataError>
    suspend fun deleteNote(id: NoteId)
    suspend fun deleteAllNotes()
}