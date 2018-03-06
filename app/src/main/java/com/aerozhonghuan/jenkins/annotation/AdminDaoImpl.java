package com.aerozhonghuan.jenkins.annotation;

/**
 * Created by liuk on 2018/3/6 0006.
 */

public class AdminDaoImpl implements AdminDao {
    @Override
    public void register() {
        System.out.println("注册");
    }

    @Override
    public void login() {
        System.out.println("登录");
    }
}
