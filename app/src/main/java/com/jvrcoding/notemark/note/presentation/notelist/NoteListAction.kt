package com.jvrcoding.notemark.note.presentation.notelist

import com.jvrcoding.notemark.core.domain.note.NoteId


sealed interface NoteListAction {
    data object OnAddButtonClick: NoteListAction
    data class OnTapNote(val id: NoteId): NoteListAction
    data class DeleteNote(val id: NoteId): NoteListAction
    data class OnNoteLongPressed(val id: NoteId) : NoteListAction
    data object DismissDeleteDialog : NoteListAction
}