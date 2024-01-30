package com.example.notatki

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notatki.data.Note
import com.example.notatki.databinding.ActivityAddNoteBinding
import com.example.notatki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IAdaptor {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var radioGroupCategory: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(this)
        createNotificationChannel()
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {
            adapter.updateData(it)
        })
        radioGroupCategory = findViewById(R.id.radioGroupCategory)
        radioGroupCategory.setOnCheckedChangeListener { group, checkedId ->
            filterNotesByCategory()
        }
        val editTextSearch: EditText = findViewById(R.id.searchView)
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used in this example
            }

            override fun afterTextChanged(s: Editable?) {
                // Update the filtered notes based on the entered text
                filterNotes(s.toString())
            }
        })
    }
    private fun filterNotesByCategory() {
        val checkedRadioButtonId = radioGroupCategory.checkedRadioButtonId
        val filteredCategory = when (checkedRadioButtonId) {
            R.id.allColorRadioButton -> null
            R.id.redColorRadioButton -> "#E89CB5"
            R.id.blueColorRadioButton -> "#89CFF0"
            R.id.greenColorRadioButton -> "#CADC79"
            R.id.yellowColorRadioButton -> "#FDE456"
            else -> null
        }
        viewModel.getNotesByCategory(filteredCategory)
            .observe(this, { filteredNotes ->
                adapter.updateData(filteredNotes)
            })
    }
    private fun filterNotes(query: String) {
        viewModel.searchNotes(query).observe(this, { filteredNotes ->
            adapter.filterData(filteredNotes)
        })

    }

    fun addNote(view: View){
        val intent = Intent(this,AddNoteActivity::class.java)
        startActivity(intent)
    }


    private fun createNotificationChannel() {
        Log.d("Przed", "XDDDDDDDDDDDDDDDDDDDDDDDDDDD")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("po", "XDDDDDDDDDDDDDDDDDDDDDDDDD")
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel2", name, importance)
            channel.description = descriptionText
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNoteClick(note: Note) {
        viewModel.delete(note)
    }

}