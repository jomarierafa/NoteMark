package com.jvrcoding.notemark.settings.presentation

import com.jvrcoding.notemark.core.presentation.util.UiText

sealed interface SettingsEvent {
    data class Error(val error: UiText): SettingsEvent
    data object OnSuccessfulLogout : SettingsEvent
}
