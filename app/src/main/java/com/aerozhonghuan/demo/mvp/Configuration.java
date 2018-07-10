package com.aerozhonghuan.demo.mvp;

import com.aerozhonghuan.jenkins.BuildConfig;

public class Configuration {
    public static final String faw_url = BuildConfig.FAW_URL;
    public static final String hy_url = BuildConfig.HY_URL;

    public static final String fawLogin = faw_url + "qingqi/product/login";
    public static final String updateUserInfo = faw_url + "qingqi/product/updateUserInfo";
    public static final String carPageList = faw_url + "qingqi/product/carPageList";
    public static final String hyLogin = hy_url + "api/hongyan/serverstation/login";
}