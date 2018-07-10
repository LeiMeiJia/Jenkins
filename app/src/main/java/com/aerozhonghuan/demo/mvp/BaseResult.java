package com.aerozhonghuan.demo.mvp;

import com.aerozhonghuan.demo.mvp.entity.CarPageListData;
import com.aerozhonghuan.demo.mvp.entity.UserInfo;

/**
 * 业务层回调方法
 * Created by Administrator on 2018/2/1.
 */

public interface BaseResult {

    interface LoginResult {
        void onLoginSuccess(UserInfo userInfo);

        void onLoginFail(int resultCode, String errorMsg);
    }

    interface CarListResult {
        void onCarListSuccess(CarPageListData carListInfo);

        void onCarListFail(int resultCode, String errorMsg);
    }

    interface UpdateInfoResult {
        void onUpdateInfoSuccess();

        void onUpdateInfoFail(int resultCode, String errorMsg);
    }

}
