package com.aerozhonghuan.jenkins.annotation;

/**
 * Created by liuk on 2018/3/6 0006.
 */

public class User {

    //    @UserInject(14)
    @UserInject(age = 18, name = "测试")
    private int age;
    private String name;

    private String eat(String eat) {
        System.out.println("user:" + eat);
        return eat + "真好吃";
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
