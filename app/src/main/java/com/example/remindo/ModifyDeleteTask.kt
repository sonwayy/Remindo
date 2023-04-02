package com.example.remindo

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mybdd.classes.TaskModelClass
import com.example.mybdd.handler.DatabaseHandler
import java.util.*

class ModifyDeleteTask : AppCompatActivity()  {


    companion object{
        const val TASK_TO_MODIFY = "task to modify" //Key of to modify/delete the task
        private var taskToModify: Array<String>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_delete_task)

        val intent = intent

        taskToModify = intent.getStringArrayExtra(TASK_TO_MODIFY)
        //taskToModify --> 0:Id ; 1:Title ; 2:Date ; 3:Description ; 4:Status

        val editTitleTask: EditText = findViewById(R.id.title_task)
        val editDateTask: EditText = findViewById(R.id.date_task)
        val editDescriptionTask: EditText = findViewById(R.id.description_task)

        editTitleTask.setText(taskToModify?.get(1)) //Title
        editDateTask.setText(taskToModify?.get(2)) //Date
        editDescriptionTask.setText(taskToModify?.get(3)) //Description


        // Listener do define the expiration date
        val calendar: Calendar = Calendar.getInstance()
        editDateTask.setOnClickListener {
            DatePickerDialog(
                this,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(dayOfMonth, month, year)
                    editDateTask.setText("${dayOfMonth}/${month + 1}/${year}")
                },
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)
            ).show()
        }


    }

    fun updateTask(view: View){
        val title = findViewById<EditText>(R.id.title_task).text.toString()
        val date = findViewById<EditText>(R.id.date_task).text.toString()
        val description = findViewById<EditText>(R.id.description_task).text.toString()

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if(title.trim()!=""){
            val taskUpdate = TaskModelClass(
                title,
                description,
                date,
                taskToModify!!.get(4) //Status
            )
            taskUpdate.taskId = Integer.parseInt(taskToModify?.get(0)) //Int

            val status = databaseHandler.updateTask(
                taskUpdate
            )
            if(status > -1){
                Toast.makeText(applicationContext, "task updated", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.title_task).text.clear()
                findViewById<EditText>(R.id.date_task).text.clear()
                findViewById<EditText>(R.id.description_task).text.clear()

                // Finish the intent and go back on the principal page
                finish()
                //and then we reload the principal page to have the new list of tasks
                val intent = Intent(this@ModifyDeleteTask, MainActivity::class.java)
                startActivity(intent)
            }
        }else{
            Toast.makeText(applicationContext, "title cannot be blank", Toast.LENGTH_LONG).show()
        }
    }

    fun removeTask(view: View){
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        // We only want to delete the task so the task's 'Id' is sufficient
        val taskDelete = TaskModelClass(
            "",
            "",
            "",
            ""
        )
        taskDelete.taskId = Integer.parseInt(taskToModify?.get(0)) //Int
        val status = databaseHandler.deleteTask(
            taskDelete
        )
        if(status > -1){
            Toast.makeText(applicationContext, "task deleted", Toast.LENGTH_LONG).show()
            findViewById<EditText>(R.id.title_task).text.clear()
            findViewById<EditText>(R.id.date_task).text.clear()
            findViewById<EditText>(R.id.description_task).text.clear()

            // Finish the intent and go back on the principal page
            finish()
            //and then we reload the principal page to have the new list of tasks
            val intent = Intent(this@ModifyDeleteTask, MainActivity::class.java)
            startActivity(intent)
        }
    }

}