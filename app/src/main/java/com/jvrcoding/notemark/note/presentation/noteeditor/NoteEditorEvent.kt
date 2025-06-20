package com.jvrcoding.notemark.note.presentation.noteeditor

import com.jvrcoding.notemark.core.presentation.util.UiText

sealed interface NoteEditorEvent {
    data class Error(val error: UiText): NoteEditorEvent
    data object NoteSaved: NoteEditorEvent
}