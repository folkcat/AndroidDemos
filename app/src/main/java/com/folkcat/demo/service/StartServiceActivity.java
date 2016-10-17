package com.folkcat.demo.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.folkcat.demo.R;


/**
 * Created by Tamas on 2016/6/26.
 */
public class StartServiceActivity extends Activity implements View.OnClickListener{
    private static final String TAG="StartServiceActivity";

    private Button mBtnStartService;
    private Button mBtnStopService;
    Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = new Intent(StartServiceActivity.this,TestService.class);//
        setContentView(R.layout.activity_start_service);
        mBtnStartService=(Button)findViewById(R.id.btn_start_service);
        mBtnStopService=(Button)findViewById(R.id.btn_stop_service);
        findViewById(R.id.btn_check_service_status).setOnClickListener(this);
        //绑定Service
        mBtnStartService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_start_service:
                //this.bindService(mIntent, conn, Context.BIND_AUTO_CREATE);
                this.startService(mIntent);
                break;
            case R.id.btn_stop_service:
                stopService(mIntent);

                break;
            case R.id.btn_check_service_status:

                break;
        }
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
