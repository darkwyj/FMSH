package com.fmsh.rfid.rfidnfc.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.fmsh.rfid.rfidnfc.utils.LogUtil;
import com.fmsh.rfid.rfidnfc.utils.ToastUtil;

/**
 * Created by wuyajiang on 2018/3/7.
 */
public class BaseNfcActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;
    public Tag mTag;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            ToastUtil.sToastUtil.shortDuration("该设备不支持NFC功能!");
            finish();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {//判断设备是否打开Nfc
            Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(setNfc);;
            finish();
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (mTag == null) {
            return;
        }
        String[] techList = mTag.getTechList();
        for (String tech : techList) {
            LogUtil.i("mTag    "+tech);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            //打开前台发布系统，使页面优于其它nfc处理.当检测到一个Tag标签就会执行mPendingItent
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);////关闭前台发布系统
        }
    }
}
