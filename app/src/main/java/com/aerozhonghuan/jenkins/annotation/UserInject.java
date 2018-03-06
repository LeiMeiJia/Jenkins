package com.aerozhonghuan.jenkins.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuk on 2018/3/6 0006.
 */
// 用于限制当前自定义注解类作用的对象
@Target(ElementType.FIELD)
// 自定义注解类只会在源码中出现，当源码编译成字节码时注解信息会被清除
//@Retention(RetentionPolicy.SOURCE)
// 自定义注解类被编译在字节码中，当虚拟机加载该字节码时注解信息会被清除
//@Retention(RetentionPolicy.CLASS)
// 自定义注解类会被加载到虚拟机中
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInject {

//    int value();
    int age();
    String name();


}
