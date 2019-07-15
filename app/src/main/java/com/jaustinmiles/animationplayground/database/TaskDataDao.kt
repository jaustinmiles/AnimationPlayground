package com.jaustinmiles.animationplayground.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.jaustinmiles.animationplayground.model.Task

@Dao
interface TaskDataDao {

    @Query("SELECT * from task")
    fun getAll() : List<Task>

    @Insert(onConflict = REPLACE)
    fun insert(task: Task)

    @Query("DELETE from task")
    fun deleteAll()
}