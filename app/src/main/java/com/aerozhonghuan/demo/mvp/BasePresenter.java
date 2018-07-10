package com.aerozhonghuan.demo.mvp;

/**
 * Presenter基类
 * Created by Administrator on 2018/2/1.
 */

public interface BasePresenter {

    interface LoginPresenter extends BasePresenter {
        void login();
    }

    interface CarListPresenter extends BasePresenter {
        void getCars(String token);
    }

    interface UpdateInfoPresenter extends BasePresenter {
        void updateInfo(String token);
    }

}
