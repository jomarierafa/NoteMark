package com.jvrcoding.notemark.note.data.worker

import com.jvrcoding.notemark.core.domain.util.DataError
import androidx.work.ListenableWorker.Result

fun DataError.toWorkerResult(): Result {
    return when(this) {
        DataError.Local.DISK_FULL -> Result.failure()
        DataError.Network.REQUEST_TIMEOUT -> Result.retry()
        DataError.Network.UNAUTHORIZED -> Result.retry()
        DataError.Network.NOT_FOUND -> Result.failure()
        DataError.Network.CONFLICT -> Result.retry()
        DataError.Network.TOO_MANY_REQUEST -> Result.retry()
        DataError.Network.NO_INTERNET -> Result.retry()
        DataError.Network.PAYLOAD_TOO_LARGE -> Result.failure()
        DataError.Network.SERVER_ERROR -> Result.retry()
        DataError.Network.SERIALIZATION -> Result.failure()
        DataError.Network.UNKNOWN -> Result.failure()
    }
}