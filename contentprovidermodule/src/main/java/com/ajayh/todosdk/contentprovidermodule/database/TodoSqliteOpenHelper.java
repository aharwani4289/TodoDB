package com.ajayh.todosdk.contentprovidermodule.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ajayh.todosdk.contentprovidermodule.ToDoSdkApplication;

public class TodoSqliteOpenHelper extends SQLiteOpenHelper {

    private static TodoSqliteOpenHelper mInstance;
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoDatabaseContract.ToDoTable.TABLE_NAME + " (" +
                    TodoDatabaseContract.ToDoTable._ID + " INTEGER PRIMARY KEY," +
                    TodoDatabaseContract.ToDoTable.TODO_TASK + " TEXT," +
                    TodoDatabaseContract.ToDoTable.TODO_TASK_IS_COMPLETED + " INTEGER)";

    protected static TodoSqliteOpenHelper getInstance() {
        if (mInstance == null) {
            mInstance = new TodoSqliteOpenHelper(ToDoSdkApplication.getAppContext());
        }
        return mInstance;
    }

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoDatabaseContract.ToDoTable.TABLE_NAME;

    public TodoSqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

}
