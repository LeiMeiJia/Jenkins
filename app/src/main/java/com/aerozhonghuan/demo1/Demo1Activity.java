package com.aerozhonghuan.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aerozhonghuan.demo1.framworks.core.ActionCallbackListener;
import com.aerozhonghuan.demo1.framworks.core.AppAction;
import com.aerozhonghuan.demo1.framworks.model.UserInfo;
import com.aerozhonghuan.jenkins.MyApplication;
import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class Demo1Activity extends AppCompatActivity {

    private final static String TAG = "Demo1Activity";
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        MyApplication myApplication = (MyApplication) getApplication();
        AppAction appAction = myApplication.getAppAction();

        appAction.login("linqi", "Qweasd1", "1111", new ActionCallbackListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, "userinfo:" + userInfo);
                mUserInfo = userInfo;
            }

            @Override
            public void onFailure(int resultCode, String message) {
                LogUtils.logd(TAG, "resultCode:" + resultCode);
                LogUtils.logd(TAG, "message:" + message);
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Demo1Activity.this, Demo2Activity.class);
                intent.putExtra("userInfo", mUserInfo);
                startActivity(intent);
            }
        });

    }

}
