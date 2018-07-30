package com.fmsh.rfid.rfidnfc.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by wuyajiang on 2018/3/7.
 */
public class MyApp extends Application {
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }
}

