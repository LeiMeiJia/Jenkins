package com.aerozhonghuan.demo.mvp.entity;

import java.io.Serializable;

/**
 * 步骤信息
 * Created by Administrator on 2016/11/3.
 */
public class LinkInfo implements Serializable {

    private static final long serialVersionUID = -1;

    private String linkName;
    private int linkStatus;

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public int getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(int linkStatus) {
        this.linkStatus = linkStatus;
    }

    @Override
    public String toString() {
        return "LinkInfo{" +
                "linkName='" + linkName + '\'' +
                ", linkStatus=" + linkStatus +
                '}';
    }
}
