package com.folkcat.demo.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/4/22.
 */
public class TestBroadcastActivity extends Activity {

    private  Button mBtnSendBroadcast;
    private Button mBtnRegBroadcast;
    private BroadcastReceiver mMyBroadcastReceiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_broadcast);
        mBtnSendBroadcast=(Button)findViewById(R.id.btn_send_broadcast);
        mBtnRegBroadcast=(Button)findViewById(R.id.btn_register_broadcast);

        mMyBroadcastReceiver=new MyBroadcastReciver();


        mBtnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction("com.folkcat.LAUNCH");
                sendBroadcast(intent);
            }
        });
        mBtnRegBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyBroadcastReciver reciver=new MyBroadcastReciver();
                IntentFilter filter=new IntentFilter();
                filter.addAction("com.folkcat.LAUNCH");
                dynamicRegBroadcast(mMyBroadcastReceiver,filter);
            }
        });

    }

    public Intent dynamicRegBroadcast(BroadcastReceiver _receiver,IntentFilter _filter){
        return registerReceiver(_receiver,_filter);
    }

}
