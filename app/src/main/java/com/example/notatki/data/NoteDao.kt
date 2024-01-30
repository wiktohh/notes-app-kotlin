package com.example.notatki.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(note: Note)

    @Delete
    abstract suspend fun delete(note: Note)

    @Upsert
    abstract suspend fun update(note: Note)

    @Query("SELECT * FROM noteTable WHERE category = :category OR :category IS NULL")
    abstract fun getNotesByCategory(category: String?): LiveData<List<Note>>


    @Query("SELECT * FROM noteTable ORDER BY id DESC")
    abstract fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM noteTable WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    abstract fun searchNotes(query: String): LiveData<List<Note>>
}