package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.ui.text.input.TextFieldValue


sealed interface NoteEditorAction {
    data object OnBackClick: NoteEditorAction
    data object OnSaveNoteClick: NoteEditorAction
    data class OnTitleChanged(val value: String) : NoteEditorAction
    data class OnContentChanged(val value: TextFieldValue) : NoteEditorAction
}