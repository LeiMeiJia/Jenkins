package com.aerozhonghuan.demo1.framworks.core;


import com.aerozhonghuan.demo1.framworks.model.UserInfo;
import com.aerozhonghuan.demo1.framworks.net.ApiResponse;

public interface AppAction {

    ApiResponse<UserInfo> login(String userName, String password, String deviceId, ActionCallbackListener<UserInfo> listener);

    ApiResponse updateUserInfo(String token, String rName, ActionCallbackListener listener);

}
