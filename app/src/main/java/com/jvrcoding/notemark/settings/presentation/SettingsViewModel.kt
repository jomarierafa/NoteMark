package com.jvrcoding.notemark.settings.presentation

import androidx.lifecycle.ViewModel
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.settings.domain.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val noteRepository: NoteRepository,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage
): ViewModel() {

    fun onAction(action: SettingsAction) {
        when(action) {
            SettingsAction.OnLogoutClick -> logout()
            else -> Unit
        }
    }

    private fun logout() {
        applicationScope.launch {
            settingsRepository.logout()
            noteRepository.deleteAllNotes()
            sessionStorage.set(null)
        }
    }
}