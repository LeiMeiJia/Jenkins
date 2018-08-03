package com.aerozhonghuan.demo.mvp.presenter;

import android.os.AsyncTask;

import com.aerozhonghuan.demo.mvp.BasePresenter;
import com.aerozhonghuan.demo.mvp.BaseResult;
import com.aerozhonghuan.demo.mvp.entity.UserInfo;
import com.aerozhonghuan.demo.mvp.model.ApiResponse;
import com.aerozhonghuan.demo.mvp.model.NetCallAdapter;
import com.aerozhonghuan.demo.mvp.model.ResponseCallback;
import com.aerozhonghuan.demo.mvp.model.http.HttpRequest;
import com.aerozhonghuan.demo.mvp.model.retrofit.RetrofitRequest;
import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by Administrator on 2018/2/1.
 */

public class LoginPresenterImpl implements BasePresenter.LoginPresenter {

    private HttpRequest httpRequest;
    private RetrofitRequest retrofitRequest;
    private BaseResult.LoginResult result;
    private Call<ResponseBody> call;
    private AsyncTask task;

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
                if (result == null) {
                    LogUtils.logd(TAG, LogUtils.getThreadName() + "页面销毁");
                } else {
                    result.onLoginSuccess(userInfo); // 页面正常时回调
                }
            }

            @Override
            public void onFail(int resultCode, String errorMsg) {
                if (result == null) {
                    LogUtils.logd(TAG, LogUtils.getThreadName() + "页面销毁");
                } else {
                    result.onLoginFail(resultCode, errorMsg);
                }
            }
        });
        // 调用网络层
//        task = httpRequest.loginGet(callback);
        call = retrofitRequest.loginGet(callback);
    }


    @Override
    public void onDestroy() {
//        httpRequest.cancel(task);
        // 取消网络请求
        retrofitRequest.cancel(call);
        // 中断View层强引用
        result = null;
        System.gc();
    }

}
