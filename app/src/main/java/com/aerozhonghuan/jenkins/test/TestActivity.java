package com.aerozhonghuan.jenkins.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.jenkins.mvp.BasePresenter;
import com.aerozhonghuan.jenkins.mvp.BaseResult;
import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;
import com.aerozhonghuan.jenkins.mvp.presenter.LoginPresenterImpl;
import com.aerozhonghuan.mytools.utils.LogUtils;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();
    private ArrayList<BasePresenter> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 5; i++) {
                    BasePresenter.LoginPresenter login = getLoginPresenter();
                    list.add(login);
                    login.login("wanghb", "Aa123456", "123", "POST");
                }
            }
        });
    }

    private BasePresenter.LoginPresenter getLoginPresenter() {
        return new LoginPresenterImpl(new BaseResult.LoginResult() {
            @Override
            public void onLoginSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "userInfo:" + userInfo);
                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginFail(int resultCode, String errorMsg) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "onFail:" + errorMsg);
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "list:" + list.size());
        for (BasePresenter presenter : list) {
            LogUtils.logd(TAG, LogUtils.getThreadName() + "presenter:" + presenter);
            presenter.cancelHttpRequest();
        }
    }
}
