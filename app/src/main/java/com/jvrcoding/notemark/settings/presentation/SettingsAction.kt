package com.jvrcoding.notemark.settings.presentation

sealed interface SettingsAction {
    data object OnBackClick: SettingsAction
    data object OnLogoutClick: SettingsAction
}