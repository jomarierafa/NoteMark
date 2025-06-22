package com.jvrcoding.notemark.note.data

import com.jvrcoding.notemark.core.data.networking.delete
import com.jvrcoding.notemark.core.data.networking.get
import com.jvrcoding.notemark.core.data.networking.post
import com.jvrcoding.notemark.core.data.networking.put
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.RemoteNoteDataSource
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.EmptyResult
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.domain.util.map
import io.ktor.client.HttpClient

class KtorRemoteNoteDataSource(
    private val httpClient: HttpClient
): RemoteNoteDataSource {
    override suspend fun getNotes(): Result<List<Note>, DataError.Network> {
        return httpClient.get<NoteListDto>(
            route = "/api/notes",
            queryParameters = mapOf(
                "page" to -1
            )
        ).map { noteDto ->
            noteDto.notes.map { it.toNote() }
        }
    }

    override suspend fun postNote(note: Note): Result<Note, DataError.Network> {
        return httpClient.post<UpsertNoteRequest, NoteDto>(
            route = "/api/notes",
            body = note.toUpsertNoteRequest()
        ).map { noteDto ->
            noteDto.toNote()
        }
    }

    override suspend fun putNote(note: Note): Result<Note, DataError.Network> {
        return httpClient.put<UpsertNoteRequest, NoteDto>(
            route = "/api/notes",
            body = note.toUpsertNoteRequest()
        ).map { noteDto ->
            noteDto.toNote()
        }
    }

    override suspend fun deleteNote(id: String): EmptyResult<DataError.Network> {
        return httpClient.delete(
            route = "/api/notes/${id}"
        )
    }
}