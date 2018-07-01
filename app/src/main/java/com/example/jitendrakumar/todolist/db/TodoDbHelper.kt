package com.example.jitendrakumar.todolist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.jitendrakumar.todolist.db.tables.TaskTable

val DB_NAME = "todo.db"
val DB_VER = 4

class TodoDbHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let{
            it.execSQL(TaskTable.CMD_CREATE_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}