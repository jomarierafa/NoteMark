package com.jvrcoding.notemark.note.presentation.notelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jvrcoding.notemark.note.presentation.notelist.model.NoteUi


class NoteListViewModel: ViewModel() {

    var state by mutableStateOf(NoteListState())
        private set


    init {
        val dummyNotes: List<NoteUi> = List(10) { index ->
            NoteUi(
                id = index.toString(),
                title = "Note Title $index",
                label = "This is the content of note number $index. It might contain a preview or description.",
                date = "23 APR"
            )
        }

        state = state.copy(notes = dummyNotes)
    }
}