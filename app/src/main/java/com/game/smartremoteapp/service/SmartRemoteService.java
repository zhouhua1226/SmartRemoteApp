package com.game.smartremoteapp.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;


import com.game.smartremoteapp.utils.Utils;
import com.gatz.netty.global.ConnectResultEvent;
import com.gatz.netty.observer.HandlerObserver;
import com.gatz.netty.observer.RequestSubscriber;
import com.gatz.netty.observer.SuberInfo;
import com.hwangjr.rxbus.RxBus;
import com.iot.game.pooh.server.entity.json.GetStatusResponse;
import com.iot.game.pooh.server.entity.json.MoveControlResponse;
import com.iot.game.pooh.server.entity.json.app.AppInRoomResponse;
import com.iot.game.pooh.server.entity.json.app.AppOutRoomResponse;


import rx.Observable;
import rx.Subscriber;

public class SmartRemoteService extends Service {
    private static final String TAG = "SmartRemoteService";

    public SmartRemoteService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!HandlerObserver.getInstance().getRequestSubscriberSet()) {
            Utils.showLogE(TAG, "setObservable and setRequestSubscriber.......");
            HandlerObserver.getInstance().setObservable(o);
            HandlerObserver.getInstance().setRequestSubscriber(rs);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.get().register(this);
        ContentResolver r = this.getContentResolver();
        int val = Settings.System.getInt(r, Settings.System.WIFI_SLEEP_POLICY,
                Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
        if (Settings.System.WIFI_SLEEP_POLICY_NEVER != val)
            Settings.System.putInt(r, Settings.System.WIFI_SLEEP_POLICY,
                    Settings.System.WIFI_SLEEP_POLICY_NEVER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Observable<SuberInfo> o = Observable.create(new Observable.OnSubscribe<SuberInfo>() {
        @Override
        public void call(Subscriber<? super SuberInfo> subscriber) {
            SuberInfo info = HandlerObserver.getInstance().getInfo();
            subscriber.onNext(info);
            subscriber.onCompleted();
        }
    });


    private RequestSubscriber<SuberInfo> rs = new RequestSubscriber<SuberInfo>() {
        @Override
        public void _onSuccess(SuberInfo suberInfo) {
            String tag =  suberInfo.getTag();
            Object[] objs = suberInfo.getObject();
            Utils.showLogE(TAG, "Tag::::::" + tag);
            if ((tag.equals(ConnectResultEvent.SESSION_INVALID) ||
                    (tag.equals(ConnectResultEvent.CONNECT_SESSION_INVALID)))) {
                Utils.connectStatus = ConnectResultEvent.CONNECT_FAILURE;
                RxBus.get().post(Utils.TAG_SESSION_INVALID, Utils.TAG_SESSION_INVALID);
            } else if (tag.equals(ConnectResultEvent.PING_SUCCESS)) {
                if (!Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS)){
                    Utils.connectStatus = ConnectResultEvent.CONNECT_SUCCESS;
                    RxBus.get().post(Utils.TAG_CONNECT_SUCESS, Utils.TAG_CONNECT_SUCESS);
                }
            } else if (tag.equals(ConnectResultEvent.GATEWAY_UNEXIST) ||
                    (tag.equals(ConnectResultEvent.CONNECT_FAILURE))) {
                if (!Utils.connectStatus.equals(ConnectResultEvent.CONNECT_FAILURE)) {
                    RxBus.get().post(Utils.TAG_CONNECT_ERR, Utils.TAG_CONNECT_ERR);
                }
                Utils.connectStatus = ConnectResultEvent.CONNECT_FAILURE;
            } else if (tag.equals(ConnectResultEvent.GATEWAY_IN_USING)) {
                RxBus.get().post(Utils.TAG_GATEWAT_USING, Utils.TAG_GATEWAT_USING);
            } else if (tag.equals(ConnectResultEvent.MOVE_RESPONSE)) {
                MoveControlResponse moveControlResponse = (MoveControlResponse) objs[0];
                RxBus.get().post(Utils.TAG_MOVE_RESPONSE, moveControlResponse);
            } else if (tag.equals(ConnectResultEvent.GET_STATUS_SUCESS)) {
                GetStatusResponse getStatusResponse = (GetStatusResponse) objs[0];
                RxBus.get().post(Utils.TAG_GET_DEVICE_STATUS, getStatusResponse);
            } else if (tag.equals(ConnectResultEvent.FAILURE)) {
                RxBus.get().post(Utils.TAG_MOVE_FAILE, Utils.TAG_MOVE_FAILE);
            } else if (tag.equals(ConnectResultEvent.ROOM_IN_RESPONSE)) {
                AppInRoomResponse appInRoomResponse = (AppInRoomResponse) objs[0];
                RxBus.get().post(Utils.TAG_ROOM_IN, appInRoomResponse);
            } else if (tag.equals(ConnectResultEvent.ROOM_OUT_RESPONSE)) {
                AppOutRoomResponse appOutRoomResponse = (AppOutRoomResponse) objs[0];
                RxBus.get().post(Utils.TAG_ROOM_OUT, appOutRoomResponse);
            } else if (tag.equals(ConnectResultEvent.SINGLE_CONNECT)) {
                String id_connect = (String) objs[0];
                RxBus.get().post(Utils.TAG_GATEWAY_SINGLE_CONNECT, id_connect);
            } else if (tag.equals(ConnectResultEvent.SINGLE_DISCONNECT)) {
                String id_disconnect = (String) objs[0];
                RxBus.get().post(Utils.TAG_GATEWAY_SINGLE_DISCONNECT, id_disconnect);
            }
        }

        @Override
        public void _onError(Throwable e) {

        }
    };
}
