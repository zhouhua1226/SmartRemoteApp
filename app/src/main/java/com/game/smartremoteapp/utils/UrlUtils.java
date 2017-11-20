package com.game.smartremoteapp.utils;

/**
 * Created by zhouh on 2017/9/8.
 */
public class UrlUtils {
    //视频url
    public static final String VIDEO_ROOT_URL = "https://open.ys7.com/";
    public static final String VIDEO_GET_TOKEN = "api/lapp/token/get";
    //getToken
    public static final String APPKEY = "appKey";
    public static final String APPSECRET = "appSecret";

    public static final String PHONE = "phone";
    public static final String SMSCODE = "code";
    public static final String FACEIMAGE="faceimage";
    public static final String NickName="nickname";


    public static final String URL="http://47.100.15.18:8080";
//    public static final String URL="http://106.75.142.42:8080/";

    //getSmsCode
    public static final String GETSMSCODE = "http://47.100.15.18:8080/pooh-web/sms/getRegSMSCode";//"http://controller.ngrok.cc/m/sms/getSMSCodeLogin";
    //login
    public static final String LOGIN = "http://47.100.15.18:8080/pooh-web/sms/getSMSCodeLogin";//"http://controller.ngrok.cc/m/sms/getRegSMSCode";
    //login without code
    public static final String LOGINWITHOUTCODE = "http://47.100.15.18:8080/pooh-web/sms/getDoll";
    //完整的URL：http://106.75.142.42:8080/pooh-web/uploadFiles/DollImage/A002201710310002.jpg
    public static final String PICTUREURL="http://47.100.15.18:8080/pooh-web/uploadFiles/DollImage";

    //头像上传http://47.100.15.18:8080/pooh-web/api/updateUser
    public static final String FACEIMAGEURL="http://47.100.15.18:8080/pooh-web/api/updateUser";

    //修改昵称
    public static final String UserNickNameURL="http;//47100.15.18:8080/faceImage";
}
