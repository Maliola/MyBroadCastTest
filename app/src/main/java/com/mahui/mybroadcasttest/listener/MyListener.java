package com.mahui.mybroadcasttest.listener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2016/10/26.
 */
public class MyListener {
    public static String ACTION="myaction";
    Activity activity;
    StateChangeListener stateChangeListener;
    MyReceiver myReceiver;

    public MyListener(Activity activity,StateChangeListener stateChangeListener) {
        this.activity=activity;
        this.stateChangeListener=stateChangeListener;
    }
    public void onStart(){
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION);
        if(null!=myReceiver&&null!=activity){
            activity.registerReceiver(myReceiver,intentFilter);
        }
    }
    public void onDestory(){
        if(null!=myReceiver&&null!=activity){
            activity.unregisterReceiver(myReceiver);
        }
        myReceiver=null;
    }
    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(MyListener.ACTION)){
                int state=intent.getIntExtra("state",0);
                if(state==0){
                    if(null!=stateChangeListener){
                        stateChangeListener.myplay();
                    }
                }else if(state==1){
                    if(null!=stateChangeListener){
                        stateChangeListener.mypause();
                    }
                }else if(state==2){
                    if(null!=stateChangeListener){
                        stateChangeListener.mystop();
                    }
                }
            }
        }
    }
    public interface StateChangeListener{
        void myplay();
        void mypause();
        void mystop();
    }
}
