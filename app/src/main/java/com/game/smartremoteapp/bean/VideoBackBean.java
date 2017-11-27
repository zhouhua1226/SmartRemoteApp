package com.game.smartremoteapp.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/11/25 12:35
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class VideoBackBean implements Serializable{

    private String DOLLNAME;
    private String STATE;
    private String USERNAME;
    private String ID;
    private String CREATETIME;
    private String DOLL_URL;

    public String getDOLLNAME() {
        return DOLLNAME;
    }

    public void setDOLLNAME(String DOLLNAME) {
        this.DOLLNAME = DOLLNAME;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getDOLL_URL() {
        return DOLL_URL;
    }

    public void setDOLL_URL(String DOLL_URL) {
        this.DOLL_URL = DOLL_URL;
    }
}
