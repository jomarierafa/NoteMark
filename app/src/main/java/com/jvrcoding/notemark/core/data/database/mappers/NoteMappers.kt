package com.jvrcoding.notemark.core.data.database.mappers

import com.jvrcoding.notemark.core.data.database.entity.NoteEntity
import com.jvrcoding.notemark.core.domain.note.Note
import java.time.Instant
import java.time.ZoneId
import java.util.UUID

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt)
            .atZone(ZoneId.of("UTC")),
        lastEditedAt = Instant.parse(lastEditAt)
            .atZone(ZoneId.of("UTC"))
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id ?: UUID.randomUUID().toString(),
        title = title,
        content = content,
        createdAt = createdAt.toInstant().toString(),
        lastEditAt = lastEditedAt.toInstant().toString()
    )
}