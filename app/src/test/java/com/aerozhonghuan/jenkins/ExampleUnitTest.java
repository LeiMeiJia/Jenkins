package com.aerozhonghuan.jenkins;

import com.aerozhonghuan.jenkins.annotation.User;
import com.aerozhonghuan.jenkins.annotation.UserInject;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() throws Exception{
        User user = new User();

        // 1、获取对象的字节码信息
        Class<User> clazz = (Class<User>) ClassLoader.getSystemClassLoader().loadClass("com.aerozhonghuan.jenkins.annotation.User");

        // 2、获取当前字段上的注解类信息
        Field declaredFieldAge = clazz.getDeclaredField("age");
        UserInject userInject = declaredFieldAge.getAnnotation(UserInject.class);
        // 3、获取自定义注解类上的参数
        int age = userInject.age();
        String name = userInject.name();

        // 4、通过反射机制赋值
        Field declaredFieldName = clazz.getDeclaredField("name");

        // 暴力破解
        declaredFieldAge.setAccessible(true);
        declaredFieldName.setAccessible(true);

        declaredFieldAge.set(user, age);
        declaredFieldName.set(user,name);

        Method eat = clazz.getDeclaredMethod("eat", String.class);
        eat.setAccessible(true);
        Object result = eat.invoke(user, "吃饭");

        System.out.println("user:" + user);
        System.out.println("result:" + result);
    }

}