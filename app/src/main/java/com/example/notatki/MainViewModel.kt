package com.example.notatki

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notatki.data.Note
import com.example.notatki.data.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    private val repo : NoteRepository

    init {
        val dao = NoteDatabase.NoteDb.getInstance(application).noteDao()
        repo = NoteRepository(dao)
        allNotes = repo.allNotes
    }
    fun searchNotes(query: String): LiveData<List<Note>> {
        return repo.searchNotes(query)
    }

    fun getNotesByCategory(category: String?): LiveData<List<Note>> {
        return repo.getNotesByCategory(category)
    }

    fun insert(note:Note) = viewModelScope.launch ( Dispatchers.IO ) {
        repo.insert(note)
    }

    fun delete(note:Note) = viewModelScope.launch ( Dispatchers.IO ) {
        repo.delete(note)
    }



}