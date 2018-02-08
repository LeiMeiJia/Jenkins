package com.aerozhonghuan.jenkins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 当 Thread 因未捕获的异常而突然终止时，调用处理程序。
 * <p>
 * ,方法主要作用为设置当线程由于未捕获到异常而突然终止
 * Created by Administrator on 2018/2/8.
 */

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {

    private File file;

    public DefaultExceptionHandler(File file) {
        this.file = file;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 打印异常
        ex.printStackTrace();
        // 将异常存入文件中
        PrintWriter err = null;
        try {
            String dbPath = file.getPath() + "/err.log";
            FileOutputStream ps = new FileOutputStream(dbPath, true);
            err = new PrintWriter(ps, true);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            String time = sdfDate.format(new Date(System.currentTimeMillis()));
            err.write("\n" + time + "\n");
            ex.printStackTrace(err);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (err != null) {
                err.close();
            }
        }
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
