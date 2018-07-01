package com.example.jitendrakumar.todolist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.jitendrakumar.todolist.R.id.*
import com.example.jitendrakumar.todolist.adapters.TaskRecyclerAdapter
import com.example.jitendrakumar.todolist.db.TodoDbHelper
import com.example.jitendrakumar.todolist.db.tables.TaskTable
import com.example.jitendrakumar.todolist.models.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_task.*

class MainActivity : AppCompatActivity() {

    val tasks = ArrayList<Task>()
    lateinit var taskAdapter: TaskRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = TodoDbHelper(this).writableDatabase

        rvTasks.layoutManager = LinearLayoutManager(this)
        fun refreshTodos () {
            tasks.clear()
            tasks.addAll(TaskTable.getAllTasks(db))
            taskAdapter.notifyDataSetChanged()
        }

        val onTaskUpdate = {
            task: Task ->
            TaskTable.updateTask(db, task)
            refreshTodos()
        }
        val onTaskDelete = {
            task: Task ->
            TaskTable.deleteTask(db, task)
            refreshTodos()
        }

        taskAdapter = TaskRecyclerAdapter(tasks, onTaskUpdate, onTaskDelete)
        rvTasks.adapter = taskAdapter

        refreshTodos()


        btnAddTask.setOnClickListener {
            val newTask = Task(
                    null,
                    etNewTask.text.toString(),
                    false
            )
            val id = TaskTable.addTask(db, newTask)
            refreshTodos()
            Log.d("TASK", "INSERTED AT ${id}")
            taskAdapter.notifyDataSetChanged()
        }

        btnClearTask.setOnClickListener {
            TaskTable.deleteTask(db, true)
            refreshTodos()
        }

    }
}