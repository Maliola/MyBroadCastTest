package com.mahui.mybroadcasttest.listener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/10/26.
 */
public class NetListener {
    Activity activity;
    OnNetChangeListener onNetChangeListener;
    NetWorkReceiver netWorkReceiver;

    public NetListener(Activity activity,OnNetChangeListener onNetChangeListener) {
        this.activity=activity;
        this.onNetChangeListener=onNetChangeListener;
    }
    public void onStart(){
        netWorkReceiver=new NetWorkReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if(null!=netWorkReceiver&&null!=activity){
            activity.registerReceiver(netWorkReceiver,intentFilter);
        }
    }
    public void onStop(){
        if(null!=netWorkReceiver&&null!=activity){
            activity.unregisterReceiver(netWorkReceiver);
        }
        netWorkReceiver=null;
    }
    public class NetWorkReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                boolean isConnected=isNetworkConnected(context);
                boolean isWifi=isWifi(context);
                if(isConnected&&!isWifi){
                    if(null!=onNetChangeListener){
                        onNetChangeListener.onMobileConnected();
                    }
                }
            }
        }
        public boolean isNetworkConnected(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
            return false;
        }
        public boolean isWifi(Context mContext) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetInfo != null
                    && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            return false;
        }
    }
    public interface OnNetChangeListener{
        void onMobileConnected();
    }
}
