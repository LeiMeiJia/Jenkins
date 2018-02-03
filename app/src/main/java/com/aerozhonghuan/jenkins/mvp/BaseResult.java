package com.aerozhonghuan.jenkins.mvp;


import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;

/**
 * 业务层回调方法
 * Created by Administrator on 2018/2/1.
 */

public interface BaseResult {

    interface LoginResult {
        void onLoginSuccess(UserInfo userInfo);

        void onLoginFail(int resultCode, String errorMsg);
    }
}
