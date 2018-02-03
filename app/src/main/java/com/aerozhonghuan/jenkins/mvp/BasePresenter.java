package com.aerozhonghuan.jenkins.mvp;

/**
 * Presenter基类
 * Created by Administrator on 2018/2/1.
 */

public interface BasePresenter {

    void cancelHttpRequest();

    interface LoginPresenter extends BasePresenter {
        void login(String username, String password, String deviceId, String type);
    }

}
