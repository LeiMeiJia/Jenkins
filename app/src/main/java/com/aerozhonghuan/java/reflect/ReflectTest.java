package com.aerozhonghuan.java.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
//        declaredFields = Field.class.getDeclaredFields();
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
//        declaredMethods = Method.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("declaredMethod:" + declaredMethod.getName());
        }

        // 4、通过反射机制赋值
        System.out.println("====通过反射机制赋值======");
        Field name = clazz.getDeclaredField("name");
        // 暴力破解
        name.setAccessible(true);
//        System.out.println("name:" + name.get(adminDao));
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

    public static void test() {
        System.out.println(Integer.TYPE == int.class ? true : false);
    }

    // ============== 1、数组参数类型============
    public static void testFunc1() throws Exception {
        ReflectTest test = new ReflectTest();
        Class clazz = test.getClass();
        Method func = clazz.getDeclaredMethod("func1", String[].class);
        func.setAccessible(true);
        // 注意：可变参数，这个变量其实是数组，它会自动把多个参数组装成一个数组
        func.invoke(test, new String[1]);// 运行正常：null
        func.invoke(test, (Object) new String[]{"a", "b"}); // 运行正常：2
        func.invoke(test, new Object[]{new String[]{"a", "b", "c"}}); // 运行正常：3
//        func.invoke(test, new String[]{"a"}); // 报错：将可变参数拆解后，入参为String-->a，与func1入参String[]类型不符合
//        func.invoke(test, new String[]{"a", "b"}); // 报错：将可变参数拆解后，入参个数为2个，与func1入参个数为1不符合
//        func.invoke(test, new String[2]);// 报错：同上
    }

    public static void testFunc2() throws Exception {
        ReflectTest test = new ReflectTest();
        Class clazz = test.getClass();
        Method func = clazz.getDeclaredMethod("func2", String.class, String[].class);
        func.setAccessible(true);
        String str = new String();
        // 注意：可变参数，这个变量其实是数组，它会自动把多个参数组装成一个数组
        func.invoke(test, str, new String[1]); // 运行正常：1
        func.invoke(test, str, (Object) new String[]{"a", "b"}); // 运行正常：2
//        func.invoke(test, str, new Object[]{new String[]{"a", "b", "c"}}); // 报错：入参为Object[]类型，与func2入参String[]类型不符合
        func.invoke(test, str, new String[]{"a"}); // 运行正常：1
        func.invoke(test, str, new String[]{"a", "b"}); // 运行正常：2
        func.invoke(test, str, new String[2]);// 运行正常：2
    }

    private void func1(String[] args) {
        System.out.println(args == null ? "null" : args.length);
    }

    private void func2(String key, String[] args) {
        System.out.println(args == null ? "null" : args.length);
    }

    private static final Integer INTEGER_VALUE = 100;
    private static final int INT_VALUE = 100;

    // ============== 2、通过反射修改常量值============
    public static void testFinal() throws Exception {
        System.out.println("INTEGER_VALUE:" + INTEGER_VALUE);
        System.out.println("INT_VALUE:" + INT_VALUE);
        ReflectTest test = new ReflectTest();
        Class clazz = test.getClass();
        Field field1 = clazz.getDeclaredField("INTEGER_VALUE");
        Field field2 = clazz.getDeclaredField("INT_VALUE");
        field1.setAccessible(true);
        field2.setAccessible(true);

        // Field 对象有个一个属性叫做 modifiers, 它表示的是属性是否是 public, private, static, final 等修饰的组合。
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);

        System.out.println("modifiersField:" + Modifier.toString(modifiersField.getModifiers()));
        System.out.println("field1:" + Modifier.toString(field1.getModifiers()));
        System.out.println("field2:" + Modifier.toString(field2.getModifiers()));
//        System.out.println("modifiersField:" + field2.getInt(null)); // final获取后后不能设置


        //  用按位取反 ~ 再按位与，~ 操作把 final 从修饰集中剔除掉，其他特性如 private, static 保持不变。
        modifiersField.setInt(field1, field1.getModifiers() & ~Modifier.FINAL);
        modifiersField.setInt(field2, field2.getModifiers() & ~Modifier.FINAL);
        System.out.println("field1:" + Modifier.toString(field1.getModifiers()));
//        System.out.println("modifiersField:" + field2.getInt(null)); // final获取后后不能设置
        field1.set(test, 200);
        // 注意：修改final 基本类型与String类型常量时，在编译时其值已经被替换，所有通过反射修改不起作用
        field2.set(test, 300);
        System.out.println("INTEGER_VALUE:" + INTEGER_VALUE);
        /*
         * 对于基本类型的静态常量，JAVA在编译的时候就会把代码中对此常量中引用的地方替换成相应常量值。
         * 编译时会被优化成下面这样：System.out.println(100);
         */
        System.out.println("INT_VALUE:" + INT_VALUE);

    }

    private static void testInner() throws Exception {
        ReflectTest test = new ReflectTest();
        Class clazz = test.getClass();
        Class[] declaredClasses = clazz.getDeclaredClasses();
        for (Class cls : declaredClasses) {
            int modifiers = cls.getModifiers();
            String name = Modifier.toString(modifiers);
            if (name.contains("static")) {
                Object instance = cls.newInstance();
            }
        }

    }


    private class InnerA {
        public InnerA() {
        }
    }

    private static class InnerB {
        public InnerB() {
        }
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            System.out.println("Method run of Runnable r");
        }
    };

}
