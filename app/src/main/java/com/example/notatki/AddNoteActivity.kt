package com.example.notatki


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TimePicker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notatki.data.Note
import com.example.notatki.databinding.ActivityAddNoteBinding
import com.example.notatki.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var selectedCalendar: Calendar
    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
    private var selectedPriority: String = ""
    private var selectedCategory: String = ""

    private lateinit var binding : ActivityAddNoteBinding

    interface MainActivityListener {
        fun scheduleNotification(note: Note)
    }
    private var listener: MainActivityListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        selectedCalendar = Calendar.getInstance()
        dateEditText = findViewById(R.id.dateEditText)
        timeEditText = findViewById(R.id.timeEditText)
        val btn : Button = findViewById(R.id.buttonSave)
        btn.setOnClickListener {
            addingNote()
        }

        val priorityRadioGroup: RadioGroup = findViewById(R.id.priorityRadioGroup)
        val categoryRadioGroup: RadioGroup = findViewById(R.id.radioGroupCategory)

        priorityRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedPriority = findViewById<RadioButton>(checkedId)?.text.toString()
            Log.d("priorytet", selectedPriority)
            val isHighPriority = selectedPriority == "Wysoki"
            dateEditText.visibility = if (isHighPriority) View.VISIBLE else View.GONE
            timeEditText.visibility = if (isHighPriority) View.VISIBLE else View.GONE
        }
        categoryRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedCategory = findViewById<RadioButton>(checkedId)?.text.toString()
            Log.d("kategoria", selectedCategory)
        }
        dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        timeEditText.setOnClickListener {
            showTimePickerDialog()
        }

    }
    private fun addingNote(){
        val title: EditText = findViewById(R.id.editTextTitle)
        var description: EditText = findViewById(R.id.editTextDescription)
        val notificationDate = selectedCalendar.timeInMillis
        Log.d("Czas", notificationDate.toString())

        var titleInput = title.text.toString()
        var descriptionInput = description.text.toString()


        Log.d("Priorytet podczas dodawania", selectedPriority)

        val category = when {
            findViewById<RadioButton>(R.id.redColorRadioButton).isChecked -> "#E89CB5"
            findViewById<RadioButton>(R.id.blueColorRadioButton).isChecked -> "#89CFF0"
            findViewById<RadioButton>(R.id.greenColorRadioButton).isChecked -> "#CADC79"
            findViewById<RadioButton>(R.id.yellowColorRadioButton).isChecked -> "#FDE456"
            else -> "#FFFFFF"
        }

        val note = Note(title= titleInput, description = descriptionInput, category = category, priority = selectedPriority, notificationDate = notificationDate.toString())

        viewModel.insert(note)
        if(selectedPriority=="Wysoki"){
            Log.d("Wchodzi", selectedPriority)
            scheduleNotification(note)
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun scheduleNotification(note: Note)
    {
        Log.d("notatka", note.toString())
        val intent = Intent(applicationContext, Notification::class.java)
        val title = note.title
        val message = note.description
        intent.putExtra("titleExtra", title)
        intent.putExtra("messageExtra", message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val time = note.notificationDate.toLong()
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                selectedCalendar.set(year, month, day)
                updateDateEditText()
            },
            selectedCalendar.get(Calendar.YEAR),
            selectedCalendar.get(Calendar.MONTH),
            selectedCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)
                updateTimeEditText()
            },
            selectedCalendar.get(Calendar.HOUR_OF_DAY),
            selectedCalendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun updateDateEditText() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateEditText.setText(dateFormat.format(selectedCalendar.time))
    }

    private fun updateTimeEditText() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeEditText.setText(timeFormat.format(selectedCalendar.time))
    }

}