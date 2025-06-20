package com.jvrcoding.notemark.note.presentation.notelist

import com.jvrcoding.notemark.core.domain.note.NoteId


sealed interface NoteListAction {
    data object OnAddButtonClick: NoteListAction
    data class DeleteNote(val noteId: NoteId): NoteListAction
    data class OnNoteLongPressed(val noteId: NoteId) : NoteListAction
    data object DismissDeleteDialog : NoteListAction
}