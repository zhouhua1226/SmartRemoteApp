package com.game.smartremoteapp.activity.home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.game.smartremoteapp.R;
import com.game.smartremoteapp.bean.LoginInfo;
import com.game.smartremoteapp.bean.Result;
import com.game.smartremoteapp.model.http.HttpManager;
import com.game.smartremoteapp.model.http.RequestSubscriber;
import com.game.smartremoteapp.utils.Base64;
import com.game.smartremoteapp.utils.BitmapUtils;
import com.game.smartremoteapp.utils.Utils;
import com.jph.takephoto.model.CropOptions;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by hongxiu on 2017/9/26.
 */
public class TakePhotoActivity extends com.jph.takephoto.app.TakePhotoActivity implements View.OnClickListener {
    private static final String TAG = "TakePhotoActivity-";
    private TextView shootTv;
    private TextView photoTv;
    private TextView cancelTv;
    private Uri imageUri;
    private CropOptions cropOptions;
    private File file;
    private String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picture);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        shootTv = (TextView) findViewById(R.id.shoot_tv);
        photoTv = (TextView) findViewById(R.id.photo_tv);
        cancelTv = (TextView) findViewById(R.id.cancel_tv);
        shootTv.setOnClickListener(this);
        photoTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);

        file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        imageUri=Uri.fromFile(file);
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shoot_tv:
                getTakePhoto().onPickFromCaptureWithCrop(imageUri,cropOptions);

                break;
            case R.id.photo_tv:
                getTakePhoto().onPickFromDocumentsWithCrop(imageUri, cropOptions);
//                getTakePhoto().onPickFromGalleryWithCrop(imageUri,cropOptions);
                break;
            case R.id.cancel_tv:
                finish();
                break;
        }
    }

    @Override
    public void takeSuccess(String imagePath) {
        Utils.showLogE(TAG,imagePath);
        String string="15335756655";
        getFaceImage(string,imagePath);
    }


    private void getFaceImage(String phone,String base64Image){
        String stringphone=android.util.Base64.encodeToString(phone.getBytes(), android.util.Base64.DEFAULT);
        Log.e("@@@@"+"#####",stringphone);
        Bitmap bitmap= BitmapUtils.compressImageFromFile(base64Image);
        String base64=Base64.encode(BitmapUtils.compressBmpFromBmp(bitmap));
        HttpManager.getInstance().getFaceImage(stringphone, base64, new RequestSubscriber<Result>() {
            @Override
            public void _onSuccess(Result result) {
                Utils.showLogE(TAG,result.getMsg());

            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "getFaceImage::::" + e.getMessage());
            }
        });

    }



    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
