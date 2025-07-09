package com.jvrcoding.notemark.core.navigation

import com.jvrcoding.notemark.core.domain.note.NoteId
import kotlinx.serialization.Serializable

@Serializable
object Landing

@Serializable
object Login

@Serializable
object Register

@Serializable
object NoteList

@Serializable
object Settings

@Serializable
data class NoteEditor(
    val id: NoteId = "",
    val isNewNote: Boolean = false,
)