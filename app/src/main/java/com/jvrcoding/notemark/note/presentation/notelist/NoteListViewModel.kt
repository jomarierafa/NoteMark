package com.jvrcoding.notemark.note.presentation.notelist

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.Note
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.presentation.util.asUiText
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
    private val sessionStorage: SessionStorage,
    private val appContext: Context
): ViewModel() {

    var state by mutableStateOf(NoteListState())
        private set

    private val eventChannel = Channel<NoteListEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        observeNetworkStatus()

        noteRepository.getNotes().onEach { notes ->
            val notesUi = notes.map { it.toNoteUi() }
            state = state.copy(notes = notesUi.filter { it.content.isNotEmpty() })
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            sessionStorage.get()?.let {
                state = state.copy(username = it.username)
            }

            noteRepository.fetchNotes()
        }
    }

    private fun observeNetworkStatus() {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        state = state.copy(isOffline = !isConnected)

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                state = state.copy(isOffline = false)
            }

            override fun onLost(network: Network) {
                state = state.copy(isOffline = true)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
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
            else -> Unit
        }
    }


    private fun saveNote() {
        if(state.isAddingNote) return

        viewModelScope.launch {
            val noteId = UUID.randomUUID().toString()

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

            when (val result = noteRepository.createNote(note)) {
                is Result.Error -> {
                    eventChannel.send(NoteListEvent.Error(result.error.asUiText()))
                }
                is Result.Success -> {
                    eventChannel.send(NoteListEvent.NoteSaved(noteId))
                }
            }

            state = state.copy(isAddingNote = false)
        }

    }
}