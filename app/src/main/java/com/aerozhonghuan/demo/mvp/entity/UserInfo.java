package com.aerozhonghuan.demo.mvp.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1;

    private String accountNickname;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("accountNickname='").append(accountNickname).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
