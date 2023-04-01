package com.example.mybdd.handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mybdd.classes.TaskModelClass

//creating the database Logic, extending the SQLiteOpenHelper base class
class DatabaseHandler( context : Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "TaskDatabase"
        private val TABLE_TASKS = "TaskTable"
        private val KEY_ID = "id"
        private val KEY_TITLE = "title"
        private val KEY_DESCRIPTION = "description"
        private val KEY_DATE = "date"
        private val KEY_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_TASKS_TABLE = ("CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_DATE + " DATE,"
                + KEY_STATUS + " TEXT" + ")")
        db?.execSQL(CREATE_TASKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS)
        onCreate(db)
    }

    //method to insert data
    fun addTask(task: TaskModelClass): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_ID, task.taskId)
        contentValues.put(KEY_TITLE, task.taskTitle) // TaskModelClass title
        contentValues.put(KEY_DESCRIPTION, task.taskDescription) // TaskModelClass description
        contentValues.put(KEY_DATE, task.taskDate.toString()) // TaskModelClass date
        contentValues.put(KEY_STATUS, task.taskStatus) // TaskModelClass status
        // Inserting Row
        val success = db.insert(TABLE_TASKS, null, contentValues)
        println(success)
        //2nd argument is String containing nullColumnHack
        db.close() // closing database connection
        return success
    }

    //method to read data
    fun viewTask(): ArrayList<TaskModelClass> {
        val taskList: ArrayList<TaskModelClass> = ArrayList<TaskModelClass>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var taskId: Int
        var taskTitle: String
        var taskDescription: String
        var taskDate: String
        var taskStatus: String

        if (cursor.moveToFirst()) {
            do {
                taskId = cursor.getInt(cursor.getColumnIndex("id"))
                taskTitle = cursor.getString(cursor.getColumnIndex("title"))
                taskDescription = cursor.getString(cursor.getColumnIndex("description"))
                taskDate = cursor.getString(cursor.getColumnIndex("date"))
                taskStatus = cursor.getString(cursor.getColumnIndex("status"))
                val task = TaskModelClass(
                    taskTitle = taskTitle,
                    taskDescription = taskDescription,
                    taskDate = taskDate,
                    taskStatus = taskStatus
                    )
                //We define task's id now because the id is not in the constructor
                task.taskId = taskId
                taskList.add(task)

            } while (cursor.moveToNext())
        }
        return taskList
    }

    //method to read data by status
    fun viewTaskByStatus(statusChosen:String): ArrayList<TaskModelClass> {
        val taskList: ArrayList<TaskModelClass> = ArrayList<TaskModelClass>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS WHERE $KEY_STATUS='$statusChosen'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var taskId: Int
        var taskTitle: String
        var taskDescription: String
        var taskDate: String
        var taskStatus: String

        if (cursor.moveToFirst()) {
            do {
                taskId = cursor.getInt(cursor.getColumnIndex("id"))
                taskTitle = cursor.getString(cursor.getColumnIndex("title"))
                taskDescription = cursor.getString(cursor.getColumnIndex("description"))
                taskDate = cursor.getString(cursor.getColumnIndex("date"))
                taskStatus = cursor.getString(cursor.getColumnIndex("status"))
                    val task = TaskModelClass(
                        taskTitle = taskTitle,
                        taskDescription = taskDescription,
                        taskDate = taskDate,
                        taskStatus = taskStatus
                    )
                    //We define task's id now because the id is not in the constructor
                    task.taskId = taskId
                    taskList.add(task)


            } while (cursor.moveToNext())
        }
        return taskList
    }

    //method to update data
    fun updateTask(task: TaskModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_ID, task.taskId)
        contentValues.put(KEY_TITLE, task.taskTitle) // TaskModelClass title
        contentValues.put(KEY_DESCRIPTION, task.taskDescription) // TaskModelClass description
        contentValues.put(KEY_DATE, task.taskDate.toString()) // TaskModelClass date
        contentValues.put(KEY_STATUS, task.taskStatus) // TaskModelClass status
        println("->" + task.taskStatus)
        // Updating Row
        val success = db.update(TABLE_TASKS, contentValues, "id=" + task.taskId, null)
        db.close() // Closing database connection
        return success
    }

    //method to delete data
    fun deleteTask(task: TaskModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.taskId) // TaskModelClass id

        // Deleting Row
        val success = db.delete(TABLE_TASKS, "id=" + task.taskId, null)
        db.close() // Closing database connection
        return success

    }
}