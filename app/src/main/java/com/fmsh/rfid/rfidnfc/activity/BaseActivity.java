package com.fmsh.rfid.rfidnfc.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fmsh.rfid.rfidnfc.R;
import com.fmsh.rfid.rfidnfc.utils.LogUtil;
import com.fmsh.rfid.rfidnfc.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by pc on 2018/1/8.
 * 基类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public Context mContext;
    private PermissionListener mPermissionListener;
    private static final int CODE_REQUEST_PERMISSION = 1;
    private MyReceiver myReceiver;
    private static final int[] ATTRS = {R.attr.windowActionBarOverlay, R.attr.actionBarSize};
    protected View mRootView;
    private LayoutInflater mInflater;
    protected View mContentView;
    protected Toolbar mToolbar;
    private int idDinmes = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        if(getlayoutID() != -1){
            setContentView(getlayoutID());
        }
        this.mContext = this;
        setBaseSetting();
        this.initView();
        this.initData();
        registBack();
        LogUtil.i("BaseActivity    "+getClass().getName());
//        if(!getClass().getName().equals("com.hoppingcity.shop.webview.WebViewActivity")){
//            StatusBarUtils.setStuatusTextColor(this);
//        }
//        StatusBarUtils.setStuatusTextColor(this);


    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mRootView = initRootView(layoutResID);
        super.setContentView(mRootView);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        mRootView = initRootView(view);
        super.setContentView(mRootView);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mRootView = initRootView(view);
        super.setContentView(mRootView, params);
        ButterKnife.bind(this);
    }

    protected View getRootView() {
        return mRootView;
    }


    private  void setBaseSetting(){
//        httpRequest = HttpRequest.getInstance();
//        gson = new Gson();
//        mDialogUtils = new DialogUtils(this);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(MyConstant.Str.finishActivity);
//        myReceiver = new MyReceiver();
//        registerReceiver(myReceiver,filter);
    }

    public abstract int getlayoutID();
    protected abstract void initView();
    protected abstract void initData();


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         *
         *   其实理解的简单一点，无论什么模式，只有activity是同一个实例的情况下，intent发生了变化，就会进入onNewIntent中，
         *   这个方法的作用也是让你来对旧的intent进行保存，对新的intent进行对应的处理
         */
        LogUtil.i("onNewIntent");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level){
            case TRIM_MEMORY_UI_HIDDEN:
                break;
        }
        /**
         * 当ui隐藏的时候释放UI资源,释放内存
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this);
        if(myReceiver != null){
            unregisterReceiver(myReceiver);
        }
    }

    private void registBack() {
//        TextView btn_back= (TextView) findViewById(R.id.btn_back);
//        if(btn_back != null){
//            btn_back.setOnClickListener(this);
//        }

    }
    @Override
    public void onClick(View view) {

    }

    /**
     * 隐藏toolbar
     */
    protected void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 显示toolbar
     */
    protected void showToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    /**
     * toolbar是否显示
     */
    protected boolean isToolbarShowing() {
        ActionBar actionBar = getSupportActionBar();
        return actionBar != null && actionBar.isShowing();
    }


    /**
     * Toolbar左侧按钮点击事件（默认退出当前页面）
     *
     * @param v view
     */
    protected void onToolbarLeftButtonClicked(View v) {
        finish();
    }

    /**
     * Toolbar右侧按钮点击事件
     *
     * @param v view
     */
    protected void onToolbarRightButtonClicked(View v) {

    }

    /**
     * 设置toolbar标题
     *
     * @param title 标题
     */
    protected void setToolbarTitle(String title) {
        if (mToolbar != null) {
            TextView titleView = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            titleView.setText(title);
        }
    }

    protected void setToolbarRightImage(@DrawableRes int drawableId) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_right_btn);
            rightView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0);
            rightView.setVisibility(View.VISIBLE);
        }
    }

    protected void setToolbarRightColor(int color) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_right_btn);
            rightView.setTextColor(color);
            rightView.setVisibility(View.VISIBLE);
        }
    }

    protected void isShowToolbarLeftImage(boolean isShow) {
        TextView leftView = (TextView) mToolbar.findViewById(R.id.toolbar_left_btn);
        if (isShow) {
            leftView.setVisibility(View.INVISIBLE);
        } else {
            leftView.setVisibility(View.VISIBLE);
        }
    }

    protected void setToolbarRightText(String str) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_right_btn);
            rightView.setText(str);
            rightView.setVisibility(View.VISIBLE);
        }
    }

    protected void setToolbarLeftText(String str) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_left_btn);
            rightView.setText(str);
            rightView.setVisibility(View.VISIBLE);
        }
    }

    protected void setToolbarLeftTextImage(int left) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_left_btn);
            rightView.setCompoundDrawablesWithIntrinsicBounds(left, 0, 0, 0);
            rightView.setVisibility(View.VISIBLE);
        }
    }

    protected void setToolbarLeftTextVisible(boolean enable) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_left_btn);
            if (enable) {
                rightView.setVisibility(View.VISIBLE);
            } else {
                rightView.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected TextView getToolbarRightTextView() {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_right_btn);
            rightView.setVisibility(View.VISIBLE);
            return rightView;
        }
        return null;
    }

    protected void setToolbarRightTextSize(int size) {
        if (mToolbar != null) {
            TextView rightView = (TextView) mToolbar.findViewById(R.id.toolbar_right_btn);
            rightView.setTextSize(size);
            rightView.setVisibility(View.VISIBLE);
        }
    }

    protected void setToolbarBackGround(int color) {
        if (mToolbar != null) {
            mToolbar.setBackgroundColor(color);
        }
    }



    /**
     * 设置toolbar的分割线是否有效， 默认有效。
     *
     * @param enable 分割线是否有效
     */
    protected void setToolbarDividerEnable(boolean enable) {
        if (mToolbar != null) {
            View divider = mToolbar.findViewById(R.id.toolbar_divider_line);
            if (enable) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 创建toolbar
     *
     * @param inflater  inflater
     * @param container container
     * @return 创建toolbar, 如果返回null则不显示toolbar
     */
    protected View createToolbarView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.layout_toolbar, container);
        view.findViewById(R.id.toolbar_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarLeftButtonClicked(v);
            }
        });
        view.findViewById(R.id.toolbar_right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarRightButtonClicked(v);
            }
        });

        return view;
    }

    private View initRootView(View contentView) {
        /*直接创建一个相对布局，作为视图容器的父容器*/
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setFitsSystemWindows(true);

        /*实际的内容布局*/
        mContentView = contentView;
        RelativeLayout.LayoutParams contentLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        /*通过inflater获取toolbar的布局文件*/
        View toolbarLayout = createToolbarView(mInflater, null);
        if (toolbarLayout != null) {
            TypedArray typedArray = getTheme().obtainStyledAttributes(ATTRS);
            /*获取主题中定义的悬浮标志*/
            boolean overly = typedArray.getBoolean(0, false);
            /*获取主题中定义的toolbar的高度*/
            @StyleableRes int index = 1;

            int toolBarSize = getToolbarHeight();
//            int toolBarSize =  (int) typedArray.getDimension(index, (int) getResources().getDimension(R.dimen.action_bar_default_height))
            typedArray.recycle();

            if (!overly) {
                contentLayoutParams.addRule(RelativeLayout.BELOW, toolbarLayout.getId());
            }
            rootView.addView(mContentView, contentLayoutParams);
            RelativeLayout.LayoutParams toolbarLayoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, toolBarSize);
            rootView.addView(toolbarLayout, toolbarLayoutParams);

            mToolbar = (Toolbar) toolbarLayout.findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        } else {
            rootView.addView(mContentView, contentLayoutParams);
        }
        return rootView;
    }

    public void setToolbarHeight(int id) {
        idDinmes = id;
    }

    private int getToolbarHeight() {
        return idDinmes != 0 ? (int) getResources().getDimension(idDinmes) : (int) getResources().getDimension(R.dimen.action_bar_default_height);
    }

    private View initRootView(@LayoutRes int contentViewResID) {
        return initRootView(mInflater.inflate(contentViewResID, null));
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * 有回调的activity跳转
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 读取状态栏的高度
     *
     * @param context
     * @return
     */

    protected int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
        } else {
            return 0;
        }
    }

    /**
     * 申请权限
     */
    public interface PermissionListener {
        void onGranted();
        void onDenied(List<String> deniedPermissions);
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case CODE_REQUEST_PERMISSION:
//                if (grantResults.length > 0) {
//                    List<String> deniedPermissions = new ArrayList<>();
//                    for (int i = 0; i < grantResults.length; i++) {
//                        int result = grantResults[i];
//                        if (result != PackageManager.PERMISSION_GRANTED) {
//                            String permission = permissions[i];
//                            deniedPermissions.add(permission);
//                        }
//                    }
//                    if (deniedPermissions.size() == grantResults.length ) {
//                        mPermissionListener.onGranted();
//                    } else {
//                        mPermissionListener.onDenied(deniedPermissions);
//                    }
//                }
//                break;
//
//            default:
//                break;
//        }
//    }
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }


    /**
     * 5.0以上系统状态栏透明
     */
    public void setTranslucentStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    protected void setFullScreen(){
        // 将activity设置为全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
