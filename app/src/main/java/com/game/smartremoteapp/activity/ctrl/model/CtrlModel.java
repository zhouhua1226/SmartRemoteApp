package com.game.smartremoteapp.activity.ctrl.model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;

import com.game.smartremoteapp.base.MyApplication;
import com.game.smartremoteapp.utils.EZUtils;
import com.game.smartremoteapp.utils.UserUtils;
import com.game.smartremoteapp.utils.Utils;
import com.gatz.netty.utils.NettyUtils;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.ConnectionDetector;

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
                    return;
                }
                //TODO 不加密
                mDeviceInfo.setIsEncrypt(0);
                mCameraInfo = EZUtils.getCameraInfoFromDevice(mDeviceInfo, 0);
                if (mCameraInfo == null) {
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
        NettyUtils.sendRoomOutCmd(UserUtils.UserNickName);
    }

    public void sendTimeStart() {
        countDownTimer.start();
    }

    public void sendTimeCancle() {
        countDownTimer.cancel();
    }


}
