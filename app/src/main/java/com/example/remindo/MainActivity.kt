package com.example.remindo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        //we save our tasks in this array
        val allTasks = arrayListOf("Tache 1", "Tache2", "Tache3")

        val listViewTask : ListView = findViewById(R.id.listTask)

        //ato display correctly each task
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            allTasks
        )
        listViewTask.adapter = adapter

    }
}