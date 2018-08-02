package com.aerozhonghuan.demo.net.retrofit;

import android.text.TextUtils;

import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.aerozhonghuan.mytools.utils.LogUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuk on 2018/3/29 0029.
 */

public class CallAdapter implements Callback<ResponseBody> {

    private static final String TAG = CallAdapter.class.getSimpleName();
    private NetCallback callback;

    public CallAdapter(NetCallback callback) {
        this.callback = callback;
    }

    // retrofit 响应结果执行在UI线程，不过OkHttp的onResponse结果执行在子线程中
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "response:" + response);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "code:" + response.code());
        if (response.isSuccessful()) { // 网络层200
            ResponseBody responseBody = response.body(); //response 不能被解析的情况下，response.body()会返回null
            try {
                if (responseBody != null) {
                    String result = new String(responseBody.bytes());
                    LogUtils.logd(TAG, LogUtils.getThreadName() + "result:" + result);
                    if (!TextUtils.isEmpty(result)) {
                        callback.netSuccess(result);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFail();
            }
        } else {     // 404，500 时执行
            callback.netFail(response.code(), "网络异常");
        }
        //
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isExecuted:" + call.isExecuted());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isCanceled:" + call.isCanceled());
    }

    //  当一个请求取消时，回调方法onFailure()会执行，而onFailure()方法在没有网络或网络超时的时候也会执行。
    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "onFailure" + t.toString());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isExecuted:" + call.isExecuted());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isCanceled:" + call.isCanceled());
        // 无网络，网络超时执行
        if (!call.isCanceled()) {
            onFail();
        } else {
            LogUtils.logd(TAG, LogUtils.getThreadName() + "取消请求");
        }
    }

    private void onFail() {
        callback.netFail(-1, "网络异常");
    }
}
