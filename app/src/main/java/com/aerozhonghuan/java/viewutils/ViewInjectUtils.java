package com.aerozhonghuan.java.viewutils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liuk on 2018/3/11 0011.
 */

public class ViewInjectUtils {

    public static void init(Activity activity) {
        bindView(activity);
        bindOnClick(activity);
    }

    private static void bindView(Activity activity) {
        // 1、获取Activity字节码对象
        Class clazz = activity.getClass();
        // 2、获取私有变量
        Field[] declaredFields = clazz.getDeclaredFields();
        // 3、遍历所有变量
        for (Field field : declaredFields) {
            // 4、判断哪个变量存在注解
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            if (annotation != null) {
                // 5、获取自定义注解类上的参数
                int resId = annotation.value();
                // 6、赋值给变量
                field.setAccessible(true);
                View view = activity.findViewById(resId);
                try {
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void bindOnClick(final Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int resId = onClick.value();
                final View view = activity.findViewById(resId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);
                        try {
                            method.invoke(activity, view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

}
