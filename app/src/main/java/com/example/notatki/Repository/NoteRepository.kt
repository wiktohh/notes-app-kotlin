package com.example.notatki.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notatki.data.Note
import com.example.notatki.data.NoteDao
import com.example.notatki.data.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val noteDao : NoteDao) {

    val allNotes = noteDao.getAll()

    suspend fun insert(note: Note) = withContext(Dispatchers.IO) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        noteDao.update(note)
    }

    fun getNotesByCategory(category: String?): LiveData<List<Note>> {
        return noteDao.getNotesByCategory(category)
    }

    fun searchNotes(query: String): LiveData<List<Note>> {
        return noteDao.searchNotes(query)
    }

    suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(note)
    }

}