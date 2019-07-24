package com.jaustinmiles.animationplayground.util

class PriorityHelper {
    companion object {
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