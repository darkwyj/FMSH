package com.fmsh.rfid.rfidnfc.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmsh.rfid.rfidnfc.R;
import com.fmsh.rfid.rfidnfc.utils.LogUtil;
import com.fmsh.rfid.rfidnfc.utils.MyConstant;
import com.fmsh.rfid.rfidnfc.utils.ReadOrWriteNFCUtil;
import com.fmsh.rfid.rfidnfc.utils.UIUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.nfc.NdefRecord.TNF_WELL_KNOWN;

/**
 * Created by wuyajiang on 2018/3/7.
 */
public class WebViewNfcActivity extends BaseNfcActivity {

    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_web)
    LinearLayout llWeb;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    private boolean haveMifareClissic = false;//标签是否支持MifareClassic
    private String mTagText;
    private Html5WebView mHtml5WebView;
    private WebView webView;
    private NdefRecord record;
    private Map<String,String> map= new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_nfc);
        ButterKnife.bind(this);
        LogUtil.i(getClass().getName() + "onCreate");
        String[] split = MyConstant.MyString.DATA.split(",");
        String[] split1 = MyConstant.MyString.UID.split(",");

        initView();
        for (int j = 0; j < 3 ; j++) {
            map.put(split[j],split1[j]);
        }
    }

    private void initView() {
        mHtml5WebView = new Html5WebView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mHtml5WebView.setLayoutParams(layoutParams);
        llWeb.addView(mHtml5WebView);
        mHtml5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mHtml5WebView.setVisibility(View.VISIBLE);
            }
        });


    }

    int i = 0;

    @Override
    protected void onPause() {
        super.onPause();
        tvInfo.setText("");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        i++;
        //        llWeb.setVisibility(View.INVISIBLE);
        LogUtil.i(getClass().getName() + "onNewIntent");
        //获取tag
        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //获取ndef (nfc data exchange format)
        Ndef ndef = Ndef.get(mTag);
        NdefFormatable ndefFormatable = NdefFormatable.get(mTag);

        byte[] id = mTag.getId();

        mTagText = bytesToHexString(id);
        LogUtil.i("getId         "+mTagText );

        LogUtil.i("mTagText    " + mTagText + "    " + i);
        if (mTagText != null && !mTagText.isEmpty()) {
//            tvMessage.setText(mTagText);
            //            LogUtil.i("mTagText    " + mTagText +"    "+i);
            if (checkID(mTagText)) {

                tvInfo.setText(UIUtils.getString(R.string.web_text1));
                tvInfo.setTextColor(UIUtils.getColor(R.color.black));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //                            llWeb.setVisibility(View.VISIBLE);
                        //                            mHtml5WebView.loadUrl(mTagText);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", mTagText);
                        //                            startActivity(WebViewActivity.class, bundle);
                        Intent intent1 = new Intent(WebViewNfcActivity.this, WebViewActivity.class);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                    }

                }, 1500);
            } else {
                showErrorInfo();
            }
        } else {
            showErrorInfo();
        }
    }

    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
    private boolean checkReadInfo(String str) {
        return mTagText != null && !mTagText.isEmpty() ? true : false;
    }

    private void showErrorInfo() {
        llWeb.setVisibility(View.GONE);
        tvInfo.setText(UIUtils.getString(R.string.web_text2));
        tvInfo.setTextColor(UIUtils.getColor(R.color.red));
    }

    private boolean checkInfo(String str) {
        String[] split = MyConstant.MyString.UID.split(",");
        for (String string : split) {
            if (str.equalsIgnoreCase(str.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkID(String str){
        String[] split = MyConstant.MyString.UID.split(",");
        if(split[0].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[0];
            return true;
        }else if(split[1].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[1];
            return true;
        }else if(split[2].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[2];
            return true;
        }else if(split[3].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[3];
            return true;
        }else if(split[4].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[4];
            return true;
        }else if(split[5].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[5];
            return true;
        }else if(split[6].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[6];
            return true;
        }else if(split[7].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[7];
            return true;
        }else if(split[8].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[8];
            return true;
        }else if(split[9].equalsIgnoreCase(str.trim())){
            mTagText = MyConstant.MyString.DATA.split(",")[9];
            return true;
        }
        return false;
    }

    private boolean checkTable(Tag mtag){
        String[] techList = mtag.getTechList();
        for (String str : techList) {
            if (str.contains("NdefFormatable")) {

                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHtml5WebView != null) {
            ViewParent parent = mHtml5WebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeAllViews();
            }
            mHtml5WebView.clearHistory();
            mHtml5WebView.removeAllViews();
            mHtml5WebView.destroy();
        }
    }

    private void readNdefFormatable( Tag mtag ,Intent intent){
        NdefFormatable ndefFormatable = NdefFormatable.get(mtag);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        byte[] bytes = new byte[1024];
        NdefMessage msgs[] = null;
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
            }
            if (msgs != null) {
                record = msgs[0].getRecords()[0];

            }
        }
        try {
            NdefMessage ndefMessage = new NdefMessage(bytes);
            if(ndefFormatable != null){
                try {
                    ndefFormatable.connect();
                    ndefFormatable.formatReadOnly(ndefMessage);
                    NdefRecord[] records = ndefMessage.getRecords();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String s = ndefFormatable.toString();
                LogUtil.i("readNdefFormatable"+s);

            }
        } catch (FormatException e) {
            e.printStackTrace();
        }

//

//        try {
//            NdefMessage ndefMessage = new NdefMessage(record);
//            if(ndefFormatable != null)
//                try {
//                    ndefFormatable.connect();
//                    ndefFormatable.formatReadOnly(ndefMessage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        } catch (FormatException e) {
//            e.printStackTrace();
//        }

    }
}
