package com.jvrcoding.notemark.note.data.worker

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

inline fun <reified T : ListenableWorker> buildPeriodicWorker(
    interval: Duration,
    tag: String,
    initialDelayMinutes: Long = 30
): PeriodicWorkRequest {
    return PeriodicWorkRequestBuilder<T>(interval.toJavaDuration())
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .setBackoffCriteria(
            BackoffPolicy.EXPONENTIAL,
            2000L,
            TimeUnit.MILLISECONDS
        )
        .setInitialDelay(initialDelayMinutes, TimeUnit.MINUTES)
        .addTag(tag)
        .build()
}