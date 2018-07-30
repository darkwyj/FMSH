package com.fmsh.rfid.rfidnfc.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.fmsh.rfid.rfidnfc.R;
import com.fmsh.rfid.rfidnfc.utils.LogUtil;
import com.fmsh.rfid.rfidnfc.utils.ToastUtil;
import com.fmsh.rfid.rfidnfc.utils.UIUtils;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by wuyajiang on 2018/3/8.
 */
public class QrCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    @Override
    public int getlayoutID() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        mQRCodeView.setDelegate(this);

        setToolbarTitle("Scan");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        //        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtil.i(result);
        vibrate();
        if (result != null && !result.isEmpty() && result.contains("http")) {
            //            ToastUtil.sToastUtil.longDuration(UIUtils.getString(R.string.web_text1).split("!")[0]);
            Bundle bundle = new Bundle();
            bundle.putString("url", result);
            startActivity(WebViewActivity.class, bundle);
        } else {
            if (result != null && !result.isEmpty()) {
                ToastUtil.sToastUtil.longDuration(result);
            }
        }
        //        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        LogUtil.e("打开相机出错");

    }

    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //
    //        mQRCodeView.showScanRect();
    //
    //        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
    //            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
    //
    //            /*
    //            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
    //            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
    //             */
    //            new AsyncTask<Void, Void, String>() {
    //                @Override
    //                protected String doInBackground(Void... params) {
    //                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
    //                }
    //
    //                @Override
    //                protected void onPostExecute(String result) {
    //                    if (TextUtils.isEmpty(result)) {
    //                        ToastUtil.sToastUtil.longDuration("未发现二维码");
    //                    } else {
    //                        ToastUtil.sToastUtil.longDuration(result);
    //                    }
    //                }
    //            }.execute();
    //        }
    //    }

}
