package com.ajayh.todosdk.contentprovidermodule.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.ajayh.todosdk.myapplication.datamodel.ToDoTask;

import java.util.ArrayList;
import java.util.Arrays;

public class ToDoDatabaseQueryManager {

    private static ToDoDatabaseQueryManager mInstance;

    private SQLiteDatabase mDatabase;

    private static final String TAG = "ToDoDbQueryManager";

    private ToDoDatabaseQueryManager() {
        mDatabase = TodoSqliteOpenHelper.getInstance().getWritableDatabase();
    }

    public static ToDoDatabaseQueryManager getInstance() {
        if (mInstance == null) {
            mInstance = new ToDoDatabaseQueryManager();
        }
        return mInstance;
    }

    private SQLiteDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = TodoSqliteOpenHelper.getInstance().getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * For inserting into database
     *
     * @param todoTask consists all values to be inserted
     */
    public void saveToDoTask(ToDoTask todoTask) {
        ContentValues values = returnModelToContentValue(todoTask);

        long rowID = getDatabase().insert(TodoDatabaseContract.ToDoTable.TABLE_NAME, "", values);
        if (rowID == -1) {
            Log.d(TAG, "Record insertion in db failed.");
        }
    }

    /**
     * Delete record by id
     *
     * @param id String
     */
    public boolean deleteTaskById(String id) {
        boolean ret = false;
        try {
            if (!TextUtils.isEmpty(id)) {
                int row = getDatabase().delete(TodoDatabaseContract.ToDoTable.TABLE_NAME,
                        getAccountIdSelection(TodoDatabaseContract.ToDoTable._ID + " =?"),
                        getAccountIdArgs(new String[]{id}));
                if (row != 0) {
                    ret = true;
                } else {
                    Log.d(TAG, "Item was not found in db" + id);
                }
            } else {
                Log.d(TAG, "Id is empty");
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not delete from db for ToDoTask id :: " + id + " " + e.getMessage());
        }
        return ret;
    }

    /**
     * Update completed state
     *
     * @param id
     * @param isCompleted
     */
    public synchronized void updateCompletedStateDownloaded(String id, boolean isCompleted) {
        int result = 0;
        if (id != null) {
            ContentValues values = new ContentValues();
            values.put(TodoDatabaseContract.ToDoTable.TODO_TASK_IS_COMPLETED, isCompleted);

            result = getDatabase().update(TodoDatabaseContract.ToDoTable.TABLE_NAME,
                    values,
                    getAccountIdSelection(TodoDatabaseContract.ToDoTable._ID + " =?"),
                    getAccountIdArgs(new String[]{id}));
        }
        if (result < 1) {
            Log.e(TAG, "Completed State was not updated for ToDoTask id :: " + id);
        }
    }

    /**
     * Update task
     *
     * @param id
     * @param todoTask
     */
    public synchronized void updateTaskDownloaded(String id, String todoTask) {
        int result = 0;
        if (id != null) {
            ContentValues values = new ContentValues();
            values.put(TodoDatabaseContract.ToDoTable.TODO_TASK, todoTask);

            result = getDatabase().update(TodoDatabaseContract.ToDoTable.TABLE_NAME,
                    values,
                    getAccountIdSelection(TodoDatabaseContract.ToDoTable._ID + " =?"),
                    getAccountIdArgs(new String[]{id}));
        }
        if (result < 1) {
            Log.e(TAG, "Task was not updated for ToDoTask id :: " + id);
        }
    }

    public Cursor getAllTodoTasksCursor() {
        Cursor c = commonQuery(null,
                /*TodoDatabaseContract.ToDoTable.TODO_TASK_ID + " !=? "*/null,
                /*new String[]{String.valueOf(1)}*/null,
                null);
        return c;
    }

    public ArrayList<ToDoTask> getAllTodoTasksList() {
        Cursor c = commonQuery(null,
                /*TodoDatabaseContract.ToDoTable.TODO_TASK_ID + " !=? "*/null,
                /*new String[]{String.valueOf(1)}*/null,
                null);
        return returnCursorToList(c);
    }

    private Cursor commonQuery(String[] projection, String selection,
                               String[] selectionArgs, String sortOrder) {
        return getDatabase().query(
                TodoDatabaseContract.ToDoTable.TABLE_NAME,
                projection,
                getAccountIdSelection(selection),
                getAccountIdArgs(selectionArgs), null, null, null);
    }

    /**
     * Added ACCOUNT_ID for each query to make sure it is specific to the account
     * // Updated relative to the story VI-99227 don't check account info
     * // Use account relative queries in case it should come back
     * // if (TextUtils.isEmpty(selection)) {
     * //     return IPTVDatabaseContract.Downloads.ACCOUNT_ID + " =? ";
     * // }
     * // return selection + " AND " + IPTVDatabaseContract.Downloads.ACCOUNT_ID + " =? ";
     *
     * @param selection String
     * @return String
     */
    private String getAccountIdSelection(String selection) {
        if (TextUtils.isEmpty(selection)) {
            return null;
        }
        return selection;
    }

    /*
     * Create default selection argument based on account
     * @param selectionArgs
     * @return
     */
    private String[] getAccountIdArgs(String[] selectionArgs) {
        ArrayList<String> arguments = new ArrayList<>();
        if (selectionArgs != null) {
            arguments.addAll(Arrays.asList(selectionArgs));
        }
        return arguments.toArray(new String[arguments.size()]);
    }


    /**
     * Retrieve all records in DataModel AraayList format
     *
     * @param c Cursor to read from
     * @return ArrayList of DataModel
     */
    private ArrayList<ToDoTask> returnCursorToList(Cursor c) {
        ArrayList<ToDoTask> todoTasks = new ArrayList<>();
        if (c != null) {
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    ToDoTask todoTask = new ToDoTask();
                    setToDoTask(c, todoTask);
                    todoTasks.add(todoTask);
                    c.moveToNext();
                }
            }
            c.close();
        }
        return todoTasks;
    }

    /**
     * Set all Todo Task Fields from Cursor
     *
     * @param c        Cursor
     * @param todoTask ToDoTask
     */
    private void setToDoTask(Cursor c, ToDoTask todoTask) {
        todoTask.setId(c.getInt(c.getColumnIndex(TodoDatabaseContract.ToDoTable._ID)));
        todoTask.setTodoTask(c.getString(c.getColumnIndex(TodoDatabaseContract.ToDoTable.TODO_TASK)));
        todoTask.setCompleted(c.getInt(c.getColumnIndex(TodoDatabaseContract.ToDoTable.TODO_TASK_IS_COMPLETED)) == 0);
    }

    private ContentValues returnModelToContentValue(ToDoTask toDoTask) {
        ContentValues values = new ContentValues();
        values.put(TodoDatabaseContract.ToDoTable.TODO_TASK, toDoTask.getTodoTask());
        values.put(TodoDatabaseContract.ToDoTable.TODO_TASK_IS_COMPLETED, toDoTask.isCompleted());
        return values;
    }
}