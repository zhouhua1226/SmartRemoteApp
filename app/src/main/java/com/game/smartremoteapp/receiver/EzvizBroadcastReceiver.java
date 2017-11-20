package com.game.smartremoteapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.game.smartremoteapp.utils.Utils;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.EZPushAlarmMessage;
import com.videogo.openapi.bean.EZPushBaseMessage;
import com.videogo.openapi.bean.EZPushTransferMessage;


/**
 * 监听广播
 * 
 * @author zhouhua
 * @data  2016-11-2
 */
public class EzvizBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EzvizBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String message = intent.getStringExtra("message_extra");
        Utils.showLogE(TAG, action + "------" + message);
        if (action.equals("com.ezviz.push.sdk.android.intent.action.MESSAGE")) {
            EZPushBaseMessage baseMessage =  EZOpenSDK.getInstance().parsePushMessage(message);
            if (baseMessage != null) {
                switch(baseMessage.getMessageType()) {
                    case 1:
                        EZPushAlarmMessage alarmMessage= (EZPushAlarmMessage) baseMessage;
                        Utils.showLogE(TAG, "onReceive: Push get alarm message:" + alarmMessage);
                        break;
                    case 99:
                        EZPushTransferMessage transferMessage = (EZPushTransferMessage) baseMessage;
                        Utils.showLogE(TAG, "onReceive: Push get transfer message:" + transferMessage);
                        break;
                    default:
                        Utils.showLogE(TAG, "onReceive: Push get other message:");
                }
            }
        }
        if (action.equals("android.net.conn .CONNECTIVITY_CHANGE")) {
            EzvizAPI.getInstance().refreshNetwork();
        } else if(action.equals(Constant.ADD_DEVICE_SUCCESS_ACTION)) {
            String deviceId = intent.getStringExtra(IntentConsts.EXTRA_DEVICE_ID);
        } else if(action.equals(Constant.OAUTH_SUCCESS_ACTION)) {

        } else if ("com.videogo.androidpn.NOTIFICATION_RECEIVED_ACTION".equals(action)) {

        }
    }
}
