package com.jvrcoding.notemark.note.presentation.noteeditor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class NoteEditorViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(NoteEditorState())
        private set

    private val eventChannel = Channel<NoteEditorEvent>()
    val events = eventChannel.receiveAsFlow()

    private var initialTitleValue: String = ""
    private var initialContentValue: String = ""

    private var hideJob: Job? = null

    private val noteId: String = savedStateHandle["id"] ?: ""
    private val isNewNote: Boolean = savedStateHandle["isNewNote"] ?: false

    init {
        state = state.copy(
            selectedFabOption = if(isNewNote)  FabOption.Pen else null,
            isAdditionalUiVisible = !isNewNote
        )
        getNote(noteId)
    }

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
                            delay(5000)

                        state = state.copy(isAdditionalUiVisible = false)
                    }
                }

            }
            NoteEditorAction.OnSaveNoteClick -> {
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
                state = state.copy(
                    showDiscardDialog = false,
                    selectedFabOption = null,
                    isAdditionalUiVisible = true
                )
            }
            NoteEditorAction.OnKeepEditingClick -> {
                state = state.copy(showDiscardDialog = false)
            }
            NoteEditorAction.OnSurfaceTap -> {
                if(state.screenMode == ScreenMode.READ) {
                    state = state.copy(isAdditionalUiVisible = !state.isAdditionalUiVisible)
                    startHideTimer()
                }
            }
            NoteEditorAction.OnStartScrolling -> {
                if(state.screenMode == ScreenMode.READ) {
                    state = state.copy(isAdditionalUiVisible = false)
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
                    state = state.copy(
                        selectedFabOption = null,
                        isAdditionalUiVisible = true,
                        lastEdited = ZonedDateTime.now()
                    )
                }
            }

            state = state.copy(isSavingNote = false)
        }
    }

    private fun handleNavIconClick() {
        if(state.screenMode == ScreenMode.EDIT) {
            state = if(initialTitleValue == state.title.text
                && initialContentValue == state.content.text
            ) {
                state.copy(
                    selectedFabOption = null,
                    isAdditionalUiVisible = true
                )
            } else {
                state.copy(showDiscardDialog = true)
            }
            return
        }

        viewModelScope.launch {
            // TODO(reusing DiscardChanges for exiting the note detail screen XD)
            eventChannel.send(NoteEditorEvent.ExitScreen)
        }
    }


    private fun startHideTimer() {
        hideJob?.cancel()
        hideJob = null
        hideJob = viewModelScope.launch {
            delay(5000)
            state = state.copy(isAdditionalUiVisible = false)
        }
    }
}