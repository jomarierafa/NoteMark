package com.jvrcoding.notemark.core.domain.note

import kotlin.time.Duration

interface SyncNoteScheduler {
    suspend fun scheduleSync(type: SyncType)
    suspend fun cancelAllSyncs()

    sealed interface SyncType {
        data class FetchNotes(val interval: Duration): SyncType
        data class SyncNotes(val interval: Duration): SyncType
    }

}