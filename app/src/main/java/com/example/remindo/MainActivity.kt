package com.example.remindo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mybdd.classes.TaskModelClass
import com.example.mybdd.handler.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var toggle :ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // List of tasks
        val listViewTask : ListView = findViewById(R.id.listTask)

        // Navigation Menu
        val drawerlayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)

        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.status_todo -> {
                    Toast.makeText(applicationContext, "Clicked A Faire", Toast.LENGTH_SHORT).show()
                    val tasksByCategory: ArrayList<TaskModelClass> = databaseHandler.viewTaskByStatus("A Faire")
                    //creating custom ArrayAdapter
                    val adapterTask : TaskAdapter = TaskAdapter(this, tasksByCategory)
                    listViewTask.adapter = adapterTask

                }
                R.id.status_inDoing -> {
                    Toast.makeText(applicationContext, "Clicked En cours", Toast.LENGTH_SHORT).show()
                    val tasksByCategory: ArrayList<TaskModelClass> = databaseHandler.viewTaskByStatus("En cours")
                    //creating custom ArrayAdapter
                    val adapterTask : TaskAdapter = TaskAdapter(this, tasksByCategory)
                    listViewTask.adapter = adapterTask
                }
                R.id.status_done -> {
                    Toast.makeText(applicationContext, "Clicked Terminé", Toast.LENGTH_SHORT).show()
                    val tasksByCategory: ArrayList<TaskModelClass> = databaseHandler.viewTaskByStatus("Terminé")
                    //creating custom ArrayAdapter
                    val adapterTask : TaskAdapter = TaskAdapter(this, tasksByCategory)
                    listViewTask.adapter = adapterTask
                }
            }
            true
        }




        //read records from database in ListView
        //creating the instance of DatabaseHandler class
        //val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        //calling the viewTask method of DatabaseHandler class to read the records
        val task: ArrayList<TaskModelClass> = databaseHandler.viewTask()
        val taskArrayStatus = Array<String>(task.size){"null"}
        val taskArrayTitle = Array<String>(task.size){"null"}
        val taskArrayDate = Array<String>(task.size){"null"}
        var index = 0
        for(t in task){
            taskArrayStatus[index] = t.taskStatus
            taskArrayTitle[index] = t.taskTitle
            taskArrayDate[index] = t.taskDate
            println( "---------->>>>>>>>>" + t.taskId)
            index++
        }
        //creating custom ArrayAdapter
        val adapterTask : TaskAdapter = TaskAdapter(this, task)
        listViewTask.adapter = adapterTask

        // Button to switch on the page to add a task
        val button : FloatingActionButton = findViewById(R.id.add_task)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateTask::class.java)
            startActivity(intent)
        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun TasksByCategory(status:String){
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
    }


}


private class TaskAdapter(private val ctx : Activity, private val task : ArrayList<TaskModelClass>)
    : ArrayAdapter<TaskModelClass>(ctx, R.layout.activity_main, task){

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val inflater = ctx.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item_task, viewGroup, false)

        val titleTask = rowView!!.findViewById(R.id.titre) as TextView
        val statusTask = rowView!!.findViewById(R.id.status) as TextView
        val dateTask= rowView!!.findViewById(R.id.date) as TextView

        titleTask.text = task[position].taskTitle
        statusTask.text = task[position].taskStatus
        dateTask.text = task[position].taskDate
        val databaseHandler: DatabaseHandler = DatabaseHandler(ctx)

        //Change the color of the radioButton of the task according to his status in database
        val radioButton = rowView!!.findViewById<RadioButton>(R.id.radioButton)
        if (statusTask!!.text == "A Faire"){
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.grey))
            radioButton.buttonTintList = colorStateList
        }else if (statusTask!!.text == "En cours"){
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.yellow))
            radioButton.buttonTintList = colorStateList
        }else{
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.green))
            radioButton.buttonTintList = colorStateList
        }

        //Listener to change the status's color of the task manually
        radioButton.setOnClickListener {


            //change the radioButton's color according to the new status
            when(statusTask!!.text){
                "A Faire" -> {
                    statusTask!!.text = "En cours"
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.yellow))
                    radioButton.buttonTintList = colorStateList
                }

                "En cours" -> {
                    statusTask!!.text = "Terminé"
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.green))
                    radioButton.buttonTintList = colorStateList
                }

                "Terminé" -> {
                    statusTask!!.text = "A Faire"
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(viewGroup.context, R.color.grey))
                    radioButton.buttonTintList = colorStateList
                }
            }

            //then change in database the status of the task
            val databaseHandler: DatabaseHandler = DatabaseHandler(ctx)
            println("SALUT---------------------------")
            println(task[position].taskStatus)
            println(statusTask!!.text.toString())

            task[position].taskStatus = statusTask!!.text.toString()
            println(task[position].taskStatus)
            val status = databaseHandler.updateTask(
                task[position]
            )

        }




        return rowView


    }
}