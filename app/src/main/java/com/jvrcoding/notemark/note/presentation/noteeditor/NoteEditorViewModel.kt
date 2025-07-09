package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.NoteId
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.presentation.util.asUiText
import com.jvrcoding.notemark.note.presentation.noteeditor.componets.FabOption
import kotlinx.coroutines.delay

class NoteEditorViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var state by mutableStateOf(NoteEditorState())
        private set

    private val eventChannel = Channel<NoteEditorEvent>()
    val events = eventChannel.receiveAsFlow()

    private var initialTitleValue: String = ""
    private var initialContentValue: String = ""


    fun onAction(action: NoteEditorAction) {
        when (action) {
            is NoteEditorAction.OnSelectFabOption -> {
                val option = action.option
                state = state.copy(
                    selectedFabOption = option
                )



                if (option != null) {
                    viewModelScope.launch {
                        if(option == FabOption.Book)
                            delay(300) // wait for screen rotate

                        state = state.copy(isAdditionalUiVisible = false)
                    }
                }

            }
            is NoteEditorAction.GetNote -> {
                getNote(action.id)
            }
            is NoteEditorAction.OnSaveNoteClick -> {
                saveNote(state.id)
            }
            is NoteEditorAction.OnTitleChanged -> {
                state = state.copy(title = action.value)
            }
            is NoteEditorAction.OnContentChanged -> {
                state = state.copy(content = action.value)
            }
            NoteEditorAction.OnNavIconClick -> {
                handleNavIconClick()
            }
            NoteEditorAction.OnDiscardClick -> {
                viewModelScope.launch {
                    state = state.copy(showDiscardDialog = false)
                    eventChannel.send(NoteEditorEvent.DiscardChanges)
                }
            }
            NoteEditorAction.OnKeepEditingClick -> {
                state = state.copy(showDiscardDialog = false)
            }

            NoteEditorAction.OnSurfaceTap -> {
                if(state.screenMode == ScreenMode.READ) {
                    state = state.copy(isAdditionalUiVisible = !state.isAdditionalUiVisible)
                }
            }
        }
    }

    private fun getNote(id: NoteId) {
        viewModelScope.launch {
            noteRepository.getNote(id).let { note ->
                state = state.copy(
                    id = id,
                    title = TextFieldValue(
                        text = note.title,
                        selection = TextRange(note.title.length)
                    ),
                    dateCreated = note.createdAt,
                    lastEdited = note.lastEditedAt,
                    content = TextFieldValue(text = note.content)
                )
                initialTitleValue = note.title
                initialContentValue = note.content
            }
        }
    }

    private fun saveNote(id: NoteId) {
        viewModelScope.launch {
            state = state.copy(isSavingNote = true)
            val note = Note(
                id = id,
                title = state.title.text,
                content = state.content.text,
                createdAt = state.dateCreated,
                lastEditedAt = ZonedDateTime.now()
                    .withZoneSameInstant(ZoneId.of("UTC"))
            )

            when (val result = noteRepository.updateNote(note)) {
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

    private fun handleNavIconClick() {
        if(state.screenMode == ScreenMode.EDIT) {
            state = state.copy(
                selectedFabOption = null,
                isAdditionalUiVisible = true
            )
            return
        }

        viewModelScope.launch {
            if(initialTitleValue == state.title.text
                && initialContentValue == state.content.text
            ) {
                eventChannel.send(NoteEditorEvent.DiscardChanges)
//                        noteRepository.deleteNote(state.id)
            } else {
                state = state.copy(showDiscardDialog = true)
            }
        }
    }

}