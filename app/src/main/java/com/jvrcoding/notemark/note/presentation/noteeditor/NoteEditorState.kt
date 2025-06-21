package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.ui.text.input.TextFieldValue
import com.jvrcoding.notemark.core.domain.note.NoteId

data class NoteEditorState(
    val id: NoteId = "",
    val title: TextFieldValue = TextFieldValue(),
    val content: TextFieldValue = TextFieldValue(),
    val isSavingNote: Boolean = false,
)
