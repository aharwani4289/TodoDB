package com.ajayh.todosdk.myapplication.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ajayh.todosdk.contentprovidermodule.database.TodoDatabaseContract
import com.ajayh.todosdk.myapplication.R
import com.ajayh.todosdk.myapplication.adapter.viewholder.ViewHolder

class ToDoTaskAdapter(mContext: Context, cursor: Cursor) : CursorRecyclerAdapter<ViewHolder>(mContext = mContext, c = cursor) {

    private val mContext = mContext
    private lateinit var mLayoutInflator: LayoutInflater

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.todo_list_item, null, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        holder.todoTask.text = cursor.getString(cursor.getColumnIndex(TodoDatabaseContract.ToDoTable.TODO_TASK))
    }
}