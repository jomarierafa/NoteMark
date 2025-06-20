package com.jvrcoding.notemark.note.data

import com.jvrcoding.notemark.core.domain.note.Note
import java.time.Instant
import java.time.ZoneId

fun NoteDto.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt)
            .atZone(ZoneId.of("UTC")),
        lastEditedAt = Instant.parse(lastEditedAt)
            .atZone(ZoneId.of("UTC"))
    )
}

fun Note.toUpsertNoteRequest(): UpsertNoteRequest {
    return UpsertNoteRequest(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt.toInstant().toString(),
        lastEditedAt = lastEditedAt.toInstant().toString()
    )
}