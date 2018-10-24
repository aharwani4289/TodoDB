package com.ajayh.todosdk.contentprovidermodule;

import android.app.Application;
import android.content.Context;

public class ToDoSdkApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
