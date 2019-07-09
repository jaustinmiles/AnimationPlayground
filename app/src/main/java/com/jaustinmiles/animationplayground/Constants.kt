package com.jaustinmiles.animationplayground

const val TIME_STEP = 1.0f / 60.0f
const val VELOCITY_ITERATIONS = 6
const val POSITION_ITERATIONS = 2
const val NUM_BUBBLES = 10
const val TASK_NAME = "task_name"
const val TASK_PARCELABLE = "task_parcelable"
const val CREATE_NEW_TASK_CODE = 1001

enum class Priority {
    URGENT, ESSENTIAL, IMPORTANT, TRIVIAL
}