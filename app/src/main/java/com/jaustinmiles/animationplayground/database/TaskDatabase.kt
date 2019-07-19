package com.jaustinmiles.animationplayground.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jaustinmiles.animationplayground.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDataDao(): TaskDataDao



    companion object {
        private var instance : TaskDatabase? = null

        fun getInstance(context: Context) : TaskDatabase? {
            if (instance == null) {
                synchronized(TaskDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java,
                        "tasks.db").build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}

