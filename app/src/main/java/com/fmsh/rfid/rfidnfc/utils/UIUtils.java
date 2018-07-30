package com.fmsh.rfid.rfidnfc.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.fmsh.rfid.rfidnfc.app.MyApp;

/**
 * Created by wuyajiang on 2018/3/7.
 */
public class UIUtils {
    /**
     * 获取全局上下文
     * @return
     */
    public static Context getContext(){
        return MyApp.context;
    }

    /**
     * 获取资源文件
     * @return
     */
    public static Resources getResources(){
        return getContext().getResources();
    }

    /**
     * 获取资源文件字符串
     * @param id
     * @return
     */
    public static String getString(int id){
        return getResources().getString(id);
    }
    public static int getColor(int id){
        return getResources().getColor(id);
    }
    public static Drawable getDrawable(int id){
        return getResources().getDrawable(id);
    }


}
