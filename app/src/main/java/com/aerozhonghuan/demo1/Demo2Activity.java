package com.aerozhonghuan.demo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aerozhonghuan.demo1.framworks.core.ActionCallbackListener;
import com.aerozhonghuan.demo1.framworks.core.AppAction;
import com.aerozhonghuan.demo1.framworks.model.UserInfo;
import com.aerozhonghuan.jenkins.MyApplication;
import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class Demo2Activity extends AppCompatActivity {

    private final static String TAG = "Demo2Activity";
    private AppAction appAction;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        MyApplication myApplication = (MyApplication) getApplication();
        appAction = myApplication.getAppAction();

        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appAction.updateUserInfo(userInfo.getToken(), "测试", new ActionCallbackListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        LogUtils.logd(TAG, LogUtils.getThreadName());
                    }

                    @Override
                    public void onFailure(int resultCode, String message) {
                        LogUtils.logd(TAG, "resultCode:" + resultCode);
                        LogUtils.logd(TAG, "message:" + message);
                    }
                });
            }
        });


    }

}
