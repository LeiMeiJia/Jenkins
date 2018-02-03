package com.aerozhonghuan.jenkins.mvp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class CarPageListData implements Serializable {

    private static final long serialVersionUID = -1;
    private int total;
    private int page_total;
    private List<CarInfo> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public List<CarInfo> getList() {
        return list;
    }

    public void setList(List<CarInfo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CarPageListData{");
        sb.append("list=").append(list);
        sb.append(", total=").append(total);
        sb.append(", page_total=").append(page_total);
        sb.append('}');
        return sb.toString();
    }
}
