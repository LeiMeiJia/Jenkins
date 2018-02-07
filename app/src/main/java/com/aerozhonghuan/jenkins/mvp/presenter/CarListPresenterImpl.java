package com.aerozhonghuan.jenkins.mvp.presenter;


import com.aerozhonghuan.jenkins.mvp.BasePresenter;
import com.aerozhonghuan.jenkins.mvp.BasePresenterImpl;
import com.aerozhonghuan.jenkins.mvp.BaseResult;
import com.aerozhonghuan.jenkins.mvp.entity.CarPageListData;
import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;
import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by Administrator on 2018/2/1.
 */

public class CarListPresenterImpl extends BasePresenterImpl<CarPageListData> implements BasePresenter.CarListPresenter {

    private BaseResult.CarListResult result;

    public CarListPresenterImpl(BaseResult.CarListResult result) {
        super();
        this.result = result;
    }

    // 处理网络层
    @Override
    public void carList(String token, int page_size, int page_number, String processCode) {
        LogUtils.logd("BasePresenterImpl", LogUtils.getThreadName() + "this2:" + this);
        // 创建参数
        HashMap<String, Object> hashMap = httpParameters.carPageList(token, page_size, page_number, processCode);
        // 创建接口请求
        Call<ResponseBody> call = httpApi.carPageList(hashMap);
        // 创建返回数据类型
        Type typeToken = new TypeToken<ApiResponse<CarPageListData>>() {
        }.getType();
        // 设置call对象
        setCall(call);
        // 发送请求
        sendHttpRequest(call, typeToken);
    }

    // 当网络状态码为200时，业务层实现自己的回调方法
    @Override
    public void requestSuccess(ApiResponse<CarPageListData> apiResponse) {
        // 处理业务层
        int resultCode = apiResponse.getResultCode();
        if (resultCode == 200) { // 业务层200
            result.onCarListSuccess(apiResponse.getData());
        } else {
            result.onCarListFail(resultCode, apiResponse.getMessage());
        }
    }

    // 当网络状态码为非200时，业务层实现自己的回调方法
    @Override
    public void requestFail(int resultCode, String errorMsg) {
        result.onCarListFail(resultCode, errorMsg);
    }

}
