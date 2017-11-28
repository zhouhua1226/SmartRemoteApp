/*
 * Copyright (C) 2017 Gatz.
 * All rights, including trade secret rights, reserved.
 */
package com.game.smartctrlapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.gatz.netty.global.ConnectResultEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouh on 2017/3/03.
 */
public class Utils {

    public static String connectStatus = ConnectResultEvent.CONNECT_FAILURE;
    private static final boolean D = true;
    private static final String TAG_DELIMETER = "---";

    public static final String TAG_SESSION_INVALID = "TAG_SESSION_INVALID";
    public static final String TAG_CONNECT_ERR = "TAG_CONNECT_ERR";
    public static final String TAG_CONNECT_SUCESS = "TAG_CONNECT_SUCESS";
    public static final String TAG_GATEWAT_USING = "TAG_GATEWAT_USING";
    public static final String TAG_GET_DEVICE_STATUS = "TAG_GET_DEVICE_STATUS";

    public static final String TAG_MOVE_RESPONSE = "TAG_MOVE_RESPONSE";
    public static final String TAG_MOVE_FAILE = "TAG_MOVE_FAILE";
    public static final String TAG_ROOM_IN = "TAG_ROOM_IN";
    public static final String TAG_ROOM_OUT = "TAG_ROOM_OUT";

    public static final String TAG_GATEWAY_SINGLE_CONNECT = "TAG_GATEWAY_SINGLE_CONNECT";
    public static final String TAG_GATEWAY_SINGLE_DISCONNECT = "TAG_GATEWAY_SINGLE_DISCONNECT";

    public static final String TAG_DEVICE_FREE = "TAG_DEVICE_FREE";
    public static final String TAG_DEVICE_ERR = "TAG_DEVICE_ERR";

    public static final String FREE  = "free";
    public static final String BUSY= "using";

    public static final String TAG_ROOM_NAME = "room_name";
    public static final String TAG_ROOM_STATUS = "status";
    public static final String TAG_CAMERA_NAME = "camera_name";
    public static final String TAG_DOLL_GOLD="doll_gold";
    public static boolean isExit = false;

    public static String token = "";

    public static boolean isVibrator;  //是否开启震动  11/18 11:20
    public static final String HTTP_OK = "success";

    public static final int CATCH_TIME_OUT = 20;
    public static final long GET_STATUS_DELAY_TIME = 3*60*1000;
    public static final long GET_STATUS_PRE_TIME = 2*60*1000;

    public static void showLogE(String TAG, String msg) {
        if (D) {
            android.util.Log.e(TAG, TAG + TAG_DELIMETER + msg);
        }
    }

    public static int getInt(String c) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(c);
        return Integer.valueOf(m.replaceAll("").trim());
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 正则匹配电话号码
     */

    public static boolean checkPhoneREX(String value) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 得到手机识别码
     */
    public static String getIMEI(Context context) {
        TelephonyManager mTelephonyMgr = null;
        if (context != null) {
            mTelephonyMgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getDeviceId();
        }
        return "";
    }

    /**
     * 将当前时间 格式化
     */
    public static String getTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    //dp转px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     * @author SHANHY
     * @date   2015年10月28日
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
              return info.isAvailable();
            }
        }
        return false;
    }
    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean delFile(String fileName){
        File file = new File(fileName);
        if (file.isFile()) {file.delete();}
        return file.exists();
    }

    public static String getTime(String times){
        String year=times.substring(0,4);
        String month=times.substring(4,6);
        String day=times.substring(6,8);
        String hour=times.substring(8,10);
        String minte=times.substring(10,12);
        String second=times.substring(12,14);
        return year+"/"+month+"/"+day+"  "+hour+":"+minte+":"+second;
    }


}