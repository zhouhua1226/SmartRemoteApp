package com.tencent.tmgp.jjzww.model.http;

import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.PlayBackBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.Token;
import com.tencent.tmgp.jjzww.utils.UrlUtils;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
            @Field(UrlUtils.NICKNANME) String nickname
    );

    //修改用户名   11/21 13：15
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.USERNAMEURL)
        Observable<Result<AppUserBean>> getUserName(
            @Field(UrlUtils.PHONE) String phone,
            @Field(UrlUtils.NICKNANME) String nickName
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
            @Field(UrlUtils.ID) int id,
            @Field(UrlUtils.TIME) String time,
            @Field(UrlUtils.NICKNANME) String nickName,
            @Field(UrlUtils.STATE) String state,
            @Field(UrlUtils.DOLLNAME) String dollname


    );

    //获取视频回放列表
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.VIDEOBACKURL)
    Observable<Result<LoginInfo>> getVideoBackList(
            @Field(UrlUtils.NICKNANME) String name
    );

    //获取房间用户头像
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CTRLUSERIMAGE)
    Observable<Result<AppUserBean>> getCtrlUserImage(
            @Field(UrlUtils.USERNAME) String phone
    );

    //下注
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.BETSURL)
    Observable<Result<AppUserBean>> getBets(
            @Field(UrlUtils.USERID) String userID,
            @Field(UrlUtils.WAGER) Integer wager,
            @Field(UrlUtils.GUESSKEY) String guessKey,
            @Field(UrlUtils.PLAYBACK) Integer playBackId,
            @Field(UrlUtils.DOLLID) String dollID
    );

        //跑马灯
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET(UrlUtils.getUserList)
    Observable<Result<LoginInfo>> getUserList();

    //开始游戏分发场次
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.CREATPLAYLISTURL)
    Observable<Result<LoginInfo>> getCreatPlayList(
            @Field(UrlUtils.NICKNANME) String nickName,
            @Field(UrlUtils.DOLLNAME) String dollName
    );

    //围观群众分发场次
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST(UrlUtils.PLAYIDURL)
    Observable<Result<LoginInfo>> getPlayId(
            @Field(UrlUtils.DOLLNAME) String dollName
    );

}
