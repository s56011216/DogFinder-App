package com.siriporn.dogfindertest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by siriporn on 18/1/2560.
 */

public class DogFinderApplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Oncreate","Success");
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
