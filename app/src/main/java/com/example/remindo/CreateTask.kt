package com.example.remindo

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CreateTask : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var addButton: Button
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        titleEditText = findViewById(R.id.title_edit_text)
        dateEditText = findViewById(R.id.date_edit_text)
        descriptionEditText = findViewById(R.id.description_edit_text)
        addButton = findViewById(R.id.add_button)

        calendar = Calendar.getInstance()

        dateEditText.setOnClickListener {
            DatePickerDialog(
                this,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(dayOfMonth, month, year)
                    dateEditText.setText("${dayOfMonth}/${month + 1}/${year}")
                },
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)
            ).show()
        }

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val date = dateEditText.text.toString()
            val description = descriptionEditText.text.toString()

            val resultIntent = Intent().apply {
                putExtra("title", title)
                putExtra("date", date)
                putExtra("description", description)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}