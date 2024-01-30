package com.example.notatki.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="noteTable")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val priority: String,
    val category: String,
    val description: String,
    val notificationDate: String = ""
)