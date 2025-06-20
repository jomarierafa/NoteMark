package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.presentation.util.asUiText

class NoteEditorViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    var state by mutableStateOf(NoteEditorState())
        private set

    private val eventChannel = Channel<NoteEditorEvent>()
    val events = eventChannel.receiveAsFlow()


    fun onAction(action: NoteEditorAction) {
        when(action) {
            NoteEditorAction.OnSaveNoteClick -> {
                saveNote()
            }
            is NoteEditorAction.OnTitleChanged -> {
                state = state.copy(title = action.value)
            }
            is NoteEditorAction.OnContentChanged -> {
                state = state.copy(content = action.value)
            }
            else -> Unit
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            state = state.copy(isSavingNote = true)
            val note = Note(
                id = null,
                title = state.title,
                content = state.content.text,
                createdAt = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC")),
                lastEditedAt = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC"))
            )

            when(val result = noteRepository.upsertNote(note)) {
                is Result.Error -> {
                    eventChannel.send(NoteEditorEvent.Error(result.error.asUiText()))
                }
                is Result.Success -> {
                    eventChannel.send(NoteEditorEvent.NoteSaved)
                }
            }

            state = state.copy(isSavingNote = false)
        }

    }

}