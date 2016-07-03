package com.folkcat.demo.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.folkcat.demo.R;


/**
 * Created by Tamas on 2016/6/26.
 */
public class StartServiceActivity extends Activity {
    private static final String TAG="StartServiceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);

        //绑定Service
        Intent intent = new Intent(StartServiceActivity.this,TestService.class);//
        this.bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            Log.i(TAG, "onServiceConnected");

        }
    };
}
