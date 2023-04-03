package com.example.remindo

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mybdd.classes.TaskModelClass
import com.example.mybdd.handler.DatabaseHandler
import java.text.SimpleDateFormat
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

        titleEditText = findViewById(R.id.title_task)
        dateEditText = findViewById(R.id.date_task)
        descriptionEditText = findViewById(R.id.description_task)
        addButton = findViewById(R.id.add_button)

        // Listener do define the expiration date
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

    }

    // Function trigger when clicked on the button 'add_button'
    fun saveTask(view: View){
        val title = findViewById<EditText>(R.id.title_task).text.toString()
        val date = findViewById<EditText>(R.id.date_task).text.toString()
        val description = findViewById<EditText>(R.id.description_task).text.toString()


        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if(title.trim()!=""){
            val status = databaseHandler.addTask(
                TaskModelClass(
                    title,
                    description,
                    date,
                    "A Faire"
                        )
            )
            if(status > -1){
                Toast.makeText(applicationContext, "record save", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.title_task).text.clear()
                findViewById<EditText>(R.id.date_task).text.clear()
                findViewById<EditText>(R.id.description_task).text.clear()

                // Finish the intent and go back on the principal page
                finish()
                //and then we reload the principal page to have the new list of tasks
                val intent = Intent(this@CreateTask, MainActivity::class.java)
                startActivity(intent)
            }
        }else{
            Toast.makeText(applicationContext, "title cannot be blank", Toast.LENGTH_LONG).show()
        }

    }
}