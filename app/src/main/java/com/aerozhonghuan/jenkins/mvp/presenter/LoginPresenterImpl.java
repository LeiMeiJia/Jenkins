package com.aerozhonghuan.jenkins.mvp.presenter;


import com.aerozhonghuan.jenkins.mvp.BasePresenter;
import com.aerozhonghuan.jenkins.mvp.BaseResult;
import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;
import com.aerozhonghuan.jenkins.mvp.model.HttpCallback;
import com.aerozhonghuan.jenkins.mvp.model.HttpEngine;
import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * Created by Administrator on 2018/2/1.
 */

public class LoginPresenterImpl implements BasePresenter.LoginPresenter {

    private BaseResult.LoginResult result;
    private HttpEngine httpEngine;

    public LoginPresenterImpl(BaseResult.LoginResult result) {
        this.result = result;
        httpEngine = HttpEngine.getInstance();
    }

    // 处理网络层
    @Override
    public void login(String username, String password, String deviceId, String type) {

        // 创建返回数据类型
        Type typeToken = new TypeToken<ApiResponse<UserInfo>>() {
        }.getType();
        if ("GET".equals(type)) {
            httpEngine.loginGet(username, password, deviceId, new HttpCallback<UserInfo>(typeToken) {
                @Override
                public void onSuccess(UserInfo userInfo) {
                    result.onLoginSuccess(userInfo);
                }

                @Override
                public void onFail(int resultCode, String errorMsg) {
                    result.onLoginFail(resultCode, errorMsg);
                }
            });
        } else {
            httpEngine.loginPost(username, password, deviceId, new HttpCallback<UserInfo>(typeToken) {
                @Override
                public void onSuccess(UserInfo userInfo) {
                    result.onLoginSuccess(userInfo);
                }

                @Override
                public void onFail(int resultCode, String errorMsg) {
                    result.onLoginFail(resultCode, errorMsg);
                }
            });
        }
    }

    @Override
    public void cancel() {
        httpEngine.cancelRequest("loginGet");
    }
}
