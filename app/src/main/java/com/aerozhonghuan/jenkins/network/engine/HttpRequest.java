package com.aerozhonghuan.jenkins.network.engine;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.aerozhonghuan.jenkins.BuildConfig;
import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.jenkins.network.api.HttpApi;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 设置Http请求
 * Created by Administrator on 2018/2/1.
 */

public class HttpRequest {

    private static final String TAG = HttpRequest.class.getSimpleName();
    private static HttpRequest httpRequest;
    private HttpApi httpApi;
    // 代表着返回对象
    private ApiResponse apiResponse;

    private HttpRequest() {
        // HttpLoggingInterceptor 是一个拦截器，用于输出网络请求和结果的 Log
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // 设置日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
//                .retryOnConnectionFailure(false) // 禁止重试，方法为设置出现错误进行重新连接。
                .addNetworkInterceptor(new NetworkInterceptor()) //  让所有网络请求都附上你的拦截器。设置重试
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(okHttpClient)
                .build();
        // 网络接口
        httpApi = retrofit.create(HttpApi.class);
        // 打印handler
        Handler handler = new Handler(Looper.getMainLooper());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "handler:" + handler.getLooper().getThread().getName());
    }

    public static synchronized HttpRequest getInstance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public HttpApi getHttpApi() {
        return httpApi;
    }

    // 发送网络请求
    public void setHttpRequest(Call<ResponseBody> call, final Type type, final HttpResult httpResult) {
        apiResponse = new ApiResponse(-1, "网络异常");
        //异步执行网络请求，请求执行在子线程
        call.enqueue(new Callback<ResponseBody>() {
            // retrofit 响应结果执行在UI线程，不过OkHttp的onResponse结果执行在子线程中
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "response:" + response);
                LogUtils.logd(TAG, LogUtils.getThreadName() + "code:" + response.code());
                ResponseBody responseBody = response.body(); //response 不能被解析的情况下，response.body()会返回null
                if (response.isSuccessful() && responseBody != null) { // 网络层200
                    try {
                        String result = new String(responseBody.bytes());
                        LogUtils.logd(TAG, LogUtils.getThreadName() + "result:" + result);
                        if (!TextUtils.isEmpty(result)) {
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                            apiResponse = gson.fromJson(result, type);
                            httpResult.requestSuccess(apiResponse);
                        } else {
                            httpResult.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpResult.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
                    }
                } else {
                    LogUtils.logd(TAG, LogUtils.getThreadName() + "Response is not successful");
                    httpResult.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
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
                    Log.d(TAG, "apiResponse:" + apiResponse);
                    httpResult.requestFail(apiResponse.getResultCode(), apiResponse.getMessage());
                }
                LogUtils.logd(TAG, LogUtils.getThreadName() + "isExecuted:" + call.isExecuted());
                LogUtils.logd(TAG, LogUtils.getThreadName() + "isCanceled:" + call.isCanceled());
            }
        });
    }

}
