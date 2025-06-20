package com.jvrcoding.notemark.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jvrcoding.notemark.core.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsertNote(note: NoteEntity)

    @Upsert
    suspend fun upsertNotes(notes: List<NoteEntity>)

    @Query("SELECT * FROM noteentity ORDER BY createdAt DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("DELETE FROM noteentity WHERE id=:id")
    suspend fun deleteNote(id: String)

    @Query("DELETE FROM noteentity")
    suspend fun deleteAllNotes()
}