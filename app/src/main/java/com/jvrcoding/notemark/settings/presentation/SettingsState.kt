package com.jvrcoding.notemark.settings.presentation


data class SettingsState(
    val syncInterval: SyncInterval = SyncInterval.Manual,
    val lastSync: String = ""
)

sealed class SyncInterval(val label: String) {
    data object Manual : SyncInterval("Manual only")
    data object FifteenMinutes : SyncInterval("15 minutes")
    data object ThirtyMinutes : SyncInterval("30 minutes")
    data object OneHour : SyncInterval("1 hour")

    companion object {
        fun fromValue(label: String?): SyncInterval = when (label) {
            Manual.label -> Manual
            FifteenMinutes.label -> FifteenMinutes
            ThirtyMinutes.label -> ThirtyMinutes
            OneHour.label -> OneHour
            else -> Manual
        }
    }
}