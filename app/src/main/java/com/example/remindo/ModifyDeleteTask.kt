package com.example.remindo

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ModifyDeleteTask : AppCompatActivity()  {

    private lateinit var titleEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var addButton: Button
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_delete_task)


    }

    fun modifyRecordTask(view: View){}

    fun removeRecordTask(view: View){}

}