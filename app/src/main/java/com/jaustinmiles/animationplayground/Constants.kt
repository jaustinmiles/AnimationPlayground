package com.jaustinmiles.animationplayground

const val TIME_STEP = 1.0f / 60.0f
const val VELOCITY_ITERATIONS = 6
const val POSITION_ITERATIONS = 2
const val NUM_BUBBLES = 10
const val TASK_PARCELABLE = "task_parcelable"
const val CREATE_NEW_TASK_CODE = 1001

enum class Priority {
    URGENT, ESSENTIAL, IMPORTANT, TRIVIAL
}

val PRIORITY_STRINGS = arrayOf("Urgent", "Essential", "Important", "Trivial")

const val RADIUS_URGENT = 350
const val RADIUS_ESSENTIAL = 300
const val RADIUS_IMPORTANT = 250
const val RADIUS_TRIVIAL = 200
const val RADIUS_DEFAULT = 175