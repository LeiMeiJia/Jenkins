package com.aerozhonghuan.jenkins.network.engine;

import android.text.TextUtils;

import com.aerozhonghuan.mytools.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuk on 2018/3/29 0029.
 */

public class CallAdapter implements Callback<ResponseBody> {

    private static final String TAG = HttpRequest.class.getSimpleName();
    private HttpResponse httpResponse;

    public CallAdapter(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    // retrofit 响应结果执行在UI线程，不过OkHttp的onResponse结果执行在子线程中
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "response:" + response);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "code:" + response.code());
        // 需考虑网络超时未响应的情况，404，500，无网络
        if (response.isSuccessful()) { // 网络层200
            ResponseBody responseBody = response.body(); //response 不能被解析的情况下，response.body()会返回null
            if (responseBody != null) {
                try {
                    String result = new String(responseBody.bytes());
                    LogUtils.logd(TAG, LogUtils.getThreadName() + "result:" + result);
                    if (!TextUtils.isEmpty(result)) {
                        httpResponse.responseSuccess(result);
                    } else {
                        onFail();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onFail();
                }
            } else {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "Response is not successful");
                onFail();
            }
        } else {
            httpResponse.responseFail(response.code(), "网络异常");
        }
        //
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isExecuted:" + call.isExecuted());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isCanceled:" + call.isCanceled());
    }

    //  当一个请求取消时，回调方法onFailure()会执行，而onFailure()方法在没有网络或网络错误的时候也会执行。
    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "onFailure");
        if (!call.isCanceled()) {
            onFail();
        }
        if (t instanceof RuntimeException) {
            //请求失败
            onFail();
        }
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isExecuted:" + call.isExecuted());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isCanceled:" + call.isCanceled());
    }

    private void onFail() {
        httpResponse.responseFail(-1, "网络异常");
    }
}
