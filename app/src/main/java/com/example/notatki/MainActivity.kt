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
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notatki.data.Note
import com.example.notatki.databinding.ActivityAddNoteBinding
import com.example.notatki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IAdaptor {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adaptor = NoteAdapter(this)
        createNotificationChannel()
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