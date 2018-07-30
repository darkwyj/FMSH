package com.fmsh.rfid.rfidnfc.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.fmsh.rfid.rfidnfc.R;
import com.fmsh.rfid.rfidnfc.utils.LogUtil;
import com.fmsh.rfid.rfidnfc.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by wuyajiang on 2018/3/7.
 */
public class SplashActivity extends BaseActivity  {

    @Override
    public int getlayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        hideToolbar();

        jumpMainPage();

    }

    private void jumpMainPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class, null);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

}
