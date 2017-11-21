package com.game.smartremoteapp.activity.ctrl.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.smartremoteapp.R;
import com.game.smartremoteapp.activity.ctrl.presenter.CtrlCompl;
import com.game.smartremoteapp.activity.wechat.WeChatPayActivity;
import com.game.smartremoteapp.base.BaseActivity;
import com.game.smartremoteapp.base.MyApplication;
import com.game.smartremoteapp.utils.UserUtils;
import com.game.smartremoteapp.utils.Utils;
import com.game.smartremoteapp.view.FillingCurrencyDialog;
import com.game.smartremoteapp.view.GifView;
import com.game.smartremoteapp.view.MyToast;
import com.game.smartremoteapp.view.TimeCircleProgressView;
import com.game.smartremoteapp.view.VibratorView;
import com.gatz.netty.global.ConnectResultEvent;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.iot.game.pooh.server.entity.json.MoveControlResponse;
import com.iot.game.pooh.server.entity.json.app.AppInRoomResponse;
import com.iot.game.pooh.server.entity.json.app.AppOutRoomResponse;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.makeramen.roundedimageview.RoundedImageView;
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
 * Created by zhouh on 2017/9/8.
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
    @BindView(R.id.image_service)
    ImageButton imageService;
    @BindView(R.id.money_image)
    ImageView moneyImage;
    @BindView(R.id.game_tv)
    TextView gameTv;
    @BindView(R.id.coin_tv)
    TextView coinTv;
    @BindView(R.id.recharge_button)
    ImageButton rechargeButton;
    @BindView(R.id.message_button)
    ImageButton messageButton;
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
    RoundedImageView playerSecondIv;
    @BindView(R.id.main_player_iv)
    RoundedImageView playerMainIv;
    @BindView(R.id.player_name_tv)
    TextView playerNameTv;
    @BindView(R.id.ctrl_status_iv)
    ImageView ctrlStatusIv;
    @BindView(R.id.ctrl_time_progress_view)
    TimeCircleProgressView timeCircleProgressView;

    private static final String TAG = "CtrlActivity---";
    private SurfaceHolder mRealPlaySh;
    private CtrlCompl ctrlCompl;
    private EZPlayer mEZPlayer = null;
    private Handler mHandler = null;
    private FillingCurrencyDialog fillingCurrencyDialog;

    private int EZstatus;
    private List<String> userInfos = new ArrayList<>();  //房屋内用户信息

    //2017/11/18 11：10 加入振动器
    public Vibrator vibrator; // 震动器
    public String camera_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ctrl;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Utils.showLogE(TAG, "afterCreate");
        initView();
        initData();
        coinTv.setText("1000");
        setVibrator();   //初始化振动器
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        RxBus.get().register(this);
        Utils.showLogE(TAG, "=====" + UserUtils.UserNickName);
        NettyUtils.sendRoomInCmd(UserUtils.UserNickName);
        ctrlGifView.setVisibility(View.VISIBLE);
        ctrlGifView.setEnabled(false);
        ctrlGifView.setMovieResource(R.raw.ctrl_video_loading);
        ctrlFailIv.setVisibility(View.GONE);
        mRealPlaySh = mRealPlaySv.getHolder();
        mRealPlaySh.addCallback(this);
        NettyUtils.pingRequest(); //判断连接
    }

    private void initData() {
        mHandler = new Handler(this);
        ctrlCompl = new CtrlCompl(this, this);
        camera_name = getIntent().getStringExtra(Utils.TAG_CAMERA_NAME);
        ctrlCompl.startLoginPresent(camera_name);
        String name = getIntent().getStringExtra(Utils.TAG_ROOM_NAME);
        if (!Utils.isEmpty(name)) {
            dollNameTv.setText(name);
        }
        playerNameTv.setText(UserUtils.UserNickName);
        setStartMode(getIntent().getBooleanExtra(Utils.TAG_ROOM_STATUS, true));
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
        Utils.showLogE(TAG, "======" +  "mEZPlayer.startRealPlay();");
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
        getStartstation();
        ctrlCompl.stopTimeCounter();
        timeCircleProgressView.setProgress(Utils.CATCH_TIME_OUT);
    }

    @Override
    public void getUserInfos(List<String> list) {
        //当前房屋的人数
        userInfos = list;
        int counter = userInfos.size();
        if(counter > 0) {
            playerCounterIv.setText(String.format(getString(R.string.player_counter_text), counter));
            if (counter == 1) {
                playerSecondIv.setVisibility(View.INVISIBLE);
            } else {
                playerSecondIv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mEZPlayer != null) {
            mEZPlayer.stopRealPlay();
        }
        vibrator=null;   //销毁振动器
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

    @OnClick({R.id.image_back, R.id.image_service, R.id.recharge_button, R.id.message_button,
            R.id.startgame_ll,R.id.ctrl_fail_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_service:
                MyToast.getToast(this, "我是客服").show();
                break;
            case R.id.recharge_button:
                getMoney();
                break;
            case R.id.message_button:
                MyToast.getToast(this, "我要发送弹幕了").show();
                break;
            case R.id.startgame_ll:
                //开始游戏按钮
                if ((EZstatus == EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS)
                        && (Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS))) {
                    ctrlCompl.sendCmdCtrl(MoveType.START);
                    getWorkstation();

                }
                setVibratorTime(300,-1);
                Log.e(TAG,"<<px="+(mRealPlaySv.getHeight()*16)/9+"<<dp="+Utils.px2dip(getApplicationContext(),(mRealPlaySv.getHeight()*16)/9));
                break;
            case R.id.ctrl_fail_iv:
                ctrlFailIv.setVisibility(View.GONE);
                ctrlGifView.setVisibility(View.VISIBLE);
                ctrlCompl.startLoginPresent(camera_name);
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startgameLl.setVisibility(View.VISIBLE);
                rechargeLl.setVisibility(View.VISIBLE);
                catchLl.setVisibility(View.GONE);
                operationRl.setVisibility(View.GONE);
            }
        }, Utils.CATCH_TIME_DELAY); //8s下爪  抵消掉下爪需要的时间
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
                || (!Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS))){
            return false;
        }
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                switch (view.getId()) {
                    case R.id.front_image:
                        setVibratorTime(3000,1);
                        ctrlCompl.sendCmdCtrl(MoveType.FRONT);
                        topImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_top_s));
                        break;
                    case R.id.back_image:
                        setVibratorTime(3000,1);
                        ctrlCompl.sendCmdCtrl(MoveType.BACK);
                        belowImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_below_s));
                        break;
                    case R.id.left_image:
                        setVibratorTime(3000,1);
                        ctrlCompl.sendCmdCtrl(MoveType.LEFT);
                        leftImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_left_s));
                        break;
                    case R.id.right_image:
                        setVibratorTime(3000,1);
                        ctrlCompl.sendCmdCtrl(MoveType.RIGHT);
                        rightImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_action_down_right_s));
                        break;
                    case R.id.catch_ll:
                        setVibratorTime(300,-1);
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
                        getStartstation();
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
            if ((moveControlResponse.getSeq() == -2) && (moveControlResponse.getMoveType()!=null)) {
                if (moveControlResponse.getMoveType().name().equals(MoveType.START.name())) {
                    setStartMode(false);
                } else if (moveControlResponse.getMoveType().name().equals(MoveType.CATCH.name())) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setStartMode(true);
                        }
                    }, Utils.CATCH_TIME_DELAY);
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
            String allUsers = appInRoomResponse.getAllUserInRoom();
            long seq = appInRoomResponse.getSeq();
            if ((seq != -2) && (!Utils.isEmpty(allUsers))) {
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
            NettyUtils.sendRoomInCmd(UserUtils.UserNickName);
        }
    }

    //初始化振动器   2017/11/18 11:11
    private void setVibrator(){
        vibrator = VibratorView.getVibrator(getApplicationContext());
    }
    //设置震动时间
    private void setVibratorTime(long time,int replay){
        if (null != vibrator)
            vibrator.vibrate(new long[]{0,time,0,0},replay);
    }

    private void setStartMode(boolean isFree) {
        startgameLl.setEnabled(isFree);
        if (isFree) {
            startgameLl.setBackgroundResource(R.drawable.ctrl_startgame_bg_n);
            return;
        }
        startgameLl.setBackgroundResource(R.drawable.ctrl_startgame_bg_d);
    }
}
