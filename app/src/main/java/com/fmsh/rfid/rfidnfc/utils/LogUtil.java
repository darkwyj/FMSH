package com.fmsh.rfid.rfidnfc.utils;

import android.util.Log;

import com.fmsh.rfid.rfidnfc.BuildConfig;


/**
 * Created by wuyajiang on 2018/3/7.
 */
public class LogUtil {
    private static boolean isDebug = BuildConfig.DEBUG; //一次性关闭所有log
    private static final String TAG = "LogUtil";

    /**
     * log.i
     *
     * @param msg
     */
    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG,msg);
        }
    }

    /**
     * log.d
     *
     * @param msg
     */
    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG,msg);
        }
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG,msg);
        }
    }

    /**
     * log.w
     *
     * @param msg
     */
    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG,msg);
        }
    }

    /**
     * log.v
     *
     * @param msg
     */
    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG,msg);
        }
    }

}