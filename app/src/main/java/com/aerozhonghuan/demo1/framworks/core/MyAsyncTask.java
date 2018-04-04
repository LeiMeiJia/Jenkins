package com.aerozhonghuan.demo1.framworks.core;

import android.os.AsyncTask;

import com.aerozhonghuan.demo1.framworks.eventbus.SessionFailureEvent;
import com.aerozhonghuan.demo1.framworks.net.ApiResponse;
import com.aerozhonghuan.mytools.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Comparator;


/**
 * 统一处理业务层逻辑
 * Created by Administrator on 2018/4/2.
 */

public abstract class MyAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private String TAG = "MyAsyncTask";
    private ApiResponse mApiResponse = new ApiResponse(509, "Login Fail");

    // 判断ApiResponse Code是否相等
    private Comparator<ApiResponse> comparator = new Comparator<ApiResponse>() {
        @Override
        public int compare(ApiResponse lhs, ApiResponse rhs) {
            if (lhs == null) {
                // negative – o1小于o2
                return 1;
            }
            if (rhs == null) {
                // positive – o1大于o2
                return -1;
            }
            if (lhs.getResultCode() == rhs.getResultCode()) {
                return 0;
            } else if (lhs.getResultCode() > rhs.getResultCode()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result != null && result instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) result;
            LogUtils.logd(TAG, LogUtils.getThreadName() + "### apiResponse.code:" + apiResponse.getResultCode() + ",apiResponse.message:" + apiResponse.getMessage());
            int flag = comparator.compare(apiResponse, mApiResponse);
            if (flag == 0) {
                apiResponse.setMessage("该账号已在其他设备登录，请重新登录");
                try {
                    // 使用EventBus通知业务层，处理逻辑
//                    EventBus.getDefault().post(new SessionFailureEvent());
                    EventBus.getDefault().post(new SessionFailureEvent());
                    LogUtils.logd(TAG, LogUtils.getThreadName() + "EventBus.getDefault().post(msgObj)");
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.loge(TAG, LogUtils.getThreadName(), e);
                }
            }
        }
    }
}
