package com.jvrcoding.notemark.core.domain.note

import java.time.ZonedDateTime

data class Note(
    val id: String?,
    val title: String,
    val content: String,
    val createdAt: ZonedDateTime,
    val lastEditedAt: ZonedDateTime
)
