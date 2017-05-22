package com.siriporn.dogfindertest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.siriporn.dogfindertest.Models.User;

/**
 * Created by siriporn on 18/1/2560.
 */

public class DogFinderApplication extends Application{
    private static Context mContext;
    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Oncreate","Success");
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static User getUser() { return user; }

    public static void setUser(User userAcc) { user = userAcc; }
}
