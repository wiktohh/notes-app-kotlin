package com.example.notatki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notatki.data.Note

class MainActivity : AppCompatActivity(), IAdaptor {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adaptor = NoteAdapter(this)
        recyclerView.adapter = adaptor
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {
            adaptor.updateData(it)
        })
    }

    fun addNote(view: View){
        val intent = Intent(this,AddNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onNoteClick(note: Note) {
        viewModel.delete(note)
    }

}