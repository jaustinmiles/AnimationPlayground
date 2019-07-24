package com.jaustinmiles.animationplayground

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.jaustinmiles.animationplayground.model.Task

class TaskItemAdapter(private val taskDataSet: ArrayList<Task>)
    : RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder>() {

    override fun getItemCount(): Int {
        return taskDataSet.size
    }

    class TaskViewHolder(listTaskItem: RelativeLayout) : RecyclerView.ViewHolder(listTaskItem) {
        var listTaskItemName : TextView = listTaskItem.findViewById(R.id.list_item_task_name)
        var listTaskItemDue: TextView = listTaskItem.findViewById(R.id.list_item_due_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val parentView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_list_item, parent, false)
                as RelativeLayout
        return TaskViewHolder(parentView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.listTaskItemName.text = taskDataSet[position].taskName
        holder.listTaskItemDue.text = taskDataSet[position].dueDate
    }

}


