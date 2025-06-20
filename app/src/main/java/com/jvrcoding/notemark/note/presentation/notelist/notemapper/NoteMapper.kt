package com.jvrcoding.notemark.note.presentation.notelist.notemapper

import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.note.presentation.notelist.model.NoteUi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun Note.toNoteUi(): NoteUi {
    val now = ZonedDateTime.now(ZoneId.systemDefault())
    val isSameYear = createdAt.year == now.year

    val dateTimeInLocalTime = createdAt
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern(
            if(isSameYear) "dd MMM" else "dd MMM YYYY"
        )
        .format(dateTimeInLocalTime)

    return NoteUi(
        id = id!!,
        title = title,
        content = content,
        date = formattedDateTime,
    )
}