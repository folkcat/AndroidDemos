package com.folkcat.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by Tamas on 2016/4/22.
 */
public class MyBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context _ctx,Intent _intent){
        //onReceive函数不能做耗时的事情，参考值：10s以内
        Log.i("scott", "on receive action=" + _intent.getAction());
        String action= _intent.getAction();
        //do some works
    }

}