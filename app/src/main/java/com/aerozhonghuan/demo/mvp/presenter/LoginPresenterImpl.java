package com.aerozhonghuan.demo.mvp.presenter;

import com.aerozhonghuan.demo.mvp.BasePresenter;
import com.aerozhonghuan.demo.mvp.BaseResult;
import com.aerozhonghuan.demo.mvp.entity.UserInfo;
import com.aerozhonghuan.demo.mvp.model.ApiResponse;
import com.aerozhonghuan.demo.mvp.model.ResponseCallback;
import com.aerozhonghuan.demo.mvp.model.http.HttpRequest;
import com.aerozhonghuan.demo.mvp.model.NetCallAdapter;
import com.aerozhonghuan.demo.mvp.model.retrofit.RetrofitRequest;
import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018/2/1.
 */

public class LoginPresenterImpl implements BasePresenter.LoginPresenter {

    private HttpRequest httpRequest;
    private RetrofitRequest retrofitRequest;
    private BaseResult.LoginResult result;

    public LoginPresenterImpl(BaseResult.LoginResult result) {
        httpRequest = HttpRequest.getInstance();
        retrofitRequest = RetrofitRequest.getInstance();
        this.result = result;
    }

    // 关联网络层与业务层
    @Override
    public void login() {
        // 创建返回数据类型
        Type typeToken = new TypeToken<ApiResponse<UserInfo>>() {
        }.getType();
        // 关联业务层
        NetCallback callback = new NetCallAdapter(typeToken, new ResponseCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                result.onLoginSuccess(userInfo);
            }

            @Override
            public void onFail(int resultCode, String errorMsg) {
                result.onLoginFail(resultCode, errorMsg);
            }
        });
        // 调用网络层
        httpRequest.loginGet(callback);
//        retrofitRequest.loginGet(callback);
    }

}
