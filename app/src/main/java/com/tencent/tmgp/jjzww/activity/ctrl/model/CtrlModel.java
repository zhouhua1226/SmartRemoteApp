package com.tencent.tmgp.jjzww.activity.ctrl.model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;

import com.tencent.tmgp.jjzww.base.MyApplication;
import com.tencent.tmgp.jjzww.utils.EZUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.gatz.netty.utils.NettyUtils;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.SDCardUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouh on 2017/9/7.
 */
public class CtrlModel {
    private static final String TAG = "CtrlModel-";

    private Context ctx;
    private DeviceCallBack callBack;
    private final long countTime = 20*1000;
    private final long seconds = 1000;
    private boolean isRecoding = false;

    //定时器区
    private CountDownTimer countDownTimer = new CountDownTimer(countTime, seconds) {
        @Override
        public void onTick(long l) {
            callBack.getClickTime((int)(l/seconds));
        }

        @Override
        public void onFinish() {
            callBack.getClickFinish();
        }
    };

    public CtrlModel(Context context, DeviceCallBack deviceCallBack){
        this.ctx = context;
        this.callBack = deviceCallBack;
    }

    public void getEzDevice(String name) {
        new GetCamersInfoListTask(name).execute();
    }

    /**
     * 获取事件消息任务
     */
    private class GetCamersInfoListTask extends AsyncTask<Void, Void, List<EZDeviceInfo>> {
        private int mErrorCode = 0;
        private String name;
        private EZCameraInfo mCameraInfo;
        private EZConstants.EZVideoLevel mCurrentQulityMode =
                EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET;

        public GetCamersInfoListTask(String n) {
            this.name = n;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<EZDeviceInfo> doInBackground(Void... params) {
            if (!ConnectionDetector.isNetworkAvailable(ctx)) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                return null;
            }
            try {
                List<EZDeviceInfo> result = null;
                result = MyApplication.getOpenSDK().getDeviceList(0, 10);
                return result;
            } catch (BaseException e) {
                ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                mErrorCode = errorInfo.errorCode;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<EZDeviceInfo> result) {
            super.onPostExecute(result);
            EZDeviceInfo mDeviceInfo = null;
            if (result != null) {
                for(int i = 0; i < result.size(); i ++) {
                    EZDeviceInfo deviceInfo = result.get(i);
                    if (deviceInfo.getDeviceName().equals(name)){
                        mDeviceInfo = deviceInfo;
                        break;
                    }
                }
                if(mDeviceInfo == null) {
                    Utils.showLogE(TAG, "没有找到相关摄像头");
                    callBack.getErrCode(EZUtils.EZ_OFFLINE);
                    return;
                }
                //TODO 不加密
                mDeviceInfo.setIsEncrypt(0);
                mCameraInfo = EZUtils.getCameraInfoFromDevice(mDeviceInfo, 0);
                if (mCameraInfo == null) {
                    callBack.getErrCode(EZUtils.EZ_OFFLINE);
                    return;
                }
                if (mDeviceInfo.getStatus() == EZUtils.EZ_OFFLINE) {
                    Utils.showLogE(TAG, "设备不在线");
                    callBack.getErrCode(EZUtils.EZ_OFFLINE);
                    return;
                }
                mCurrentQulityMode = (mCameraInfo.getVideoLevel());
                Utils.showLogE(TAG, "当前账号下的设备:::" + result.size() + "视频的清晰度处理::::" + mCurrentQulityMode.name());
                callBack.getEZCameraInfo(mCameraInfo);
            }
            if (mErrorCode != 0) {
                onError(mErrorCode);
            }
        }

        protected void onError(int errorCode) {
            switch (errorCode) {
                case ErrorCode.ERROR_WEB_SESSION_ERROR:
                case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
                case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_ERROR:
                    callBack.getErrCode(errorCode);
                    break;
                default:
                    //未知错误
                    callBack.getErrCode(errorCode);
                    break;
            }
        }
    }

    public void sendCmd(MoveType moveType) {
        NettyUtils.sendCtrlCmd(moveType);
    }

    public void sendCmdOutRoom() {
        NettyUtils.sendRoomOutCmd();
    }

    public void sendTimeStart() {
        countDownTimer.start();
    }

    public void sendTimeCancle() {
        countDownTimer.cancel();
    }

    /**
     * 保存视频到本地
     * @param ezPlayer
     */
    public void sendStartSecordVideo(EZPlayer ezPlayer) {
        if (!SDCardUtil.isSDCardUseable()) {
            callBack.getVideoRecordErr(UserUtils.RECODE_ERR_CODE_SDCARD_DISABLE);
            return;
        }

        if (SDCardUtil.getSDCardRemainSize() < SDCardUtil.PIC_MIN_MEM_SPACE) {
            callBack.getVideoRecordErr(UserUtils.RECODE_ERR_CODE_SDCARD_FAIL_FOR_MEMORY);
            return;
        }
        if (ezPlayer == null) {
            callBack.getVideoRecordErr(UserUtils.RECODE_ERR_CODE_EZPLAY_NULL);
            return;
        }

        Date date = new Date();
        String time = String.format("%tY", date) + String.format("%tm", date) + String.format("%td", date) +
                String.format("%tH", date) + String.format("%tM", date) +
                String.format("%tS", date);
        String strRecordFile = UserUtils.RECODE_URL + time + ".mp4";
        if (ezPlayer.startLocalRecordWithFile(strRecordFile)) {
            Utils.showLogE(TAG, "保存的视频:::" + strRecordFile);
            isRecoding = true;
            callBack.getVideoAttributetoNet(time, strRecordFile);
        } else {
            if (isRecoding) {
                stopRecordVideo(ezPlayer);
            }
        }

    }


    /**
     * 关闭视频录制
     * @param ezPlayer
     */
    public void stopRecordVideo(EZPlayer ezPlayer) {
        if (ezPlayer == null) {
            callBack.getVideoRecordErr(UserUtils.RECODE_ERR_CODE_EZPLAY_NULL);
            return;
        }
        if (!isRecoding) {
            return;
        }
        ezPlayer.stopLocalRecord();
        callBack.getVideoSucess();
        isRecoding = false;
    }
}
