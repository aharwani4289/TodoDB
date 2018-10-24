package com.ajayh.todosdk.myapplication.activity

import android.os.Bundle
import android.widget.EditText
import com.ajayh.todosdk.contentprovidermodule.database.ToDoDatabaseQueryManager
import com.ajayh.todosdk.myapplication.R
import com.ajayh.todosdk.myapplication.datamodel.ToDoTask

class CreateToDoActivity : BaseActivity() {

    val todoTaskEditText: EditText by bind<EditText>(R.id.todo_task)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_to_do)
//        initListeners();
    }

/*
    private fun initListeners() {
        todoTaskEditText.setOnEditorActionListener() { textview, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val intent = Intent().putExtra(Constants.TASK, todoTaskEditText.editableText)
                setResult(Activity.RESULT_OK, intent)
                true;
            } else {
                false
            }
        }
    }
*/

    override fun onBackPressed() {
        val toDoTask: ToDoTask = ToDoTask()
        toDoTask.setToDoTask(todoTaskEditText.editableText.toString())
        ToDoDatabaseQueryManager.getInstance().saveToDoTask(toDoTask)
//        var intent = Intent()
//        intent.putExtra(Constants.TASK, toDoTask)
//        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
