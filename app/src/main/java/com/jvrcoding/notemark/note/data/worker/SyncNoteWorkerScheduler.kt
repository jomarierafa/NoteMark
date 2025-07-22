package com.jvrcoding.notemark.note.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import androidx.work.await
import com.jvrcoding.notemark.core.domain.note.SyncNoteScheduler
import kotlin.time.Duration

class SyncNoteWorkerScheduler(
    private val context: Context,
): SyncNoteScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(type: SyncNoteScheduler.SyncType) {
        when(type) {
            is SyncNoteScheduler.SyncType.FetchNotes -> scheduleFetchNotesWorker(type.interval)
            is SyncNoteScheduler.SyncType.SyncNotes -> scheduleSyncWorkers(type.interval)
        }
    }

    private suspend fun scheduleFetchNotesWorker(interval: Duration) {
        val workRequest = buildPeriodicWorker<FetchNotesWorker>(interval, "fetch_work")

        workManager.enqueueUniquePeriodicWork(
            "periodic_fetch_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        ).await()
    }

    private suspend fun scheduleSyncWorkers(interval: Duration) {
        val syncNoteWorker = buildPeriodicWorker<SyncNoteWorker>(interval, "sync_work")
        workManager.enqueueUniquePeriodicWork(
            "periodic_sync_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            syncNoteWorker
        ).await()
    }


    override suspend fun cancelAllSyncs() {
        WorkManager.getInstance(context)
            .cancelAllWork()
            .await()
    }
}