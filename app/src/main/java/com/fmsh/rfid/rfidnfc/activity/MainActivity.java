package com.fmsh.rfid.rfidnfc.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.RadioButton;

import com.fmsh.rfid.rfidnfc.R;
import com.fmsh.rfid.rfidnfc.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {



    @BindView(R.id.rb_nfc)
    RadioButton rbNfc;
    @BindView(R.id.rb_scan)
    RadioButton rbScan;

    @Override
    public int getlayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        hideToolbar();
        rbNfc.setOnClickListener(this);
        rbScan.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rb_nfc:
                // TODO: 2018/3/7 nfc功能界面
                startActivity(WebViewNfcActivity.class, null);
                break;
            case R.id.rb_scan:
                // TODO: 2018/3/7 跳转到二维码扫描页面
                checkPermission();
                break;
        }
    }
    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if( EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)){
                startActivity(QrCodeActivity.class,null);
            }else {
                EasyPermissions.requestPermissions(this, UIUtils.getString(R.string.main_text3),111,Manifest.permission.CAMERA);
            }
        }else {
            startActivity(QrCodeActivity.class,null);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startActivity(QrCodeActivity.class,null);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        new AppSettingsDialog.Builder(this).setRationale(UIUtils.getString(R.string.main_text3)).setTitle(UIUtils.getString(R.string.text_title))
                .setPositiveButton(UIUtils.getString(R.string.text_yes))
                .setNegativeButton(UIUtils.getString(R.string.text_no)).build().show();
    }
}
