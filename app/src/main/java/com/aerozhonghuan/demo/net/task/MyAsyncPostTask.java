package com.aerozhonghuan.demo.net.task;

import android.os.AsyncTask;
import android.util.Log;

import com.aerozhonghuan.demo.net.callback.HttpCallback;
import com.aerozhonghuan.demo.net.callback.MessageStatus;
import com.aerozhonghuan.demo.net.core.HttpGetRequest;
import com.aerozhonghuan.demo.net.core.HttpPostRequest;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;


/**
 * Post 异步任务
 */
public class MyAsyncPostTask extends AsyncTask<Void, Void, String> {

    private String TAG = "MyAsyncPostTask";
    private HttpPostRequest httpRequest;
    private String url;
    private Map<String, Object> hashMap;
    private HttpCallback callback;

    public MyAsyncPostTask(String url, Map<String, Object> hashMap, HttpCallback callback) {
        httpRequest = HttpPostRequest.getInstance();
        this.url = url;
        this.hashMap = hashMap;
        this.callback = callback;
    }


    @Override
    protected String doInBackground(Void... voids) {
        if (!isCancelled()) {
            return httpRequest.post(url, hashMap);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        Log.d(TAG, LogUtils.getThreadName() + "response:" + response);
        if (callback != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<MessageStatus>() {
            }.getType();
            MessageStatus status = gson.fromJson(response, type);
            if (status.getCode() == 200) {
                callback.responseSuccess(status.getMsg());
            } else {
                callback.responseFail(status.getCode(), status.getMsg());
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(TAG, LogUtils.getThreadName());
    }
}
