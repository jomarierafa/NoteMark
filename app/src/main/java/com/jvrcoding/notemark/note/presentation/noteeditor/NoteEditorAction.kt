package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.ui.text.input.TextFieldValue
import com.jvrcoding.notemark.core.domain.note.NoteId


sealed interface NoteEditorAction {
    data object OnSaveNoteClick: NoteEditorAction
    data class OnTitleChanged(val value: TextFieldValue) : NoteEditorAction
    data class OnContentChanged(val value: TextFieldValue) : NoteEditorAction
    data class GetNote(val id: NoteId): NoteEditorAction
    data object OnBackClick: NoteEditorAction
}