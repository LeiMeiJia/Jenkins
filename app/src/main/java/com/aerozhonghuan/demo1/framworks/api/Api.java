package com.aerozhonghuan.demo1.framworks.api;


import com.aerozhonghuan.demo1.framworks.model.UserInfo;
import com.aerozhonghuan.demo1.framworks.net.ApiResponse;

public interface Api {

    ApiResponse<UserInfo> login(String userName, String password, String deviceId);

    ApiResponse updateUserInfo(String token, String rName);
}
