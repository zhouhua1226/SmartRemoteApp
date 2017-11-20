package com.game.smartremoteapp.base;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

//import com.facebook.drawee.backends.pipeline.Fresco;
import com.game.smartremoteapp.service.SmartRemoteService;
import com.game.smartremoteapp.utils.EZUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.videogo.openapi.EZOpenSDK;

/**
 * Created by zhouh on 2017/9/7.
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        videoCtrlSettings();
        startCoreService();
        getPushAgent();

    }

    private void getPushAgent() {

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("MyDeviceToken",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        mPushAgent.setDebugMode(false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void startCoreService() {
        Intent it = new Intent();
        it.setClass(getApplicationContext(), SmartRemoteService.class);
        startService(it);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    private void videoCtrlSettings(){
        EZOpenSDK.initLib(this, EZUtils.APP_KEY, "");
    }


    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }

    public void finishProcess() {
        System.exit(0);
    }


}
