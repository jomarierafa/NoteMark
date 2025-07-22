package com.jvrcoding.notemark.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jvrcoding.notemark.core.data.database.dao.NoteDao
import com.jvrcoding.notemark.core.data.database.dao.NotePendingSyncDao
import com.jvrcoding.notemark.core.data.database.entity.NoteEntity
import com.jvrcoding.notemark.core.data.database.entity.NotePendingSyncEntity

@Database(
    entities = [
        NoteEntity::class,
        NotePendingSyncEntity::class
    ],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val notePendingSyncDao: NotePendingSyncDao
}