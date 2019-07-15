package com.jaustinmiles.animationplayground.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class Task(@PrimaryKey(autoGenerate=true) var id: Long?,
                @ColumnInfo(name="task_name")
                val taskName: String?,
                @ColumnInfo(name="due_date")
                val dueDate: String?,
                @ColumnInfo(name="due_time")
                val dueTime: String?,
                @ColumnInfo(name="description")
                val description: String?,
                @ColumnInfo(name="priority_value")
                val priority: Int

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id?: -1)
        parcel.writeString(taskName)
        parcel.writeString(dueDate)
        parcel.writeString(dueTime)
        parcel.writeString(description)
        parcel.writeInt(priority)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }




}