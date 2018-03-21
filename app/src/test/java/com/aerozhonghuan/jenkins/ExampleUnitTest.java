package com.aerozhonghuan.jenkins;

import com.aerozhonghuan.jenkins.java.annotation.AnnotationTest;
import com.aerozhonghuan.jenkins.java.reflect.ReflectTest;
import com.aerozhonghuan.jenkins.java.thread.MyThreadPool;

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
    }

    @Test
    public void test() throws Exception {
        ReflectTest.reflect();
        System.out.println("=============");
        AnnotationTest.annotation();
    }

    @Test
    public void threadTest() throws Exception {
        MyThreadPool instance = MyThreadPool.getInstance();
        for (int i = 0; i < 110; i++) {
            instance.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadName=" + Thread.currentThread().getName() + "进来了");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadName=" + Thread.currentThread().getName() + "出去了");
                }
            });
        }
    }
}