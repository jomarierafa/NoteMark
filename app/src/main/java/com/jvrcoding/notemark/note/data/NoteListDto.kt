package com.jvrcoding.notemark.note.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteListDto(
    val notes: List<NoteDto>,
    val total: Int
)
