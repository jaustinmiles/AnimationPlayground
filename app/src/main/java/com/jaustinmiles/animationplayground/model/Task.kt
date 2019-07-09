package com.jaustinmiles.animationplayground.model

import android.os.Parcel
import android.os.Parcelable
import com.jaustinmiles.animationplayground.Priority

class Task(
    val taskName: String?, private val dueDate: String?, private val dueTime: String?,
    private val description: String?, private val priority: Priority

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        Priority.values()[parcel.readInt()]
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskName)
        parcel.writeString(dueDate)
        parcel.writeString(dueTime)
        parcel.writeString(description)
        parcel.writeInt(priority.ordinal)
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