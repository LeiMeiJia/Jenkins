package com.aerozhonghuan.jenkins.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liuk on 2018/3/6 0006.
 */

public class TestAnnotation {


    public void test() throws Exception {

        User user = new User();

        // 1、获取对象的字节码信息，通过反射创建对象
//        user.getClass();
//        Class.forName("com.aerozhonghuan.jenkins.annotation.User");
//        ClassLoader.getSystemClassLoader().loadClass("com.aerozhonghuan.jenkins.annotation.User");
        Class<User> clazz = User.class;

        // 2、获取当前字段上的注解类信息
        Field declaredFieldAge = clazz.getDeclaredField("age");
        UserInject userInject = declaredFieldAge.getAnnotation(UserInject.class);
        // 3、获取自定义注解类上的参数
        int age = userInject.age();
        String name = userInject.name();

        // 4、通过反射机制赋值
//        clazz.getField() //  只能获取public权限
        Field declaredFieldName = clazz.getDeclaredField("name");

        // 暴力破解
        declaredFieldAge.setAccessible(true);
        declaredFieldName.setAccessible(true);

        declaredFieldAge.set(user, age);
        declaredFieldName.set(user,name);

        Method eat = clazz.getDeclaredMethod("eat", String.class);
        eat.setAccessible(true);
        Object result = eat.invoke(user, "吃饭");

    }
}
