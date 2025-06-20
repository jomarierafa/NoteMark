package com.jvrcoding.notemark.note.presentation.notelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.note.presentation.notelist.notemapper.toNoteUi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage
): ViewModel() {

    var state by mutableStateOf(NoteListState())
        private set


    init {
        noteRepository.getNotes().onEach { notes ->
            val notesUi = notes.map { it.toNoteUi() }
            state = state.copy(notes = notesUi)
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            noteRepository.fetchNotes()
        }
    }

    fun onAction(action: NoteListAction) {
        when(action) {
            is NoteListAction.DeleteNote -> {
                viewModelScope.launch {
                    state = state.copy(noteToDelete = null)
                    noteRepository.deleteNote(action.noteId)
                }
            }
            is NoteListAction.OnNoteLongPressed -> {
                state  = state.copy(noteToDelete = action.noteId)
            }
            NoteListAction.DismissDeleteDialog -> {
                state = state.copy(noteToDelete = null)
            }
            else -> Unit
        }
    }
}