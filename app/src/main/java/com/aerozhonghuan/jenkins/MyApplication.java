package com.aerozhonghuan.jenkins;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2018/2/2.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        File file = new File(Environment.getExternalStorageDirectory() + "/hyStation");
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }
}
