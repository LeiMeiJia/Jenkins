package com.aerozhonghuan.demo.net.callback;


/**
 * 网络层回调方法
 * Created by Administrator on 2018/2/1.
 */

public interface HttpCallback {

    /**
     *  网络层200情况下，回调
     * @param result
     */
    void responseSuccess(String result);

    void responseFail(int resultCode, String msg);
}
