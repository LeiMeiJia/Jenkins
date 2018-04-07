package com.aerozhonghuan.jenkins.mvp;

import com.aerozhonghuan.jenkins.network.api.HttpApi;
import com.aerozhonghuan.jenkins.network.engine.HttpRequest;

/**
 * Presenter基类
 * Created by Administrator on 2018/2/1.
 */

public interface BasePresenter {

    void cancel();

    interface LoginPresenter extends BasePresenter {
        void login(String username, String password, String deviceId, String type);
    }

    interface CarListPresenter extends BasePresenter {
        void carPageList(String token, int page_size, int page_number, String processCode);
    }

}
