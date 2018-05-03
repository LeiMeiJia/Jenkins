package com.aerozhonghuan.jenkins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aerozhonghuan.demo.Demo1Activity;
import com.aerozhonghuan.java.thread.ThreadDemo;
import com.aerozhonghuan.java.viewutils.ViewInjectUtils;
import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 * 1、注意：Toast的创建需要依赖Handler，存在handler的话，子线程也可以弹出toast
 * 2、注意：intent传递对象时，是将对象拷贝了一份进行传递
 * 3、注意：quitSafely和quit的区别
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjectUtils.init(this);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Demo1Activity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            LogUtils.logd(TAG, LogUtils.getThreadName() + savedInstanceState.getString("data"));
        }

        ThreadDemo.testTask();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.logd(TAG, LogUtils.getThreadName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.logd(TAG, LogUtils.getThreadName());
        outState.putString("data", "test");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.logd(TAG, LogUtils.getThreadName());
    }

}
