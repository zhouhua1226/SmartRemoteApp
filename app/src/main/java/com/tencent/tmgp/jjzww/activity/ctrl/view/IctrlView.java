package com.tencent.tmgp.jjzww.activity.ctrl.view;

import com.videogo.openapi.bean.EZCameraInfo;

import java.util.List;

/**
 * Created by zhouh on 2017/9/7.
 */
public interface IctrlView {

    void getEZCameraInfo(EZCameraInfo info);
    void getEZError(int errorcode);

    void getTime(int time);
    void getTimeFinish();

    void getUserInfos(List<String> list);

    void getRecordErrCode(int code);
    void getRecordSuecss();
    void getRecordAttributetoNet(String time, String name);
}
