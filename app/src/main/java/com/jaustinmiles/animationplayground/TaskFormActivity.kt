package com.jaustinmiles.animationplayground

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jaustinmiles.animationplayground.model.Task
import com.jaustinmiles.animationplayground.util.PriorityHelper
import kotlinx.android.synthetic.main.activity_task_form.*
import java.util.*

class TaskFormActivity : AppCompatActivity() {

    var priorityInt : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        date_picker.onFocusChangeListener = View.OnFocusChangeListener(datePickerSelected())

        time_picker.onFocusChangeListener = View.OnFocusChangeListener(timePickerSelected())

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, PRIORITY_STRINGS )

        priority_selector.adapter = adapter
        priority_selector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                priorityInt = PriorityHelper.getPriorityIntFromString(PRIORITY_STRINGS[position])
            }

        }
        submit.setOnClickListener {
            submitTaskIntent()
        }
    }

    private fun timePickerSelected(): (View, Boolean) -> Unit {
        return fun(_: View, hasFocus: Boolean) {
            if (hasFocus) {
                val currentTime = Calendar.getInstance()
                val hour = currentTime.get(Calendar.HOUR_OF_DAY)
                val minute = currentTime.get(Calendar.MINUTE)
                val mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
                        _, selectedHour, selectedMinute ->
                    time_picker.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                }, hour, minute, false)
                mTimePicker.setTitle("Select Time")
                mTimePicker.show()
            }
        }
    }

    private fun datePickerSelected(): (View, Boolean) -> Unit {
        return fun(_: View, hasFocus: Boolean) {
            if (hasFocus) {
                val currentDate = Calendar.getInstance()
                val year = currentDate.get(Calendar.YEAR)
                val month = currentDate.get(Calendar.MONTH)
                val day = currentDate.get(Calendar.DAY_OF_MONTH)
                val mDatePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    val newMonth = selectedMonth + 1
                    date_picker.setText(String.format("%02d/%02d/%04d", newMonth, selectedDay, selectedYear))
                }, year, month, day)
                mDatePicker.setTitle("Select date")
                mDatePicker.show()
            }
        }
    }

    private fun submitTaskIntent() {
        val name = what_task_edit_text.text.toString()
        val date = date_picker.text.toString()
        val time = time_picker.text.toString()
        val task = Task(UUID.randomUUID().leastSignificantBits, name, date, time, null, priorityInt)
        val intent = Intent()
        intent.putParcelableArrayListExtra(TASK_PARCELABLE, arrayListOf(task))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
