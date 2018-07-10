package com.aerozhonghuan.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aerozhonghuan.demo.mvp.BasePresenter;
import com.aerozhonghuan.demo.mvp.BaseResult;
import com.aerozhonghuan.demo.mvp.entity.CarPageListData;
import com.aerozhonghuan.demo.mvp.entity.UserInfo;
import com.aerozhonghuan.demo.mvp.presenter.CarListPresenterImpl;
import com.aerozhonghuan.demo.mvp.presenter.LoginPresenterImpl;
import com.aerozhonghuan.demo.mvp.presenter.UpdateInfoPresenterImpl;
import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class DemoActivity extends AppCompatActivity {

    private final static String TAG = "Demo1Activity";
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        // 获取登录信息
        BasePresenter.LoginPresenter presenter = new LoginPresenterImpl(new BaseResult.LoginResult() {
            @Override
            public void onLoginSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, "userInfo:" + userInfo);
                mUserInfo = userInfo;
            }

            @Override
            public void onLoginFail(int resultCode, String errorMsg) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + " message:" + errorMsg);
            }
        });
        presenter.login();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCars(mUserInfo.getToken());
                update(mUserInfo.getToken());
            }
        });
    }

    // 退出页面时，取消请求回调
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logd(TAG, ">>>>>>>>>");
    }

    private void getCars(String token) {
        BasePresenter.CarListPresenter presenter = new CarListPresenterImpl(new BaseResult.CarListResult() {
            @Override
            public void onCarListSuccess(CarPageListData data) {
                LogUtils.logd(TAG, "carData:" + data);
            }

            @Override
            public void onCarListFail(int resultCode, String errorMsg) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + " message:" + errorMsg);
            }
        });
        presenter.getCars(token);
    }

    private void update(String token) {
        BasePresenter.UpdateInfoPresenter presenter = new UpdateInfoPresenterImpl(new BaseResult.UpdateInfoResult() {
            @Override
            public void onUpdateInfoSuccess() {
                LogUtils.logd(TAG, "onUpdateInfoSuccess:");
            }

            @Override
            public void onUpdateInfoFail(int resultCode, String errorMsg) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + " message:" + errorMsg);
            }
        });
        presenter.updateInfo(token);
    }

}
