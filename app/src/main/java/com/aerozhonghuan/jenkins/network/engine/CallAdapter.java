package com.aerozhonghuan.jenkins.network.engine;

import android.text.TextUtils;

import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuk on 2018/3/29 0029.
 */

public class CallAdapter implements Callback<ResponseBody> {

    private static final String TAG = HttpRequest.class.getSimpleName();
    private Type type;
    private HttpCallback httpCallback;
    // 代表着返回对象
    private ApiResponse apiResponse;

    public CallAdapter(Type type, HttpCallback httpCallback) {
        this.httpCallback = httpCallback;
        this.type = type;
        apiResponse = new ApiResponse(-1, "网络异常");

    }
    // retrofit 响应结果执行在UI线程，不过OkHttp的onResponse结果执行在子线程中
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "response:" + response);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "code:" + response.code());
        ResponseBody responseBody = response.body(); //response 不能被解析的情况下，response.body()会返回null
        if (responseBody != null && response.isSuccessful()) { // 网络层200
            try {
                String result = new String(responseBody.bytes());
                LogUtils.logd(TAG, LogUtils.getThreadName() + "result:" + result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    apiResponse = gson.fromJson(result, type);
                    httpCallback.requestSuccess(apiResponse);
                } else {
                    httpCallback.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
                httpCallback.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
            }
        } else {
            LogUtils.logd(TAG, LogUtils.getThreadName() + "Response is not successful");
            httpCallback.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
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
            httpCallback.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
        }
        if (t instanceof RuntimeException){
            //请求失败
            httpCallback.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
        }
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isExecuted:" + call.isExecuted());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "isCanceled:" + call.isCanceled());
    }
}
