package com.aerozhonghuan.jenkins.mvp;

import com.aerozhonghuan.jenkins.network.api.HttpApi;
import com.aerozhonghuan.jenkins.network.engine.HttpParameters;
import com.aerozhonghuan.jenkins.network.engine.HttpRequest;
import com.aerozhonghuan.jenkins.network.engine.HttpResult;
import com.aerozhonghuan.mytools.utils.LogUtils;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 控制网络请求
 * Created by Administrator on 2018/2/1.
 */

public abstract class BasePresenterImpl<T> implements BasePresenter, HttpResult<T> {

    private static final String TAG = BasePresenterImpl.class.getSimpleName();
    private static final HttpRequest httpRequest = HttpRequest.getInstance();
    protected static final HttpParameters httpParameters = HttpParameters.getInstance();
    protected static final HttpApi httpApi = httpRequest.getHttpApi();
    private Call<ResponseBody> call;

    // 发送请求
    protected void sendHttpRequest(Call<ResponseBody> call, Type typeToken) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "this:" + this);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "httpRequest:" + httpRequest);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "httpParameters:" + httpParameters);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "httpApi:" + httpApi);
        // 每个Call实例可以且只能执行一次请求，不能使用相同的对象再次执行execute()或enqueue()。
        httpRequest.setHttpRequest(call, typeToken, this);
    }

    // 取消请求
    @Override
    public void cancelHttpRequest() {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "call:" + call);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "cancel:" + call.isCanceled());
        if (call != null && !call.isCanceled()) {
            call.cancel();
            LogUtils.logd(TAG, LogUtils.getThreadName() + "取消请求：" + call.isCanceled());
            call = null;
        }
    }

    // 设置Call
    protected void setCall(Call<ResponseBody> call) {
        this.call = call;
    }
}
