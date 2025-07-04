package com.jvrcoding.notemark.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jvrcoding.notemark.core.data.database.dao.NoteDao
import com.jvrcoding.notemark.core.data.database.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
}