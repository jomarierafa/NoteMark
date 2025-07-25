package com.jvrcoding.notemark.settings.presentation

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.hours


data class SettingsState(
    val syncInterval: SyncInterval = SyncInterval.Manual,
    val lastSync: String = "",
    val isSyncing: Boolean = false
) {
    val duration: Duration
        get() = when (syncInterval) {
            SyncInterval.FifteenMinutes -> 15.minutes
            SyncInterval.ThirtyMinutes -> 30.minutes
            SyncInterval.OneHour -> 1.hours
            else -> 15.minutes
        }

}

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