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
    public static final String FACEIMAGE="base64Image";
    public static final String NickName="nickname";
    public static final String USERNAME="userName";
    public static final String USEPAYMONEY="money";
    public static final String USERPLAYNUM="gold";

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

    //修改用户名  11/21 13：10
    public static final String USERNAMEURL=URL+"/pooh-web/api/updateUserName";

    //头像上传成功返回的http://106.75.142.42:8080/faceImage/15335756655.png
    public static final String USERFACEIMAGEURL="http://47.100.15.18:8080/faceImage";

    //充值接口
    public static final String USERPAYURL=URL+"/pooh-web/pay/balance";

    //消费接口
    public static final String USERPLAYURL=URL+"/pooh-web/pay/costBalance";


}
