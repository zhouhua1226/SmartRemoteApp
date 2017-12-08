package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by hongxiu on 2017/12/7.
 */
public class GetPlayIdBean implements Serializable {

    /**
     * playBack : {"DOLLTOTAL":0,"POSTSTATE":"0","DOLLNAME":"宇智波佐助","STATE":"0","SENDGOODS":"","USERNAME":"百步飞剑","COUNT":"","ID":1472,"CONVERSIONGOLD":80,"CREATETIME":"20171207112829","DOLL_URL":""}
     */

    private PlayBackBean playBack;

    public PlayBackBean getPlayBack() {
        return playBack;
    }

    public void setPlayBack(PlayBackBean playBack) {
        this.playBack = playBack;
    }

    public static class PlayBackBean {
        /**
         * DOLLTOTAL : 0
         * POSTSTATE : 0
         * DOLLNAME : 宇智波佐助
         * STATE : 0
         * SENDGOODS :
         * USERNAME : 百步飞剑
         * COUNT :
         * ID : 1472
         * CONVERSIONGOLD : 80
         * CREATETIME : 20171207112829
         * DOLL_URL :
         */

        private int DOLLTOTAL;
        private String POSTSTATE;
        private String DOLLNAME;
        private String STATE;
        private String SENDGOODS;
        private String USERNAME;
        private String COUNT;
        private int ID;
        private int CONVERSIONGOLD;
        private String CREATETIME;
        private String DOLL_URL;

        public int getDOLLTOTAL() {
            return DOLLTOTAL;
        }

        public void setDOLLTOTAL(int DOLLTOTAL) {
            this.DOLLTOTAL = DOLLTOTAL;
        }

        public String getPOSTSTATE() {
            return POSTSTATE;
        }

        public void setPOSTSTATE(String POSTSTATE) {
            this.POSTSTATE = POSTSTATE;
        }

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

        public String getSENDGOODS() {
            return SENDGOODS;
        }

        public void setSENDGOODS(String SENDGOODS) {
            this.SENDGOODS = SENDGOODS;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getCOUNT() {
            return COUNT;
        }

        public void setCOUNT(String COUNT) {
            this.COUNT = COUNT;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getCONVERSIONGOLD() {
            return CONVERSIONGOLD;
        }

        public void setCONVERSIONGOLD(int CONVERSIONGOLD) {
            this.CONVERSIONGOLD = CONVERSIONGOLD;
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
}
