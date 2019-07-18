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
    }
}