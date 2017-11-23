package com.game.smartremoteapp.activity.ctrl.presenter;

import android.content.Context;

import com.game.smartremoteapp.activity.ctrl.model.CtrlModel;
import com.game.smartremoteapp.activity.ctrl.model.DeviceCallBack;
import com.game.smartremoteapp.activity.ctrl.view.IctrlView;
import com.game.smartremoteapp.base.BasePresenter;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouh on 2017/9/7.
 */
public class CtrlCompl implements BasePresenter, ICtrlPresenter{

    private IctrlView ictrlView;
    private Context ctx;
    private CtrlModel ctrlModel;
    private DeviceCallBack callBack = new DeviceCallBack() {
        @Override
        public void getEZCameraInfo(EZCameraInfo info) {
            ictrlView.getEZCameraInfo(info);
        }

        @Override
        public void getErrCode(int errCode) {
            ictrlView.getEZError(errCode);
        }

        @Override
        public void getClickTime(int time) {
            ictrlView.getTime(time);
        }

        @Override
        public void getClickFinish() {
            ictrlView.getTimeFinish();
        }

        @Override
        public void getVideoRecordErr(int errCode) {
            ictrlView.getRecordErrCode(errCode);
        }

        @Override
        public void getVideoSucess() {
            ictrlView.getRecordSuecss();
        }

        @Override
        public void getVideoAttributetoNet(String time) {
            ictrlView.getRecordAttributetoNet(time);
        }
    };

    @Override
    public void startLoginPresent(String name) {
        ctrlModel.getEzDevice(name);
    }

    @Override
    public void destoryLoginPresent() {

    }

    public CtrlCompl(IctrlView ctrlView, Context context) {
        ictrlView = ctrlView;
        ctx = context;
        ctrlModel = new CtrlModel(context, callBack);
    }

    @Override
    public void sendCmdCtrl(MoveType moveType) {
        ctrlModel.sendCmd(moveType);
    }

    @Override
    public void startTimeCounter() {
        ctrlModel.sendTimeStart();
    }

    @Override
    public void stopTimeCounter() {
        ctrlModel.sendTimeCancle();
    }

    @Override
    public void sendCmdOutRoom() {
        ctrlModel.sendCmdOutRoom();
    }

    @Override
    public void sendGetUserInfos(String o) {
        List<String> list = new ArrayList<>();
        String[] os = o.split(";");
        for(int i = 0; i < os.length; i++) {
            list.add(os[i]);
        }
        ictrlView.getUserInfos(list);
    }

    @Override
    public void startRecordVideo(EZPlayer ezPlayer) {
        ctrlModel.sendStartSecordVideo(ezPlayer);
    }

    @Override
    public void stopRecordView(EZPlayer ezPlayer) {
        ctrlModel.stopRecordVideo(ezPlayer);
    }
}
