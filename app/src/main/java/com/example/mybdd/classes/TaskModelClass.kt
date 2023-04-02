package com.example.mybdd.classes;

import android.widget.EditText
import java.util.Calendar

//creating a Data Model Class
class TaskModelClass (var taskTitle:String, var taskDescription:String, var taskDate:String, var taskStatus:String) {
    var taskId:Int? = null // the 'id' will be define when the task will be save in the database
}
