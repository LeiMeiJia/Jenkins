package com.aerozhonghuan.jenkins.network.engine;

import android.os.Handler;
import android.os.Looper;

import com.aerozhonghuan.jenkins.BuildConfig;
import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.jenkins.network.api.HttpApi;
import com.aerozhonghuan.mytools.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * 设置Http请求
 * Created by Administrator on 2018/2/1.
 */

public class HttpRequest {

    private static final String TAG = HttpRequest.class.getSimpleName();
    private static HttpRequest httpRequest;
    private HttpApi httpApi;

    private HttpRequest() {
        // HttpLoggingInterceptor 是一个拦截器，用于输出网络请求和结果的 Log
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // 设置日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
//                .retryOnConnectionFailure(false) // 禁止重试，方法为设置出现错误进行重新连接。
                // 应用拦截器  让所有网络请求都附上你的拦截器。设置重试
                // 当网络链路异常时，也就是网络请求未发送出去时，网络请求无响应，NetworkInterceptor会抛出异常，不会尝试重新连接
//                .addInterceptor(new NetworkInterceptor(2,0))
                //  网络拦截器
                // 当网络链路正常时，自定义请求重试时，发送的请求必须成功一次后才能尝试再次连接，否则会崩溃
                // 当网络链路异常时，也就是网络请求未发送出去时，网络请求无响应，会抛出网络异常，不会崩溃，且不会尝试重新连接
//                .addNetworkInterceptor(new NetworkInterceptor(2,0))
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.FAW_URL)
                .client(okHttpClient)
                .build();
        // 网络接口
        httpApi = retrofit.create(HttpApi.class);
        // 判断当前线程是否是主线程
        Looper looper = Looper.myLooper();
        Looper mainLooper = Looper.getMainLooper();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "looper:" + looper);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "mainLooper:" + mainLooper);

        // 打印handler
//        Handler handler = new Handler(Looper.getMainLooper());
        Handler handler = new Handler();
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

    // 发送网络请求，请求执行在子线程
    public void sendHttpRequest(Call<ResponseBody> call,HttpResponse httpResponse) {
        // 每个Call实例可以且只能执行一次请求，不能使用相同的对象再次执行execute()或enqueue()。
        CallAdapter callAdapter = new CallAdapter(httpResponse);
        call.enqueue(callAdapter);
    }

}
