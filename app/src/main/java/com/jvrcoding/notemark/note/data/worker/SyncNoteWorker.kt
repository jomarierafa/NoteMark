package com.jvrcoding.notemark.note.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jvrcoding.notemark.core.common.DataStoreKeys
import com.jvrcoding.notemark.core.data.database.dao.NotePendingSyncDao
import com.jvrcoding.notemark.core.data.database.entity.SyncOperationType
import com.jvrcoding.notemark.core.data.database.mappers.toNote
import com.jvrcoding.notemark.core.domain.DataStoreRepository
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.RemoteNoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime

class SyncNoteWorker(
    context: Context,
    params: WorkerParameters,
    private val notePendingSyncDao: NotePendingSyncDao,
    private val noteRunDataSource: RemoteNoteDataSource,
    private val sessionStorage: SessionStorage,
    private val dataStoreRepository: DataStoreRepository,
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        if (runAttemptCount >= 5) return@withContext Result.failure()

        try {
            coroutineScope {
                val username = sessionStorage.get()?.username ?: return@coroutineScope Result.success()

                val createdNotes = async {
                    notePendingSyncDao.getAllNotePendingSyncEntities(username, SyncOperationType.CREATE)
                }
                val updatedNotes = async {
                    notePendingSyncDao.getAllNotePendingSyncEntities(username, SyncOperationType.UPDATE)
                }
                val deletedNotes = async {
                    notePendingSyncDao.getAllNotePendingSyncEntities(username, SyncOperationType.DELETE)
                }

                val createJobs = createdNotes.await().map { entity ->
                    launch {
                        val note = entity.note.toNote()
                        val result = noteRunDataSource.postNote(note)
                        if (result is com.jvrcoding.notemark.core.domain.util.Result.Success) {
                            notePendingSyncDao.deleteNotePendingSyncEntity(entity.noteId)
                        }
                    }
                }

                val updateJobs = updatedNotes.await().map { entity ->
                    launch {
                        val note = entity.note.toNote()
                        val result = noteRunDataSource.putNote(note)
                        if (result is com.jvrcoding.notemark.core.domain.util.Result.Success) {
                            notePendingSyncDao.deleteNotePendingSyncEntity(entity.noteId)
                        }
                    }
                }

                val deleteJobs = deletedNotes.await().map { entity ->
                    launch {
                        val result = noteRunDataSource.deleteNote(entity.noteId)
                        if (result is com.jvrcoding.notemark.core.domain.util.Result.Success) {
                            notePendingSyncDao.deleteNotePendingSyncEntity(entity.noteId)
                        }
                    }
                }

                createJobs.forEach { it.join() }
                updateJobs.forEach { it.join() }
                deleteJobs.forEach { it.join() }

                dataStoreRepository.putString(
                    DataStoreKeys.LAST_SYNC,
                    ZonedDateTime.now()
                        .withZoneSameInstant(ZoneId.of("UTC"))
                        .toInstant()
                        .toString()
                    )
                Result.success()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}