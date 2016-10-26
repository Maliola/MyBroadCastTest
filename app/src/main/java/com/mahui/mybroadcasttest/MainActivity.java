package com.mahui.mybroadcasttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mahui.mybroadcasttest.listener.MyListener;
import com.mahui.mybroadcasttest.listener.NetListener;
import com.mahui.mybroadcasttest.service.MyService;

public class MainActivity extends AppCompatActivity implements MyListener.StateChangeListener, NetListener.OnNetChangeListener {
    MyListener myListener;
    NetListener netListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListener=new MyListener(this,this);
        netListener=new NetListener(this,this);
        Intent intent=new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myListener.onStart();
        netListener.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myListener.onDestory();
        netListener.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myListener.onDestory();
    }

    @Override
    public void myplay() {
        Toast.makeText(this,"broadcast-----------------------myplay",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mypause() {
        Toast.makeText(this,"broadcast-----------------------mypause",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mystop() {
        Toast.makeText(this,"broadcast-----------------------mystop",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobileConnected() {
        Toast.makeText(this,"现在使用的是非WIFI",Toast.LENGTH_SHORT).show();
    }
}
