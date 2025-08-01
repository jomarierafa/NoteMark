package com.jvrcoding.notemark.note.data.network

import kotlinx.serialization.Serializable

@Serializable
data class UpsertNoteRequest(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)