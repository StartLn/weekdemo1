package com.example.msi.observerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册Eventbus
        EventBus.getDefault().register(this);
    }

    public void btn1(View view) {
        startActivity(new Intent(this,Main2Activity.class));
    }
}
