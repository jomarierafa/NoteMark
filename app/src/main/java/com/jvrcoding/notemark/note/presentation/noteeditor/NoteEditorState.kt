package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.ui.text.input.TextFieldValue

data class NoteEditorState(
    val title: String = "",
    val content: TextFieldValue = TextFieldValue(),
    val isSavingNote: Boolean = false,
)
