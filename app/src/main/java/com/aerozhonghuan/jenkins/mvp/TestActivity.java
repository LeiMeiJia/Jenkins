package com.aerozhonghuan.jenkins.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.jenkins.mvp.entity.CarPageListData;
import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;
import com.aerozhonghuan.jenkins.mvp.presenter.CarListPresenterImpl;
import com.aerozhonghuan.jenkins.mvp.presenter.LoginPresenterImpl;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();
    private BasePresenter.LoginPresenter login;
    private BasePresenter.CarListPresenter carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        login = getLoginPresenter();
        carList = getCarListPresenter();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for (int i = 0; i < 5; i++) {
//                    BasePresenter.LoginPresenter login = getLoginPresenter();
//                    list.add(login);
//                    login.login("wanghb", "Aa123456", "123", "POST");
//                }


                login.login("linqi", "Qweasd1", "123", "GET");

            }
        });
    }

    private BasePresenter.LoginPresenter getLoginPresenter() {
        return new LoginPresenterImpl(new BaseResult.LoginResult() {
            @Override
            public void onLoginSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "userInfo:" + userInfo);
                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();

//                carList.carPageList(userInfo.getToken(), 1, 20, "00040005");
            }

            @Override
            public void onLoginFail(int resultCode, String errorMsg) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "onFail:" + errorMsg);
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BasePresenter.CarListPresenter getCarListPresenter() {
        return new CarListPresenterImpl(new BaseResult.CarListResult() {
            @Override
            public void onCarListSuccess(CarPageListData info) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "CarPageListData:" + info);
                Toast.makeText(getApplicationContext(), "请求成功2", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCarListFail(int resultCode, String errorMsg) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "onFail:" + errorMsg);
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        login.cancel();
        carList.cancel();
    }
}
