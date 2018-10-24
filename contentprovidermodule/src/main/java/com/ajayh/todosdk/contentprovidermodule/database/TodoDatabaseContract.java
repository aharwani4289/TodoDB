package com.ajayh.todosdk.contentprovidermodule.database;

import android.provider.BaseColumns;

public class TodoDatabaseContract {

    public static class ToDoTable implements BaseColumns {

        public static final String TABLE_NAME = "TODO_TABLE";
        public static final String TODO_TASK = "TODO_TASK";
        public static final String TODO_TASK_IS_COMPLETED = "TODO_TASK_IS_COMPLETED";
    }
}
