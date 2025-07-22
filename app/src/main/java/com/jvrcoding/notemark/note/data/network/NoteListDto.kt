package com.jvrcoding.notemark.note.data.network

import kotlinx.serialization.Serializable

@Serializable
data class NoteListDto(
    val notes: List<NoteDto>,
    val total: Int
)
