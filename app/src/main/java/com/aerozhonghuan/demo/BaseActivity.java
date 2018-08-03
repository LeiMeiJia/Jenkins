package com.aerozhonghuan.demo;

import android.support.v7.app.AppCompatActivity;

import com.aerozhonghuan.demo.mvp.BasePresenter;
import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 * 1、页面销毁时，统一取消网络回调
 * 2、中断Presenter强引用
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final static String TAG = "BaseActivity";
    private BasePresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter = bindPresenter();
    }

    /**
     * 退出页面时，取消请求回调
     *
     * 当页面存在多个网络请求时，需要复写此方法进行单独处理
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logd(TAG, ">>>>>>>>>");
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
            System.gc();
        }
    }

    protected abstract BasePresenter bindPresenter();

}
