package com.jvrcoding.notemark.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jvrcoding.notemark.core.data.database.entity.NotePendingSyncEntity
import com.jvrcoding.notemark.core.data.database.entity.SyncOperationType

@Dao
interface NotePendingSyncDao {

    @Query("SELECT * FROM notependingsyncentity WHERE username=:username AND operationType=:operationType")
    suspend fun getAllNotePendingSyncEntities(
        username: String,
        operationType: SyncOperationType
    ): List<NotePendingSyncEntity>

    @Query("SELECT * FROM notependingsyncentity WHERE noteId=:noteId")
    suspend fun getNotePendingSyncEntity(noteId: String): NotePendingSyncEntity?

    @Upsert
    suspend fun upsertNotePendingSyncEntity(entity: NotePendingSyncEntity)

    @Query("DELETE FROM notependingsyncentity WHERE noteId=:noteId")
    suspend fun deleteNotePendingSyncEntity(noteId: String)
}