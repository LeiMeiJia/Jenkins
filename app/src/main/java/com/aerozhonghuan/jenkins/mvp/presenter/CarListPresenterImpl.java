package com.aerozhonghuan.jenkins.mvp.presenter;


import com.aerozhonghuan.jenkins.mvp.BasePresenter;
import com.aerozhonghuan.jenkins.mvp.BaseResult;
import com.aerozhonghuan.jenkins.mvp.entity.CarPageListData;
import com.aerozhonghuan.jenkins.mvp.model.HttpCallback;
import com.aerozhonghuan.jenkins.mvp.model.HttpEngine;
import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static android.R.attr.x;


/**
 * Created by Administrator on 2018/2/1.
 */

public class CarListPresenterImpl implements BasePresenter.CarListPresenter {

    private BaseResult.CarListResult result;
    private HttpEngine httpEngine;

    public CarListPresenterImpl(BaseResult.CarListResult result) {
        this.result = result;
        httpEngine = HttpEngine.getInstance();
    }

    // 处理网络层
    @Override
    public void carPageList(String token, int page_size, int page_number, String processCode) {

        // 创建返回数据类型
        Type typeToken = new TypeToken<ApiResponse<CarPageListData>>() {
        }.getType();
        httpEngine.carPageList(token, page_size, page_number, processCode, new HttpCallback<CarPageListData>(typeToken) {
            @Override
            public void onSuccess(CarPageListData carPageListData) {
                result.onCarListSuccess(carPageListData);
            }

            @Override
            public void onFail(int resultCode, String errorMsg) {
                result.onCarListFail(resultCode, errorMsg);
            }
        });
    }

    @Override
    public void cancel() {
        httpEngine.cancelRequest("carPageList");
    }
}
