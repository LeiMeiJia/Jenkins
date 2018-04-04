package com.aerozhonghuan.demo1.framworks;

import com.aerozhonghuan.jenkins.BuildConfig;

public class Configuration {
    public static String server_url = BuildConfig.DEBUG_URL;

    public static String login = server_url + "/qingqi/product/login?";
    public static String updateUserInfo = server_url + "/qingqi/product/updateUserInfo?";
}