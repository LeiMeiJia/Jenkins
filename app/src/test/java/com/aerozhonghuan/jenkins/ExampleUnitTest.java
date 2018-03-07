package com.aerozhonghuan.jenkins;

import com.aerozhonghuan.jenkins.annotation.AdminDao;
import com.aerozhonghuan.jenkins.annotation.AdminDaoImpl;
import com.aerozhonghuan.jenkins.annotation.MyInvocationHandler;
import com.aerozhonghuan.jenkins.annotation.TestAnnotation;
import com.aerozhonghuan.jenkins.annotation.User;
import com.aerozhonghuan.jenkins.annotation.UserInject;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        TestAnnotation.test();
        System.out.println("==========");
        TestAnnotation.testProxy();
    }
}