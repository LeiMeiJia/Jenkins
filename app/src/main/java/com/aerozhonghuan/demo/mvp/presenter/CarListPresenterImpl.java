package com.aerozhonghuan.demo.mvp.presenter;


import com.aerozhonghuan.demo.mvp.BasePresenter;
import com.aerozhonghuan.demo.mvp.BaseResult;
import com.aerozhonghuan.demo.mvp.entity.CarPageListData;
import com.aerozhonghuan.demo.mvp.model.ApiResponse;
import com.aerozhonghuan.demo.mvp.model.ResponseCallback;
import com.aerozhonghuan.demo.mvp.model.http.HttpRequest;
import com.aerozhonghuan.demo.mvp.model.NetCallAdapter;
import com.aerozhonghuan.demo.mvp.model.retrofit.RetrofitRequest;
import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * Created by Administrator on 2018/2/1.
 */

public class CarListPresenterImpl implements BasePresenter.CarListPresenter {

    private HttpRequest httpRequest;
    private RetrofitRequest retrofitRequest;
    private BaseResult.CarListResult result;

    public CarListPresenterImpl(BaseResult.CarListResult result) {
        httpRequest = HttpRequest.getInstance();
        retrofitRequest = RetrofitRequest.getInstance();
        this.result = result;
    }

    // 处理网络层
    @Override
    public void getCars(String token) {
        // 创建返回数据类型
        Type typeToken = new TypeToken<ApiResponse<CarPageListData>>() {
        }.getType();
        // 关联业务层
        NetCallback callback = new NetCallAdapter(typeToken, new ResponseCallback<CarPageListData>() {
            @Override
            public void onSuccess(CarPageListData data) {
                result.onCarListSuccess(data);
            }

            @Override
            public void onFail(int resultCode, String errorMsg) {
                result.onCarListFail(resultCode, errorMsg);
            }
        });
        // 调用网络层
//        httpRequest.getCars(token, callback);
        retrofitRequest.getCars(token, callback);
    }

    @Override
    public void onDestroy() {

    }
}
