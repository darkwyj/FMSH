package com.fmsh.rfid.rfidnfc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmsh.rfid.rfidnfc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuyajiang on 2018/3/8.
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_web)
    LinearLayout llWeb;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    private Html5WebView mHtml5WebView;

    @Override
    public int getlayoutID() {
        return R.layout.activity_webview_nfc;
    }
    @Override
    protected void initView() {
        hideToolbar();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mHtml5WebView = new Html5WebView(mContext);
        mHtml5WebView.setLayoutParams(layoutParams);
        llWeb.addView(mHtml5WebView);
        llImg.setVisibility(View.GONE);
        llWeb.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            String url = extras.getString("url");
            if (url != null && !url.isEmpty()) {
                mHtml5WebView.loadUrl(url);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHtml5WebView != null) {
            ViewGroup parent = (ViewGroup) mHtml5WebView.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            mHtml5WebView.clearHistory();
            mHtml5WebView.removeAllViews();
            mHtml5WebView.destroy();
        }
    }

}
