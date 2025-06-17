package com.jvrcoding.notemark.note.presentation.note_editor

sealed interface NoteEditorAction {
    data object OnBackClick: NoteEditorAction
    data object OnSaveNoteClick: NoteEditorAction
}