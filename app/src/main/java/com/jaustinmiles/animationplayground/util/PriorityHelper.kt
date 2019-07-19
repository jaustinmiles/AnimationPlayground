package com.jaustinmiles.animationplayground.util

import com.jaustinmiles.animationplayground.Priority

class PriorityHelper {
    companion object {
        fun getIntFromPriority(priority: Priority): Int {
            return when (priority) {
                Priority.URGENT -> 5
                Priority.ESSENTIAL -> 4
                Priority.IMPORTANT -> 3
                Priority.TRIVIAL -> 2
            }
        }

        fun getPriorityIntFromString(priority : String) : Int {
            return when (priority) {
                "Urgent" -> 5
                "Essential" -> 4
                "Important" ->3
                "Trivial"-> 2
                else -> 1
            }
        }
    }
}