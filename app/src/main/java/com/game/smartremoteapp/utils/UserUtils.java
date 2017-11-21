package com.game.smartremoteapp.utils;

import com.gatz.netty.AppClient;
import com.gatz.netty.UserInfo;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.utils.NettyUtils;

/**
 * Created by zhouh on 2017/9/19.
 */
public class UserUtils {
    //SP_TAG
    public static final String SP_TAG_LOGIN = "SP_TAG_LOGIN";
    public static final String SP_TAG_PHONE = "SP_TAG_PHONE";

    public static String UserNickName = "";
    public static String UserImage="";

    public static void setNettyInfo(String sessionId, String phone, String roomId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSessionId(sessionId);
        userInfo.setUserName(phone);
        userInfo.setRoomid(roomId);
        userInfo.setUnitId("UNI1611090002765");
        AppGlobal.getInstance().setUserInfo(userInfo);
    }

    public static void doNettyConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NettyUtils.socketTag) {
                        AppClient.getInstance().doConnect(UserNickName);
                        break;
                    }
                    if (Utils.isExit) {
                        break;
                    }
                }
            }
        }).start();
    }

    public static void doGetDollStatus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NettyUtils.socketTag) {
                        NettyUtils.sendGetDeviceStatesCmd();
                        break;
                    }
                    if (Utils.isExit) {
                        break;
                    }
                }
            }
        }).start();
    }
}
