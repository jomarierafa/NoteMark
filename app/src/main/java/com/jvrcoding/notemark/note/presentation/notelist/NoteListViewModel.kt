package com.jvrcoding.notemark.note.presentation.notelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.note.presentation.notelist.notemapper.toNoteUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID


class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage
): ViewModel() {

    var state by mutableStateOf(NoteListState())
        private set

    private val eventChannel = Channel<NoteListEvent>()
    val events = eventChannel.receiveAsFlow()


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
                    noteRepository.deleteNote(action.id)
                }
            }
            NoteListAction.OnAddButtonClick -> {
                saveNote()
            }
            is NoteListAction.OnNoteLongPressed -> {
                state  = state.copy(noteToDelete = action.id)
            }
            NoteListAction.DismissDeleteDialog -> {
                state = state.copy(noteToDelete = null)
            }
        }
    }


    private fun saveNote() {
        viewModelScope.launch {

            val noteId = UUID.randomUUID().toString()
            eventChannel.send(NoteListEvent.NoteSaved(noteId))

            // adding delay here to avoid flash of empty note
//            delay(500)

            state = state.copy(isAddingNote = true)
            val note = Note(
                id = noteId,
                title = "New Note",
                content = "",
                createdAt = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC")),
                lastEditedAt = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC"))
            )

            noteRepository.createNote(note)
            state = state.copy(isAddingNote = false)
        }

    }
}