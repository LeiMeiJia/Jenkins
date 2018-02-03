package com.aerozhonghuan.jenkins.mvp.entity;

import java.io.Serializable;
import java.util.List;

public class CarInfo implements Serializable {

    private static final long serialVersionUID = -1;

    private String vin;
    private String serise;
    private String model;
    private String trader;
    private String driverCab;
    private String orderNumber;
    private String offLineTime;
    private List<LinkInfo> linkInfo;
    private String currentProcessCode;
    private String currentProcessInfo;
    private String currentProcessTime;
    private String lon;
    private String lat;
    private String address;
    private String position;

    private String notF9Code;
    private String vinOld;
    private String status;


    private String cxm;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDriverCab() {
        return driverCab;
    }

    public void setDriverCab(String driverCab) {
        this.driverCab = driverCab;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSerise() {
        return serise;
    }

    public void setSerise(String serise) {
        this.serise = serise;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getOffLineTime() {
        return offLineTime;
    }

    public void setOffLineTime(String offLineTime) {
        this.offLineTime = offLineTime;
    }

    public String getCurrentProcessCode() {
        return currentProcessCode;
    }

    public void setCurrentProcessCode(String currentProcessCode) {
        this.currentProcessCode = currentProcessCode;
    }

    public String getCurrentProcessInfo() {
        return currentProcessInfo;
    }

    public void setCurrentProcessInfo(String currentProcessInfo) {
        this.currentProcessInfo = currentProcessInfo;
    }

    public String getCurrentProcessTime() {
        return currentProcessTime;
    }

    public void setCurrentProcessTime(String currentProcessTime) {
        this.currentProcessTime = currentProcessTime;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public List<LinkInfo> getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(List<LinkInfo> linkInfo) {
        this.linkInfo = linkInfo;
    }

    public String getNotF9Code() {
        return notF9Code;
    }

    public void setNotF9Code(String notF9Code) {
        this.notF9Code = notF9Code;
    }

    public String getVinOld() {
        return vinOld;
    }

    public void setVinOld(String vinOld) {
        this.vinOld = vinOld;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCxm() {
        return cxm;
    }

    public void setCxm(String cxm) {
        this.cxm = cxm;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "vin='" + vin + '\'' +
                ", serise='" + serise + '\'' +
                ", model='" + model + '\'' +
                ", trader='" + trader + '\'' +
                ", driverCab='" + driverCab + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", offLineTime='" + offLineTime + '\'' +
                ", linkInfo=" + linkInfo +
                ", currentProcessCode='" + currentProcessCode + '\'' +
                ", currentProcessInfo='" + currentProcessInfo + '\'' +
                ", currentProcessTime='" + currentProcessTime + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", notF9Code='" + notF9Code + '\'' +
                ", vinOld='" + vinOld + '\'' +
                ", status='" + status + '\'' +
                ", cxm='" + cxm + '\'' +
                '}';
    }
}
