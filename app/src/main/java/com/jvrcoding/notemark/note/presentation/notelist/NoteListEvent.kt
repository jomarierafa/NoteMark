package com.jvrcoding.notemark.note.presentation.notelist

import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.core.presentation.util.UiText

sealed interface NoteListEvent {
    data class Error(val error: UiText): NoteListEvent
    data class NoteSaved(val noteId: NoteId): NoteListEvent
}