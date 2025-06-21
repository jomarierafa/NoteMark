package com.jvrcoding.notemark.note.presentation.notelist

import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.note.presentation.notelist.model.NoteUi

data class NoteListState(
    val notes: List<NoteUi> = emptyList(),
    val isAddingNote: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val noteToDelete: NoteId? = null
)