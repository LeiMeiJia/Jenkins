package com.aerozhonghuan.demo1.framworks.core;

import com.aerozhonghuan.demo1.framworks.model.UserInfo;
import com.aerozhonghuan.demo1.framworks.api.Api;
import com.aerozhonghuan.demo1.framworks.api.ApiImpl;
import com.aerozhonghuan.demo1.framworks.net.ApiResponse;
import com.aerozhonghuan.mytools.utils.LogUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppActionImpl implements AppAction {

    private String TAG = this.getClass().getSimpleName();
    private Api api;
    static Executor executor = Executors.newCachedThreadPool();

    public AppActionImpl() {
        this.api = new ApiImpl();
    }

    @Override
    public ApiResponse<UserInfo> login(final String userName, final String password, final String deviceId, final ActionCallbackListener<UserInfo> listener) {
        new MyAsyncTask<Void, Void, ApiResponse<UserInfo>>() {
            @Override
            protected ApiResponse<UserInfo> doInBackground(Void... voids) {
                return api.login(userName, password, deviceId);
            }

            @Override
            protected void onPostExecute(final ApiResponse<UserInfo> response) {
                super.onPostExecute(response);
                LogUtils.logd(TAG, LogUtils.getThreadName() + "response:" + response);
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getData());
                    } else {
                        listener.onFailure(response.getResultCode(), response.getMessage());
                    }
                }
            }
        }.executeOnExecutor(executor);
        return null;
    }

    @Override
    public ApiResponse updateUserInfo(final String token, final String rName, final ActionCallbackListener listener) {
        new MyAsyncTask<Void, Void, ApiResponse>() {
            @Override
            protected ApiResponse<UserInfo> doInBackground(Void... voids) {
                return api.updateUserInfo(token, rName);
            }

            @Override
            protected void onPostExecute(final ApiResponse response) {
                super.onPostExecute(response);
                LogUtils.logd(TAG, LogUtils.getThreadName() + "response:" + response);
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getData());
                    } else {
                        listener.onFailure(response.getResultCode(), response.getMessage());
                    }
                }
            }
        }.executeOnExecutor(executor);
        return null;
    }

}
