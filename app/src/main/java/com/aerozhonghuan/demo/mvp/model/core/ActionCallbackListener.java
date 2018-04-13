package com.aerozhonghuan.demo.mvp.model.core;

public abstract class ActionCallbackListener<T> {

    abstract void onSuccess(T obj);

    abstract void onFailure(int resultCode, String message);

    void onCookie() {

    }

}
