package com.example.jitendrakumar.todolist.adapters


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.jitendrakumar.todolist.R
import com.example.jitendrakumar.todolist.models.Task
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.list_item_task.view.*

class TaskRecyclerAdapter (
        val tasks: ArrayList<Task>,
        val onTaskUpdate: (task: Task) -> Unit,
        val onTaskDelete: (task: Task) -> Unit
): RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.list_item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.checkBox.setOnCheckedChangeListener(null)

        holder.itemView.checkBox.isChecked = tasks[position].done
        holder.itemView.tvTaskName.text = tasks[position].taskName

        holder.itemView.checkBox.setOnCheckedChangeListener {
            _, isChecked ->
            tasks[position].done = isChecked
            onTaskUpdate(tasks[position])
        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Delete Task")
                    .setMessage("Do you really want to delete this task ? ")
                    .setPositiveButton(
                            "YES",
                            { _, _ -> onTaskDelete(tasks[position]) }
                    )
                    .setNegativeButton("NO", {_, _ -> Unit})
                    .show()
            true
        }
    }

    class TaskViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}