package com.game.smartremoteapp.model.http;

import rx.Subscriber;

/**
 * Created by zhouh on 2017/7/5.
 */
public abstract class RequestSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        _onError(e);
    }

    @Override
    public void onNext(T t) {
        _onSuccess(t);
    }

    public abstract void _onSuccess(T t);

    public abstract void _onError(Throwable e);
}
