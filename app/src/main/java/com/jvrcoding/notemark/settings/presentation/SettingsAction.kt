package com.jvrcoding.notemark.settings.presentation

sealed interface SettingsAction {
    data object OnBackClick: SettingsAction
    data object OnLogoutClick: SettingsAction
    data object OnSyncDataClick: SettingsAction
    data class OnSyncIntervalChange(val syncInterval: SyncInterval): SettingsAction

}