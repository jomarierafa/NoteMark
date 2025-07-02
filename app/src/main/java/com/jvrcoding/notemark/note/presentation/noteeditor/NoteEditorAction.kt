package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.ui.text.input.TextFieldValue
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.note.presentation.noteeditor.componets.FabOption


sealed interface NoteEditorAction {
    data object OnSaveNoteClick: NoteEditorAction
    data class OnSelectFabOption(val option: FabOption?) : NoteEditorAction
    data class OnTitleChanged(val value: TextFieldValue): NoteEditorAction
    data class OnContentChanged(val value: TextFieldValue): NoteEditorAction
    data class GetNote(val id: NoteId): NoteEditorAction
    data object OnBackClick: NoteEditorAction
    data object OnKeepEditingClick: NoteEditorAction
    data object OnDiscardClick: NoteEditorAction
}