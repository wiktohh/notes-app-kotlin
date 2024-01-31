package com.example.notatki.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.example.notatki.Viewmodel.MainViewModel
import com.example.notatki.R
import com.example.notatki.data.Note

class EditNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var selectedPriority = ""
    private var selectedCategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val backBtn: Button = findViewById(R.id.backToNotes)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val priorityRadioGroup: RadioGroup = findViewById(R.id.priorityRadioGroup)
        val categoryRadioGroup: RadioGroup = findViewById(R.id.categoryRadioGroup)
        val titleText: EditText = findViewById(R.id.editTextTitle)
        val desciptionText: EditText = findViewById(R.id.editTextDescription)


        val noteId = intent.getLongExtra("noteId", -1)
        val priority = intent.getStringExtra("notePriority").toString()
        val category = intent.getStringExtra("noteCategory").toString()
        val title = intent.getStringExtra("noteTitle").toString()
        val description = intent.getStringExtra("noteDescription").toString()

        Log.d("noteId", noteId.toString())

        titleText.setText(title)
        desciptionText.setText(description)

        when (priority) {
            "Low" -> findViewById<RadioButton>(R.id.lowPriorityRadioButton).isChecked = true
            "Medium" -> findViewById<RadioButton>(R.id.mediumPriorityRadioButton).isChecked = true
            "High" -> findViewById<RadioButton>(R.id.highPriorityRadioButton).isChecked = true
        }

        when (category) {
            "#E89CB5" -> findViewById<RadioButton>(R.id.redColorRadioButton).isChecked = true
            "#89CFF0" -> findViewById<RadioButton>(R.id.blueColorRadioButton).isChecked = true
            "#CADC79" -> findViewById<RadioButton>(R.id.greenColorRadioButton).isChecked = true
            "#FDE456" -> findViewById<RadioButton>(R.id.yellowColorRadioButton).isChecked = true
        }

        priorityRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedPriority = findViewById<RadioButton>(checkedId)?.text.toString()
        }

        categoryRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedCategory = findViewById<RadioButton>(checkedId)?.text.toString()
        }

        val btn: Button = findViewById(R.id.buttonSave)
        btn.setOnClickListener {
            val titleText: EditText = findViewById(R.id.editTextTitle)
            val descriptionText: EditText = findViewById(R.id.editTextDescription)

            val updatedTitle = titleText.text.toString()
            val updatedDescription = descriptionText.text.toString()

            val category = when {
                findViewById<RadioButton>(R.id.redColorRadioButton).isChecked -> "#E89CB5"
                findViewById<RadioButton>(R.id.blueColorRadioButton).isChecked -> "#89CFF0"
                findViewById<RadioButton>(R.id.greenColorRadioButton).isChecked -> "#CADC79"
                findViewById<RadioButton>(R.id.yellowColorRadioButton).isChecked -> "#FDE456"
                else -> "#FFFFFF"
            }
            val prio = when {
                findViewById<RadioButton>(R.id.lowPriorityRadioButton).isChecked -> "Low"
                findViewById<RadioButton>(R.id.mediumPriorityRadioButton).isChecked -> "Medium"
                findViewById<RadioButton>(R.id.highPriorityRadioButton).isChecked -> "High"
                else -> "None"
            }
            Log.d("priorytet", selectedPriority)
            if(noteId.toInt() != -1) {
                val updatedNote = Note(
                    id = noteId,
                    title = updatedTitle,
                    description = updatedDescription,
                    priority = prio,
                    category = category,
                )
                Log.d("notatka ", updatedNote.toString())

                viewModel.update(updatedNote)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            }

        }
    }
