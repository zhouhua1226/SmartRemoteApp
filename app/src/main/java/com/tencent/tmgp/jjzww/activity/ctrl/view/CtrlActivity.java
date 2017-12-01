package com.tencent.tmgp.jjzww.activity.ctrl.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.global.ConnectResultEvent;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.iot.game.pooh.server.entity.json.MoveControlResponse;
import com.iot.game.pooh.server.entity.json.announce.GatewayPoohStatusMessage;
import com.iot.game.pooh.server.entity.json.app.AppInRoomResponse;
import com.iot.game.pooh.server.entity.json.app.AppOutRoomResponse;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.iot.game.pooh.server.entity.json.enums.PoohAbnormalStatus;
import com.iot.game.pooh.server.entity.json.enums.ReturnCode;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.ctrl.presenter.CtrlCompl;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.base.MyApplication;
import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.FillingCurrencyDialog;
import com.tencent.tmgp.jjzww.view.GifView;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.QuizInstrictionDialog;
import com.tencent.tmgp.jjzww.view.TimeCircleProgressView;
import com.tencent.tmgp.jjzww.view.VibratorView;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhouh on 2017/9/8. SomeCommits
 */
public class CtrlActivity extends BaseActivity implements IctrlView,
        SurfaceHolder.Callback,
        Handler.Callback {
    @BindView(R.id.realplay_sv)
    SurfaceView mRealPlaySv;
    @BindView(R.id.ctrl_gif_view)
    GifView ctrlGifView;
    @BindView(R.id.ctrl_fail_iv)
    ImageView ctrlFailIv;
    @BindView(R.id.image_back)
    ImageButton imageBack;
    //    @BindView(R.id.image_service)
//    ImageButton imageService;
    @BindView(R.id.money_image)
    ImageView moneyImage;
    //    @BindView(R.id.game_tv)
//    TextView gameTv;
    @BindView(R.id.coin_tv)
    TextView coinTv;
    @BindView(R.id.recharge_button)
    ImageButton rechargeButton;
    //    @BindView(R.id.message_button)
//    ImageButton messageButton;
    @BindView(R.id.front_image)
    ImageView topImage;
    @BindView(R.id.back_image)
    ImageView belowImage;
    @BindView(R.id.left_image)
    ImageView leftImage;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.startgame_ll)
    LinearLayout startgameLl;
    @BindView(R.id.recharge_ll)
    LinearLayout rechargeLl;
    @BindView(R.id.catch_ll)
    RelativeLayout catchLl;
    @BindView(R.id.operation_rl)
    RelativeLayout operationRl;
    @BindView(R.id.doll_name)
    TextView dollNameTv;
    @BindView(R.id.player_counter_tv)
    TextView playerCounterIv;
    @BindView(R.id.player2_iv)
    ImageView playerSecondIv;
    @BindView(R.id.main_player_iv)
    ImageView playerMainIv;
    @BindView(R.id.player_name_tv)
    TextView playerNameTv;
    @BindView(R.id.ctrl_status_iv)
    ImageView ctrlStatusIv;
    @BindView(R.id.ctrl_time_progress_view)
    TimeCircleProgressView timeCircleProgressView;
    @BindView(R.id.ctrl_dollgold_tv)
    TextView ctrlDollgoldTv;

    private static final String TAG = "CtrlActivity---";
    @BindView(R.id.ctrl_quiz_layout)
    RelativeLayout ctrlQuizLayout;
    @BindView(R.id.ctrl_instruction_image)
    ImageView ctrlInstructionImage;
    @BindView(R.id.ctrl_buttom_layout)
    RelativeLayout ctrlButtomLayout;
    @BindView(R.id.ctrl_betting_number_one)
    TextView ctrlBettingNumberOne;
    @BindView(R.id.ctrl_betting_number_two)
    TextView ctrlBettingNumberTwo;
    @BindView(R.id.ctrl_betting_number_layout)
    LinearLayout ctrlBettingNumberLayout;
    @BindView(R.id.ctrl_betting_winning)
    Button ctrlBettingWinning;
    @BindView(R.id.ctrl_betting_fail)
    Button ctrlBettingFail;
    @BindView(R.id.ctrl_dollgold_tv1)
    TextView ctrlDollgoldTv1;
    @BindView(R.id.ctrl_confirm_layout)
    LinearLayout ctrlConfirmLayout;
    @BindView(R.id.ctrl_beting_layout)
    RelativeLayout ctrlBetingLayout;
    private SurfaceHolder mRealPlaySh;
    private CtrlCompl ctrlCompl;
    private EZPlayer mEZPlayer = null;
    private Handler mHandler = null;
    private FillingCurrencyDialog fillingCurrencyDialog;

    private int EZstatus;
    private List<String> userInfos = new ArrayList<>();  //房屋内用户电话信息

    //2017/11/18 11：10 加入振动器
    public Vibrator vibrator; // 震动器
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private String camera_name;
    private String dollName = "未知";
    private boolean isCurrentConnect = true;
    private String upTime;
    private String upFileName;
    private int money = 0;
    private String state = "";
    private QuizInstrictionDialog quizInstrictionDialog;
    private String id;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ctrl;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Utils.showLogE(TAG, "afterCreate");
        initView();
        initData();
        coinTv.setText(UserUtils.UserBalance);
        setVibrator();   //初始化振动器
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        RxBus.get().register(this);
        Utils.showLogE(TAG, "=====" + UserUtils.UserPhone);
        NettyUtils.sendRoomInCmd();
        ctrlGifView.setVisibility(View.VISIBLE);
        ctrlGifView.setEnabled(false);
        ctrlGifView.setMovieResource(R.raw.ctrl_video_loading);
        ctrlFailIv.setVisibility(View.GONE);
        mRealPlaySh = mRealPlaySv.getHolder();
        mRealPlaySh.addCallback(this);
        if (Utils.connectStatus.equals(ConnectResultEvent.CONNECT_FAILURE)) {
            ctrlStatusIv.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        } else {
            ctrlStatusIv.setImageResource(R.drawable.point_green);
        }
        NettyUtils.pingRequest(); //判断连接
    }

    private void initData() {
        mHandler = new Handler(this);
        ctrlCompl = new CtrlCompl(this, this);
        camera_name = getIntent().getStringExtra(Utils.TAG_CAMERA_NAME);
        ctrlCompl.startLoginPresent(camera_name);
        dollName = getIntent().getStringExtra(Utils.TAG_ROOM_NAME);
        money = Integer.parseInt(getIntent().getStringExtra(Utils.TAG_DOLL_GOLD));
        id=getIntent().getStringExtra(Utils.TAG_DOLL_Id);
        Log.e(TAG,"哈哈哈哈哈哈"+id);
        if (!Utils.isEmpty(dollName)) {
            dollNameTv.setText(dollName);
        }
        ctrlDollgoldTv.setText(money + "/次");
        ctrlDollgoldTv1.setText(money+"/次");//下注金额
        playerNameTv.setText(UserUtils.UserName);
        setStartMode(getIntent().getBooleanExtra(Utils.TAG_ROOM_STATUS, true));
        settings = getSharedPreferences("app_user", 0);// 获取SharedPreference对象
        editor = settings.edit();// 获取编辑对象
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Utils.showLogE(TAG, "surfaceCreated");
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(surfaceHolder);
        }
        mRealPlaySh = surfaceHolder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Utils.showLogE(TAG, "surfaceDestroyed");
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(null);
        }
        mRealPlaySh = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.showLogE(TAG, "onDestroy");
        ctrlCompl.stopRecordView(mEZPlayer);
        if (mEZPlayer != null) {
            mEZPlayer.release();
        }
        ctrlCompl.sendCmdCtrl(MoveType.CATCH);
        ctrlCompl.stopTimeCounter();
        ctrlCompl.sendCmdOutRoom();
        NettyUtils.sendGetDeviceStatesCmd();
        ctrlCompl = null;
        RxBus.get().unregister(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Utils.showLogE(TAG, "=====" + msg.what);
        EZstatus = msg.what;
        ctrlGifView.setVisibility(View.GONE);
        switch (msg.what) {
            //播放失败错误
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                ctrlFailIv.setVisibility(View.VISIBLE);
                break;
            //播放成功
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:

                break;
            //停止播放成功
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_STOP_SUCCESS:
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void getEZCameraInfo(EZCameraInfo info) {
        if (mEZPlayer == null) {
            mEZPlayer = MyApplication.getOpenSDK().createPlayer(
                    info.getDeviceSerial(), info.getCameraNo());
        }
        if (mEZPlayer == null) {
            return;
        }
        mEZPlayer.setHandler(mHandler);
        mEZPlayer.setSurfaceHold(mRealPlaySh);
        Utils.showLogE(TAG, "======" + "mEZPlayer.startRealPlay();");
        mEZPlayer.startRealPlay();
    }

    @Override
    public void getEZError(int errorcode) {
        Utils.showLogE(TAG, "getEZError:" + errorcode);
        ctrlGifView.setVisibility(View.GONE);
        ctrlFailIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void getTime(int time) {
        timeCircleProgressView.setProgress(Utils.CATCH_TIME_OUT - time);
    }

    @Override
    public void getTimeFinish() {
        ctrlCompl.sendCmdCtrl(MoveType.CATCH);
        ctrlCompl.stopTimeCounter();
        timeCircleProgressView.setProgress(Utils.CATCH_TIME_OUT);
    }

    @Override
    public void getUserInfos(List<String> list) {
        //当前房屋的人数
        userInfos = list;
        int counter = userInfos.size();
        if (counter > 0) {
            playerCounterIv.setText(String.format(getString(R.string.player_counter_text), counter));
            Glide.with(this).load(UserUtils.UserImage).asBitmap().transform(new GlideCircleTransform(this)).into(playerMainIv);
            if (counter == 1) {
                playerSecondIv.setVisibility(View.INVISIBLE);
            } else {
                //显示另外一个人
                for(int i=0; i<counter; i++) {
                    if (!userInfos.get(i).equals(UserUtils.UserName)) {
                        getCtrlUserImage(userInfos.get(i));
                        break;
                    }
                }
                //显示第二个人
                playerSecondIv.setVisibility(View.VISIBLE);
            }
        }
        if (counter>1){
            ctrlQuizLayout.setEnabled(true);
        }else {
            ctrlQuizLayout.setEnabled(false);
        }
    }

    public void getCtrlUserImage(String phone) {
        String str = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
        HttpManager.getInstance().getCtrlUserImage(str, new RequestSubscriber<Result<AppUserBean>>() {
            @Override
            public void _onSuccess(Result<AppUserBean> appUserBeanResult) {
                UserUtils.UserImage1 = UrlUtils.USERFACEIMAGEURL + appUserBeanResult.getData().getAppUser().getIMAGE_URL();
                Glide.with(getApplicationContext()).load(UserUtils.UserImage1)
                        .asBitmap().transform(new GlideCircleTransform(CtrlActivity.this)).into(playerSecondIv);
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    @Override
    public void getRecordErrCode(int code) {
        Utils.showLogE(TAG, "录制视频失败::::::" + code);
    }

    @Override
    public void getRecordSuecss() {
        Utils.showLogE(TAG, "录制视频完毕......");
    }

    @Override
    public void getRecordAttributetoNet(String time, String fileName) {
        Utils.showLogE(TAG, "视频上传的时间::::" + time + "=====" + fileName);
        upTime = time;
        upFileName = fileName;
    }

    private void updataTime(String time, String state) {

        HttpManager.getInstance().getRegPlayBack(UserUtils.id, time, UserUtils.UserName, state, dollName, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {

            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mEZPlayer != null) {
            mEZPlayer.stopRealPlay();
        }
        //vibrator = null;   //销毁振动器
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.showLogE(TAG, "onRestart");
        ctrlGifView.setVisibility(View.VISIBLE);
        NettyUtils.pingRequest();
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(mRealPlaySh);
            mEZPlayer.startRealPlay();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.image_back, R.id.recharge_button,
            R.id.startgame_ll, R.id.ctrl_fail_iv,R.id.ctrl_quiz_layout, R.id.ctrl_instruction_image, R.id.ctrl_betting_winning, R.id.ctrl_betting_fail,
            R.id.ctrl_confirm_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.recharge_button:
                getMoney();
                break;
            case R.id.startgame_ll:
                //开始游戏按钮
                if (Integer.parseInt(UserUtils.UserBalance) >= money) {
                    if ((EZstatus == EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS)
                            && (Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS))
                            && (isCurrentConnect)) {
                        ctrlCompl.sendCmdCtrl(MoveType.START);
                        coinTv.setText((Integer.parseInt(UserUtils.UserBalance) - money) + "");
//                        getPlayNum(UserUtils.UserPhone, String.valueOf(money), UserUtils.UserName, dollName);   //扣款
                    }
                    setVibratorTime(300, -1);
                } else {
                    MyToast.getToast(getApplicationContext(), "余额不足，请充值！").show();
                }
                Log.e(TAG, "<<px=" + (mRealPlaySv.getHeight() * 16) / 9 + "<<dp=" + Utils.px2dip(getApplicationContext(), (mRealPlaySv.getHeight() * 16) / 9));
                break;
            case R.id.ctrl_fail_iv:
                ctrlFailIv.setVisibility(View.GONE);
                ctrlGifView.setVisibility(View.VISIBLE);
                ctrlCompl.startLoginPresent(camera_name);
                break;
            case R.id.ctrl_quiz_layout:
                ctrlButtomLayout.setVisibility(View.GONE);
                ctrlBetingLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.ctrl_instruction_image:
                //说明
                quizInstrictionDialog=new QuizInstrictionDialog(this,R.style.easy_dialog_style);
                quizInstrictionDialog.show();
                break;

            case R.id.ctrl_betting_winning:

                ctrlBettingWinning.setBackgroundResource(R.drawable.ctrl_betting_item);
                //中
                break;
            case R.id.ctrl_betting_fail:

                ctrlBettingWinning.setBackgroundResource(R.drawable.ctrl_betting_item);
                //不中
                break;
            case R.id.ctrl_confirm_layout:
                //确认按钮
//                getBets(UserUtils.UserName,Integer.valueOf(money).intValue(),"1",UserUtils.id,id);
                break;
            default:
                break;
        }
    }


    private void getWorkstation() {
        startgameLl.setVisibility(View.GONE);
        rechargeLl.setVisibility(View.GONE);
        catchLl.setVisibility(View.VISIBLE);
        operationRl.setVisibility(View.VISIBLE);
        ctrlCompl.startTimeCounter();
    }

    private void getStartstation() {
        startgameLl.setVisibility(View.VISIBLE);
        rechargeLl.setVisibility(View.VISIBLE);
        catchLl.setVisibility(View.GONE);
        operationRl.setVisibility(View.GONE);
    }

    private void getMoney() {
        fillingCurrencyDialog = new FillingCurrencyDialog(this, R.style.easy_dialog_style);
        fillingCurrencyDialog.show();
        fillingCurrencyDialog.setDialogClickListener(myDialogClick);
    }

    private FillingCurrencyDialog.MyDialogClick myDialogClick =
            new FillingCurrencyDialog.MyDialogClick() {
                @Override
                public void getMoney10(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }

                @Override
                public void getMoney20(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }

                @Override
                public void getMoney50(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }

                @Override
                public void getMoney100(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }
            };

    //摇杆操作
    @OnTouch({R.id.front_image, R.id.back_image, R.id.left_image, R.id.right_image, R.id.catch_ll})
    public boolean onTouchEvent(View view, MotionEvent motionEvent) {
        if ((EZstatus != EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS)
                || (!Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS))) {
            return false;
        }
        if (!isCurrentConnect) {
            return false;
        }
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                switch (view.getId()) {
                    case R.id.front_image:
                        setVibratorTime(3000, 1);
                        ctrlCompl.sendCmdCtrl(MoveType.FRONT);
                        topImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_top_s));
                        break;
                    case R.id.back_image:
                        setVibratorTime(3000, 1);
                        ctrlCompl.sendCmdCtrl(MoveType.BACK);
                        belowImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_below_s));
                        break;
                    case R.id.left_image:
                        setVibratorTime(3000, 1);
                        ctrlCompl.sendCmdCtrl(MoveType.LEFT);
                        leftImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_left_s));
                        break;
                    case R.id.right_image:
                        setVibratorTime(3000, 1);
                        ctrlCompl.sendCmdCtrl(MoveType.RIGHT);
                        rightImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_right_s));
                        break;
                    case R.id.catch_ll:
                        setVibratorTime(300, -1);
                        ctrlCompl.sendCmdCtrl(MoveType.CATCH);
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (view.getId()) {
                    case R.id.front_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        topImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_top_n));
                        break;
                    case R.id.back_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        belowImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_below_n));
                        break;
                    case R.id.left_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        leftImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_left_n));
                        break;
                    case R.id.right_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        rightImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_right_n));
                        break;
                    case R.id.catch_ll:
                        ctrlCompl.stopTimeCounter();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    //控制区与状态区
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_ROOM_IN),
            @Tag(Utils.TAG_ROOM_OUT),
            @Tag(Utils.TAG_MOVE_FAILE),
            @Tag(Utils.TAG_MOVE_RESPONSE)})
    public void getKnxConnectStates(Object response) {
        if (response instanceof MoveControlResponse) {
            MoveControlResponse moveControlResponse = (MoveControlResponse) response;
            Utils.showLogE(TAG, moveControlResponse.toString());
            if ((moveControlResponse.getSeq() == -2)) {
                if (moveControlResponse.getMoveType() == null) {
                    return;
                }
                if (moveControlResponse.getMoveType().name().equals(MoveType.START.name())) {
                    setStartMode(false);
                }
            } else {
                if (moveControlResponse.getReturnCode() != ReturnCode.SUCCESS) {
                    return;
                }
                //本人点击start
                if (moveControlResponse.getMoveType().name()
                        .equals(MoveType.START.name())) {
                    getWorkstation();
                    ctrlCompl.startRecordVideo(mEZPlayer);
                }
            }
        } else if (response instanceof String) {
            Utils.showLogE(TAG, "move faile....");
        } else if (response instanceof AppOutRoomResponse) {
            AppOutRoomResponse appOutRoomResponse = (AppOutRoomResponse) response;
            Utils.showLogE(TAG, appOutRoomResponse.toString());
            long seq = appOutRoomResponse.getSeq();
            if (seq == -2) {
                userInfos.remove(appOutRoomResponse.getNickName());
                getUserInfos(userInfos);
            }
        } else if (response instanceof AppInRoomResponse) {
            AppInRoomResponse appInRoomResponse = (AppInRoomResponse) response;
            Utils.showLogE(TAG, "=====" + appInRoomResponse.toString());
            String allUsers = appInRoomResponse.getAllUserInRoom(); //返回的昵称
            Boolean free = appInRoomResponse.getFree();
            setStartMode(free);
            long seq = appInRoomResponse.getSeq();
            if ((seq != -2) && (!Utils.isEmpty(allUsers))) {
                //TODO  我本人进来了
                ctrlCompl.sendGetUserInfos(allUsers);
            } else {
                userInfos.add(appInRoomResponse.getNickName());
                getUserInfos(userInfos);
            }
        }
    }

    //监控网关区
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_CONNECT_ERR),
            @Tag(Utils.TAG_CONNECT_SUCESS)})
    public void getConnectStates(String state) {
        if (state.equals(Utils.TAG_CONNECT_ERR)) {
            Utils.showLogE(TAG, "TAG_CONNECT_ERR");
            ctrlStatusIv.setImageResource(R.drawable.point_red);
        } else if (state.equals(Utils.TAG_CONNECT_SUCESS)) {
            Utils.showLogE(TAG, "TAG_CONNECT_SUCESS");
            ctrlStatusIv.setImageResource(R.drawable.point_green);
            NettyUtils.sendRoomInCmd();
        }
    }

    //监控单个网关连接区
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_DISCONNECT)})
    public void getSingleGatwayDisConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayDisConnect id" + id);
        if (id.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            ctrlStatusIv.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_CONNECT)})
    public void getSingleGatwayConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayConnect id" + id);
        if (id.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            ctrlStatusIv.setImageResource(R.drawable.point_green);
            isCurrentConnect = true;
        }
    }

    //设备故障
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(Utils.TAG_DEVICE_ERR)})
    public void getDeviceErr(Object object) {
        if (object instanceof GatewayPoohStatusMessage) {
            //TODO 主板没有返回数据
            GatewayPoohStatusMessage message = (GatewayPoohStatusMessage) object;
            Utils.showLogE(TAG, "主板没有返回数据" + message.toString());
        } else if (object instanceof PoohAbnormalStatus) {
            //TODO 主板报错
            PoohAbnormalStatus status = (PoohAbnormalStatus) object;
            Utils.showLogE(TAG, "主板报错 错误代码:::" + status.getValue());
        }
    }

    //设备正常返回
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_DEVICE_FREE)})
    public void getDeviceFree(GatewayPoohStatusMessage message) {
        String roomId = message.getRoomId();
        int number = message.getGifinumber();
        Utils.showLogE(TAG, "getDeviceFree::::::" + roomId + "======" + number);
        if (roomId.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            getStartstation();
            setStartMode(true);
            if (Utils.isEmpty(upTime)) {
                return;
            }
            ctrlCompl.stopRecordView(mEZPlayer); //录制完毕
            getPlayNum(UserUtils.UserPhone, String.valueOf(money), UserUtils.UserName, dollName);   //扣款
            if (number != 0) {
                //抓到娃娃  上传给后台
                upFileName = "";
                state = "1";
            } else {
                //删除本地视频
                state = "0";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean d = Utils.delFile(upFileName);
                        Utils.showLogE(TAG, "没抓住 删除" + upFileName + "视频....." + d);
                        upFileName = "";

                    }
                }, 2000);  //4s后删除 保证录制完毕
            }


        }
    }

    //初始化振动器   2017/11/18 11:11
    private void setVibrator() {
        vibrator = VibratorView.getVibrator(getApplicationContext());
    }

    //设置震动时间
    private void setVibratorTime(long time, int replay) {
        if (null != vibrator)
            vibrator.vibrate(new long[]{0, time, 0, 0}, replay);
    }

    private void setStartMode(boolean isFree) {
        startgameLl.setEnabled(isFree);
        if (isFree) {
            startgameLl.setBackgroundResource(R.drawable.ctrl_startgame_bg_n);
            return;
        }
        startgameLl.setBackgroundResource(R.drawable.ctrl_startgame_bg_d);
    }

    private void getPlayNum(String phone, String number, String userName, String dollName) {
        String phones = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
        HttpManager.getInstance().getUserPlayNum(phones, number, userName, dollName, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                Log.e(TAG, "消费结果=" + result.getMsg());
                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                UserUtils.id=result.getData().getPlayBack().getID();
                updataTime(upTime, state);
                upTime = "";
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(), "扣费失败！").show();
            }
        });
    }

    //下注接口
    private void getBets(String userID,Integer wager,String guessKey,Integer playBackId,
                         String dollID){
        HttpManager.getInstance().getBets(userID, wager, guessKey, playBackId, dollID, new RequestSubscriber<Result<AppUserBean>>() {
            @Override
            public void _onSuccess(Result<AppUserBean> appUserBeanResult) {

            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
