package com.aerozhonghuan.jenkins;

import com.aerozhonghuan.java.annotation.AnnotationTest;
import com.aerozhonghuan.java.collections.HashSetDemo;
import com.aerozhonghuan.java.reference.EqualsDemo;
import com.aerozhonghuan.java.reference.ReferenceDemo;
import com.aerozhonghuan.java.reflect.ReflectTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        ReflectTest.testInner();
//        ReferenceDemo.softReference();
//        ReferenceDemo.weakReference();
//        ReferenceDemo.testWeakHashMap();
//        EqualsDemo.equalsHashSet();
//        EqualsDemo.equalsHashMap();
        HashSetDemo demo = new HashSetDemo();
        demo.testHashSet();
        int index=0;
        String[] strs = new String[]{"0","1","2"};
        System.out.println("=============" + strs[index++]);
        System.out.println("======index====" + index);
        System.out.println("=============" + strs[index++]);
        System.out.println("======index=====" + index);
    }

    @Test
    public void test() throws Exception {
        ReflectTest.reflect();
        System.out.println("=============");
        AnnotationTest.annotation();
    }

}