package com.tencent.tmgp.jjzww.model.http;

import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.Token;
import com.tencent.tmgp.jjzww.utils.UrlUtils;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhouh on 2017/9/8.
 */
public interface SmartService {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.VIDEO_GET_TOKEN)
    Observable<Result<Token>> getVideoToken(
            @Field(UrlUtils.APPKEY) String appKey,
            @Field(UrlUtils.APPSECRET) String appSecret);

    //登录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGIN)
    Observable<Result<LoginInfo>> getLogin(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.SMSCODE) String code
    );


    //验证码
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.GETSMSCODE)
    Observable<Result<Token>> getCode(
            @Field(UrlUtils.PHONE) String phone);

    //不需要验证码直接登录
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.LOGINWITHOUTCODE)
    Observable<Result<LoginInfo>> getLoginWithOutCode(
            @Field(UrlUtils.PHONE) String phone);

    //头像上传
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.FACEIMAGEURL)
    Observable<Result<AppUserBean>> getFaceImage(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.FACEIMAGE) String base64Image
    );

    //修改昵称
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.UserNickNameURL)
    Observable<Result> getNickName(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.NickName) String nickname
    );

    //修改用户名   11/21 13：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERNAMEURL)
        Observable<Result<AppUserBean>> getUserName(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.USERNAME) String userName
    );

    //充值   11/22 15：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERPAYURL)
    Observable<Result<LoginInfo>> getUserPay(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.USEPAYMONEY) String money
    );

    //消费   11/22 16：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERPLAYURL)
    Observable<Result<LoginInfo>> getUserPlayNum(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.USERPLAYNUM) String gold
    );

    //ListRank
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.LISTRANKURL)
    Observable<Result<ListRankBean>> getListRank();

    //视屏上传
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.UPLOADURL)
    Observable<Result<LoginInfo>> getRegPlayBack(
            @Field(UrlUtils.USERNAME) String name,
            @Field(UrlUtils.TIME) String time,
            @Field(UrlUtils.DOLLNAME) String dollname
    );

    //获取视频回放列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.VIDEOBACKURL)
    Observable<Result<LoginInfo>> getVideoBackList(
            @Field(UrlUtils.USERNAME) String name
    );

}