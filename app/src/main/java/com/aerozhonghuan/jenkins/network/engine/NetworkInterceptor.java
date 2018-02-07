package com.aerozhonghuan.jenkins.network.engine;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/2/6.
 */

public class NetworkInterceptor implements Interceptor {
    private static final String TAG = NetworkInterceptor.class.getSimpleName();
    private int maxRetry = 1;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG, "retryNum=" + retryNum);
        okhttp3.Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            Log.d(TAG, "retryNum=" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}
