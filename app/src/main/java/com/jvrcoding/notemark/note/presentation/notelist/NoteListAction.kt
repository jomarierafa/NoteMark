package com.jvrcoding.notemark.note.presentation.notelist


sealed interface NoteListAction {
    data object OnAddButtonClick: NoteListAction
    data class DeleteNote(val noteId: Int): NoteListAction
}