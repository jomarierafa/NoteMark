package com.jvrcoding.notemark.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.presentation.util.UiText
import com.jvrcoding.notemark.core.presentation.util.asUiText
import com.jvrcoding.notemark.settings.domain.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val noteRepository: NoteRepository,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage
): ViewModel() {

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when(action) {
            SettingsAction.OnLogoutClick -> logout()
            else -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch {
            when (val result = settingsRepository.logout()) {
                is Result.Error -> {
                    if(result.error == DataError.Network.NO_INTERNET) {
                        eventChannel.send(SettingsEvent.Error(
                            UiText.StringResource(R.string.error_no_internet_logout)
                        ))
                    } else {
                        eventChannel.send(SettingsEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    applicationScope.launch {
                        noteRepository.deleteAllNotes()
                        sessionStorage.set(null)
                    }
                    eventChannel.send(SettingsEvent.OnSuccessfulLogout)
                }
            }
        }
    }
}