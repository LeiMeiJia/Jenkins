package com.aerozhonghuan.demo.mvp;

import com.aerozhonghuan.jenkins.BuildConfig;

public class Configuration {
    public static final String faw_url = BuildConfig.FAW_URL;
    public static final String hy_url = BuildConfig.HY_URL;

    public static String fawLogin = faw_url + "qingqi/product/login";
    public static String updateUserInfo = faw_url + "qingqi/product/updateUserInfo";
    public static String carPageList = faw_url + "qingqi/product/carPageList";
    public static String hyLogin = hy_url + "hyapipro/hongyan/serverstation/login";
}