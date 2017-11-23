package com.game.smartremoteapp.activity.ctrl.model;

import com.videogo.openapi.bean.EZCameraInfo;

/**
 * Created by dell on 2017/9/8.
 */
public interface DeviceCallBack {

    void getEZCameraInfo(EZCameraInfo info);
    void getErrCode(int errCode);
    void getClickTime(int time);
    void getClickFinish();
    //视频回放区
    void getVideoRecordErr(int errCode);
    void getVideoSucess();
}
