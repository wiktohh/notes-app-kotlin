package com.example.notatki

import android.content.Context
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

    suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(note)
    }

}