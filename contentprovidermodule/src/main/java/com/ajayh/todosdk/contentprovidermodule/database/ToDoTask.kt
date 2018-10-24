package com.ajayh.todosdk.myapplication.datamodel

import android.os.Parcel
import android.os.Parcelable

class ToDoTask() : Parcelable{

    /*lateinit*/ var id: Int =0

    lateinit var todoTask: String

    var isTaskCompleted: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        todoTask = parcel.readString()
        isTaskCompleted = parcel.readByte() != 0.toByte()
    }

    fun getTaskId(): Int {
        return id
    }

    fun setTaskId(id: Int) {
        this.id = id
    }

    fun getToDoTask(): String {
        return todoTask
    }

    fun setToDoTask(task: String) {
        this.todoTask = task
    }

    fun isCompleted(): Boolean {
        return isTaskCompleted
    }

    fun setCompleted(isCompleted: Boolean) {
        this.isTaskCompleted = isCompleted
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(todoTask)
        parcel.writeByte(if (isTaskCompleted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDoTask> {
        override fun createFromParcel(parcel: Parcel): ToDoTask {
            return ToDoTask(parcel)
        }

        override fun newArray(size: Int): Array<ToDoTask?> {
            return arrayOfNulls(size)
        }
    }
}