package com.aerozhonghuan.demo1.framworks.core;

public interface ActionCallbackListener<T> {

    void onSuccess(T obj);

    void onFailure(int resultCode, String message);

}
