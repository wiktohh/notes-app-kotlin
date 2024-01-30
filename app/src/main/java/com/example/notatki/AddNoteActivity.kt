package com.example.notatki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notatki.data.Note

class AddNoteActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val btn : Button = findViewById(R.id.buttonSave)
        btn.setOnClickListener{
            addingNote()
        }
    }
    private fun addingNote(){
        val title: EditText = findViewById(R.id.editTextTitle)
        var description: EditText = findViewById(R.id.editTextDescription)
        var category: RadioGroup = findViewById(R.id.radioGroupCategory)
        var priority: RadioGroup = findViewById(R.id.radioGroupPriority)

        var titleInput = title.text.toString()
        var descriptionInput = description.text.toString()
        var categoryInput = category.checkedRadioButtonId.toString()
        var priorityInput = priority.checkedRadioButtonId.toString()

        viewModel.insert(Note(title= titleInput, description = descriptionInput, category = categoryInput, priority = priorityInput))
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}