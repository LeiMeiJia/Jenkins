package com.aerozhonghuan.java.reflect;

import java.lang.reflect.Constructor;
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

public class ReflectTest {

    public static void reflect() throws Exception {
        //      LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + Integer.toHexString(hashCode()));
        AdminDaoImpl adminDao = new AdminDaoImpl();
        // 1、4 种方式获取类的字节码文件对象
        // AdminDao.class;
        // adminDao.getClass();
        // Class.forName("com.aerozhonghuan.java.reflect.AdminDaoImpl"); // 优先考虑
        // ClassLoader.getSystemClassLoader().loadClass("com.aerozhonghuan.java.reflect.AdminDaoImpl");
        Class clazz = Class.forName("com.aerozhonghuan.java.reflect.AdminDaoImpl");


        // 2、获取成员变量
        System.out.println("====获取公共成员变量，包括父类=====");
        Field[] fields = clazz.getFields(); // 获取所有public权限的变量，包括其父类的公用变量
        for (Field field : fields) {
            System.out.println("field:" + field.getName());
        }
        System.out.println("====获取所有成员变量，不包括父类=====");
        Field[] declaredFields = clazz.getDeclaredFields(); // 获取所有已声明的成员变量。但不能得到其父类的成员变量
        for (Field declareField : declaredFields) {
            System.out.println("declareField:" + declareField.getName());
        }


        // 3、获取成员方法
        System.out.println("====获取公共成员方法，包括父类=====");
        Method[] methods = clazz.getMethods(); // 获取所有公用（public）方法包括其父类的公用方法
        for (Method method : methods) {
            System.out.println("method:" + method.getName());
        }
        System.out.println("====获取所有成员方法，不包括父类======");
        Method[] declaredMethods = clazz.getDeclaredMethods(); // 获取返回类或接口声明的所有方法，不包括继承的方法。
        for (Method declaredMethod : declaredMethods) {
            System.out.println("declaredMethod:" + declaredMethod.getName());
        }

        // 4、通过反射机制赋值
        System.out.println("====通过反射机制赋值======");
        Field name = clazz.getDeclaredField("name");
        // 暴力破解
        name.setAccessible(true);
        name.set(adminDao, "测试");
        System.out.println("adminDao:" + adminDao);

        System.out.println("====通过反射调用私有方法======");
        Method setName = clazz.getDeclaredMethod("setName", String.class);
        setName.setAccessible(true);
        setName.invoke(adminDao, "测试");

        // 5、通过反射机制创建对象
        System.out.println("====通过反射创建对象======");
        Constructor constructor = clazz.getConstructor();
        Object ad1 = constructor.newInstance();
        Object ad2 = clazz.newInstance();
        System.out.println("ad1:" + ad1);
        System.out.println("ad2:" + ad2);
    }

    // InvocationHandler代理对象
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
