package com.aerozhonghuan.jenkins;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * Application类是为了那些需要保存全局变量设计的基本类
 * Created by Administrator on 2018/2/2.
 */

public class MyApplication extends Application {

    private File file;

    @Override
    public void onCreate() {
        super.onCreate();
        file = new File(Environment.getExternalStorageDirectory() + "/hyStation");
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        // 设置未捕获异常处理器
        DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler(file);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }
}
