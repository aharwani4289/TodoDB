package com.ajayh.todosdk.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.ajayh.todosdk.contentprovidermodule.database.ToDoDatabaseQueryManager
import com.ajayh.todosdk.myapplication.R
import com.ajayh.todosdk.myapplication.adapter.ToDoTaskAdapter

class MainActivity : BaseActivity() {

    private val addToDo: Button by bind(R.id.create_todo)

    private val toDoRecycler: RecyclerView by bind(R.id.todo_recycler)

    private lateinit var todoAdapter: ToDoTaskAdapter

//    private var mTaskList: ArrayList<ToDoTask> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toDoRecycler.layoutManager = LinearLayoutManager(this)
        todoAdapter = ToDoTaskAdapter(mContext = this, cursor = ToDoDatabaseQueryManager.getInstance().allTodoTasksCursor)
        toDoRecycler.adapter = todoAdapter
        addToDo.setOnClickListener {
            createToDo()
        }
    }

    override fun onResume() {
        super.onResume()
    if(todoAdapter != null){
        todoAdapter.changeCursor( ToDoDatabaseQueryManager.getInstance().allTodoTasksCursor)
    }
    }

    private fun createToDo() {
        val intent = Intent(this, CreateToDoActivity::class.java)
        startActivity(intent)

//        startActivityForResult(intent, Constants.REQUEST_TASK_CREATION)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Constants.REQUEST_TASK_CREATION && resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                var task: ToDoTask = data!!.getParcelableExtra<ToDoTask>(Constants.TASK)
//                mTaskList.add(task)
//                todoAdapter.notifyDataSetChanged()
//            }
//        }
//    }
}


