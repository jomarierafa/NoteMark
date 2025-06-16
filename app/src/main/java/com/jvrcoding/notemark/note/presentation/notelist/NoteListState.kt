package com.jvrcoding.notemark.note.presentation.notelist

import com.jvrcoding.notemark.note.presentation.notelist.model.NoteUi

data class NoteListState(
    val notes: List<NoteUi> = emptyList()
)