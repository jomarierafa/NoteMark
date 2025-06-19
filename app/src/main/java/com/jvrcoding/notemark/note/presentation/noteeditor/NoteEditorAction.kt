package com.jvrcoding.notemark.note.presentation.noteeditor

sealed interface NoteEditorAction {
    data object OnBackClick: NoteEditorAction
    data object OnSaveNoteClick: NoteEditorAction
}