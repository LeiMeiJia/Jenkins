package com.aerozhonghuan.jenkins.rxjava;

import java.util.ArrayList;

/**
 * Created by LiuK on 2016/6/3
 */
public class Student {

    private String name;
    private ArrayList<String> arrayList = new ArrayList<String>();

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public Student(String name) {
        this.name = name;
        this.arrayList.add("Java");
        this.arrayList.add("android");
        this.arrayList.add("c++");
    }

    public String getName() {
        return name;
    }

}
