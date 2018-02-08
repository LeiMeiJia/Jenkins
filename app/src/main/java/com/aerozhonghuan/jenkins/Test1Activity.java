package com.aerozhonghuan.jenkins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Test1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        Log.d("Test", "test1:" + this);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test1Activity.this, Test2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Test", "test1:onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Test", "test1:onResume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Test", "test1:onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Test", "test1:onNewIntent");
    }
}
