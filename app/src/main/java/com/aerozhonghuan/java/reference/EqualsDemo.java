package com.aerozhonghuan.java.reference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/16.
 */

public class EqualsDemo {

    /**
     * HashSet集合底层默认元素不能重复，当
     */
    public static void equalsHashSet() {
        StudentDemo demo1 = new StudentDemo(1, "test1");
        StudentDemo demo2 = new StudentDemo(1, "test1");
        System.out.println(demo1.equals(demo2));
        HashSet<StudentDemo> hashSet = new HashSet<>();
        hashSet.add(demo1);
        hashSet.add(demo2);
        System.out.println("hashSet:" + hashSet);
    }

    public static void equalsHashMap() {
        StudentDemo demo1 = new StudentDemo(1, "test1");
        StudentDemo demo2 = new StudentDemo(1, "test1");
        System.out.println(demo1.equals(demo2));
        HashMap<StudentDemo, Integer> hashMap = new HashMap<>();
        hashMap.put(demo1, 1);
        hashMap.put(demo2, 2);
        System.out.println("hashMap:" + hashMap);
    }
}
