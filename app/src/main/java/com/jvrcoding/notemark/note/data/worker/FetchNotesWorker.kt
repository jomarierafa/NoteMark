package com.jvrcoding.notemark.note.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jvrcoding.notemark.core.domain.note.NoteRepository

class FetchNotesWorker(
    context: Context,
    params: WorkerParameters,
    private val noteRepository: NoteRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if(runAttemptCount >= 5) {
            return Result.failure()
        }
        return when(val result = noteRepository.fetchNotes()) {
            is com.jvrcoding.notemark.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
            is com.jvrcoding.notemark.core.domain.util.Result.Success -> Result.success()
        }
    }
}