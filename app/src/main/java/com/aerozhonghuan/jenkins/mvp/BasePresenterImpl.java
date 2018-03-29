package com.aerozhonghuan.jenkins.mvp;

import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.jenkins.network.api.HttpApi;
import com.aerozhonghuan.jenkins.network.engine.CallAdapter;
import com.aerozhonghuan.jenkins.network.engine.HttpParameters;
import com.aerozhonghuan.jenkins.network.engine.HttpRequest;
import com.aerozhonghuan.jenkins.network.engine.HttpCallback;
import com.aerozhonghuan.mytools.utils.LogUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 控制网络请求
 * Created by Administrator on 2018/2/1.
 */

public abstract class BasePresenterImpl<T> implements BasePresenter, HttpCallback<T> {

    private static final String TAG = BasePresenterImpl.class.getSimpleName();
    protected static final HttpParameters httpParameters = HttpParameters.getInstance();
    private static final HttpRequest httpRequest = HttpRequest.getInstance();
    protected static final HttpApi httpApi = httpRequest.getHttpApi();
    private Call<ResponseBody> call;

    // ======统一处理网络层=======
    // 发送请求
    protected void sendHttpRequest(Call<ResponseBody> call, CallAdapter callAdapter) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + "this:" + this);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "httpRequest:" + httpRequest);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "httpParameters:" + httpParameters);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "httpApi:" + httpApi);
        // 每个Call实例可以且只能执行一次请求，不能使用相同的对象再次执行execute()或enqueue()。
        httpRequest.setHttpRequest(call, callAdapter);
    }

    @Override
    // 取消请求
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

    // ======统一处理业务层=======
    // 当网络状态码为200时，业务层实现自己的回调方法
    @Override
    public void requestSuccess(ApiResponse<T> apiResponse) {
        // 处理业务层，需注意内存对象的处理
        int resultCode = apiResponse.getResultCode();
        if (resultCode == 200) { // 业务层200
            onSuccess(apiResponse.getData());
        } else if (resultCode == 509) {
            onFail(resultCode, apiResponse.getMessage());
            onCookieExpired();
        } else {
            onFail(resultCode, apiResponse.getMessage());
        }
    }

    // 当网络状态码为非200时，业务层实现自己的回调方法
    @Override
    public void requestFail(int resultCode, String errorMsg) {
        onFail(resultCode, errorMsg);
    }

    abstract public void onSuccess(T t);

    abstract public void onFail(int resultCode, String errorMsg);

    private void onCookieExpired() {

    }


}
