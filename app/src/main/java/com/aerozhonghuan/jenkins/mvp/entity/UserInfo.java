package com.aerozhonghuan.jenkins.mvp.entity;


import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1;
    private int id;
    private String accountId;
    private String accountName;
    private String address;
    private String phone;
    private String roleCode;
    private String serviceCode = "";
    private String serviceStationName;
    private String token;
    private String password = "";
    private String isAuto = "false";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceStationName() {
        return serviceStationName;
    }

    public void setServiceStationName(String serviceStationName) {
        this.serviceStationName = serviceStationName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("id=").append(id);
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append(", accountName='").append(accountName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", roleCode='").append(roleCode).append('\'');
        sb.append(", serviceCode='").append(serviceCode).append('\'');
        sb.append(", serviceStationName='").append(serviceStationName).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", isAuto='").append(isAuto).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
