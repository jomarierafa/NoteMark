package com.jvrcoding.notemark.core.domain.note

import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.domain.util.EmptyResult

interface RemoteNoteDataSource {
    suspend fun getNotes(): Result<List<Note>, DataError.Network>
    suspend fun postNote(note: Note): Result<Note, DataError.Network>
    suspend fun putNote(note: Note): Result<Note, DataError.Network>
    suspend fun deleteNote(id: String): EmptyResult<DataError.Network>
}