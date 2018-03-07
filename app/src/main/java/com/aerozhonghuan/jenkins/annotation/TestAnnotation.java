package com.aerozhonghuan.jenkins.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Java对象运行
 * 1、通过new关键字创建一个对象后，编译生成class文件
 * 2、类加载器将class文件加载到JVM内存中，生成Class对象
 * 3、Class对象是在加载类时由Java虚拟机以及通过调用类加载器中的defineClass
 * 方法自动构造的。一个类只产生一个Class对象
 * 4、jvm创建对象前，会先检查类是否加载，寻找类对应的class对象，
 * 若加载好，则为你的对象分配内存，初始化也就是代码:new Object()。
 * <p>
 * <p>
 * 反射的本质：获取类的class文件对象后，反向获取对象中的属性信息
 * 反射的核心：JVM在运行时才动态加载类或调用方法/访问属性，它不需要事先（写代码的时候或编译期）知道运行对象是谁。
 * <p>
 * 通过反射创建对象：1、通过Class对象的newInstance创建对应类的实例  2、通过Class对象获取指定的Constructor对像创建
 * 通过反射运行配置文件的内容
 * 通过反射越过泛型检查,泛型在编译时有效，在运行时会跳过检查
 * <p>
 * 动态代理，通过反射生成一个代理对象
 * <p>
 * Created by liuk on 2018/3/6 0006.
 */

public class TestAnnotation {

    private static final String TAG = TestAnnotation.class.getSimpleName();

    public static void test() throws Exception {

        User user = new User();

        // 1、获取类的字节码文件对象
        // User.class;
        // user.getClass();
        // Class.forName("com.aerozhonghuan.jenkins.annotation.User"); // 优先考虑
        // ClassLoader.getSystemClassLoader().loadClass("com.aerozhonghuan.jenkins.annotation.User");
        Class clazz = Class.forName("com.aerozhonghuan.jenkins.annotation.User");

        // 2、获取当前字段上的注解类信息
        Field declaredFieldAge = clazz.getDeclaredField("age");
        UserInject userInject = declaredFieldAge.getAnnotation(UserInject.class);

        // 3、获取自定义注解类上的参数
        int age = userInject.age();
        String name = userInject.name();

        // 4、通过反射机制赋值
//        clazz.getField()    // 只能获取public权限
//        clazz.getMethods(); // 所有公用（public）方法包括其继承类的公用方法
//        clazz.getDeclaredMethods(); // 返回类或接口声明的所有方法，不包括继承的方法。
        Field declaredFieldName = clazz.getDeclaredField("name");  // 所有已声明的成员变量。但不能得到其父类的成员变量


        // 暴力破解
        declaredFieldAge.setAccessible(true);
        declaredFieldName.setAccessible(true);

        // 向变量赋值
        declaredFieldAge.set(user, age);
        declaredFieldName.set(user, name);

        Method eat = clazz.getDeclaredMethod("eat", String.class);
        eat.setAccessible(true);
        Object result = eat.invoke(user, "米饭");

        // 打印
        System.out.println("user:" + user);
        System.out.println("result:" + result);
    }

    // 代理对象
    public static void testProxy() {
        AdminDao adminDao1 = new AdminDaoImpl();
        adminDao1.register();
        adminDao1.login();
        System.out.println("-------使用代理对象-----");
        // 添加代理对象
        MyInvocationHandler my = new MyInvocationHandler(adminDao1);
        AdminDao adminDao2 = (AdminDao) Proxy.newProxyInstance(adminDao1.getClass().getClassLoader(),
                adminDao1.getClass().getInterfaces(), my);
        adminDao2.register();
        adminDao2.login();
    }
}
