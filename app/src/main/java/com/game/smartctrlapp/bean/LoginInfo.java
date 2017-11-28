package com.game.smartctrlapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouh on 2017/11/1.
 */
public class LoginInfo implements Serializable{
    private String accessToken;
    private String sessionID;
    private UserBean appUser;
    private List<ZwwRoomBean> dollList;
    private List<VideoBackBean> playback;

    public List<VideoBackBean> getPlayback() {
        return playback;
    }

    public void setPlayback(List<VideoBackBean> playback) {
        this.playback = playback;
    }


    public void setAppUser(UserBean appUser) {
        this.appUser = appUser;
    }

    public void setDollList(List<ZwwRoomBean> dollList) {
        this.dollList = dollList;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<ZwwRoomBean> getDollList() {
        return dollList;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserBean getAppUser() {
        return appUser;
    }
}
