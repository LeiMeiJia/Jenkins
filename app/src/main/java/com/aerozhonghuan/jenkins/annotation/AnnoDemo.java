package com.aerozhonghuan.jenkins.annotation;


/**
 * 已经使用了注解
 * @author Administrator
 */
@Anno1(a=1,s="haha",c=AnnoDemo.class,a2=@Anno2(b=10),c2=Color.RED,arrs={"abc","345"})
@Anno3
@Anno4(value="hehe",d=10)
public class AnnoDemo {
	
}

/**
 * 自定义注解
 * 定义属性：类型 变量名称 ();
 * 如果注解中的属性要没有默认值，使用注解的时候，必须属性进行赋值
 */
@interface Anno1{
	int a();
	String s();
	Class c();
	// 注解类型的
	Anno2 a2();
	// 定义枚举类型
	Color c2();
	// 以上数据类型的一维数组
	String [] arrs();
}

// 注解也可以包含属性
@interface Anno2{
	int b();
}

// 枚举类型
enum Color{
	RED,GREEN;
}


// 新的注解
@interface Anno3{
	int c() default 5;
}

// 如果是属性名称是value，那么value=  可以省略不写了
@interface Anno4{
	// 属性名称是value,没有指定默认值
	String value();
	int d();
}














