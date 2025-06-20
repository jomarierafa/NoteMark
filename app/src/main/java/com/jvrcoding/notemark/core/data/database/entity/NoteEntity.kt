package com.jvrcoding.notemark.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class NoteEntity(
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditAt: String,
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString()
)
