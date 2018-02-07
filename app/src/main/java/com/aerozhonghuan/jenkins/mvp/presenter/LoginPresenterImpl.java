package com.aerozhonghuan.jenkins.mvp.presenter;



import com.aerozhonghuan.jenkins.mvp.BasePresenter;
import com.aerozhonghuan.jenkins.mvp.BasePresenterImpl;
import com.aerozhonghuan.jenkins.mvp.BaseResult;
import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;
import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by Administrator on 2018/2/1.
 */

public class LoginPresenterImpl extends BasePresenterImpl<UserInfo> implements BasePresenter.LoginPresenter {

    private BaseResult.LoginResult result;

    public LoginPresenterImpl(BaseResult.LoginResult result) {
        super();
        this.result = result;
    }

    // 处理网络层
    @Override
    public void login(String username, String password, String deviceId, String type) {
        LogUtils.logd("BasePresenterImpl", LogUtils.getThreadName() + "this1:" + this);
        Call<ResponseBody> call;
        if ("GET".equals(type)) {
            // 创建参数
            HashMap<String, String> hashMap = httpParameters.loginGet(username, password, deviceId);
            call = httpApi.loginGet(hashMap);
        } else {
            // 创建参数
            RequestBody requestBody = httpParameters.loginPost(username, password, deviceId);
            // 创建接口请求
            call = httpApi.loginPost(requestBody);
        }
        // 创建返回数据类型
        Type typeToken = new TypeToken<ApiResponse<UserInfo>>() {
        }.getType();
        // 设置call对象
        setCall(call);
        // 发送请求
        sendHttpRequest(call, typeToken);
    }

    // 当网络状态码为200时，业务层实现自己的回调方法
    @Override
    public void requestSuccess(ApiResponse<UserInfo> apiResponse) {
        // 处理业务层，需注意内存对象的处理
        int resultCode = apiResponse.getResultCode();
        if (resultCode == 200) { // 业务层200
            result.onLoginSuccess(apiResponse.getData());
        } else {
            result.onLoginFail(resultCode, apiResponse.getMessage());
        }
    }

    // 当网络状态码为非200时，业务层实现自己的回调方法
    @Override
    public void requestFail(int resultCode, String errorMsg) {
        result.onLoginFail(resultCode, errorMsg);
    }

}
