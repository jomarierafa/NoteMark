package com.jvrcoding.notemark.settings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvrcoding.notemark.R
import com.jvrcoding.notemark.core.common.DataStoreKeys
import com.jvrcoding.notemark.core.domain.DataStoreRepository
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.presentation.util.UiText
import com.jvrcoding.notemark.core.presentation.util.asUiText
import com.jvrcoding.notemark.core.presentation.util.toSyncStatusMessage
import com.jvrcoding.notemark.settings.domain.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val noteRepository: NoteRepository,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage,
    private val dataStoreRepository: DataStoreRepository,
): ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when(action) {
            SettingsAction.OnLogoutClick -> logout()
            SettingsAction.OnSyncDataClick -> syncData()
            is SettingsAction.OnSyncIntervalChange -> {
                viewModelScope.launch {
                    state = state.copy(
                        syncInterval = action.syncInterval
                    )
                    dataStoreRepository.putString(DataStoreKeys.SYNC_INTERVAL, action.syncInterval.label)
                }
            }
            else -> Unit
        }
    }

    init {
        viewModelScope.launch {
            val syncInterval = dataStoreRepository.getString(DataStoreKeys.SYNC_INTERVAL)
            val lastSync = dataStoreRepository.getString(DataStoreKeys.LAST_SYNC)
            state = state.copy(
                syncInterval =  SyncInterval.fromValue(syncInterval),
                lastSync = "Last sync: ${lastSync.toSyncStatusMessage()}"
            )
        }
    }

    private fun syncData() {
        viewModelScope.launch {

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