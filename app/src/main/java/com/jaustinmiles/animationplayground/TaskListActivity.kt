package com.jaustinmiles.animationplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jaustinmiles.animationplayground.model.Task
import kotlinx.android.synthetic.main.task_list_activity.*
import java.util.ArrayList

class TaskListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list_activity)
        setSupportActionBar(toolbar_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tasks = intent.getParcelableArrayListExtra<Task>(TASK_LIST_PARCELABLE)

        setupRecyclerView(tasks)

    }

    private fun setupRecyclerView(tasks: ArrayList<Task>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskItemAdapter(tasks)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        val divider = DividerItemDecoration(
            recyclerView.context,
            (viewManager as LinearLayoutManager).orientation
        )
        recyclerView.addItemDecoration(divider)
    }
}