package com.game.smartremoteapp.activity.ctrl.presenter;

import com.iot.game.pooh.server.entity.json.enums.MoveType;

import java.util.Map;

/**
 * Created by zhouh on 2017/9/7.
 */
public interface ICtrlPresenter {
    void sendCmdCtrl(MoveType moveType);
    void startTimeCounter();
    void stopTimeCounter();
    void sendCmdOutRoom();
    void sendGetUserInfos(String o);
}
