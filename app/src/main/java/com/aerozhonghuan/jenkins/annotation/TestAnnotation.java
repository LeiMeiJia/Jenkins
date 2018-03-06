package com.aerozhonghuan.jenkins.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * 通过反射创建对象
 * 通过反射运行配置文件的内容
 * 通过反射越过泛型检查,泛型在编译时有效，在运行时会跳过检查
 * <p>
 * 动态代理，通过反射生成一个代理对象
 * <p>
 * Created by liuk on 2018/3/6 0006.
 */

public class TestAnnotation {


    public void test() throws Exception {

        User user = new User();

        // 1、获取对象的字节码信息
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
        declaredFieldName.set(user, name);

        Method eat = clazz.getDeclaredMethod("eat", String.class);
        eat.setAccessible(true);
        Object result = eat.invoke(user, "吃饭");

    }

    // 代理对象
    public void testProxy() {
        AdminDao adminDao1 = new AdminDaoImpl();
        adminDao1.register();
        adminDao1.login();
        System.out.println("------------");

        // 添加代理对象
        MyInvocationHandler my = new MyInvocationHandler(adminDao1);
        AdminDao adminDao2 = (AdminDao) Proxy.newProxyInstance(AdminDao.class.getClassLoader(),
                AdminDao.class.getInterfaces(), my);
        adminDao2.register();
        adminDao2.login();
    }
}
