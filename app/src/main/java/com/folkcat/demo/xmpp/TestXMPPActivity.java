package com.folkcat.demo.xmpp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.folkcat.demo.R;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created by Tamas on 2016/9/18.
 */
public class TestXMPPActivity extends Activity {
    private static final String TAG="TestXMPPActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_xmpp);
        Log.i(TAG,"开始vnc");
        Log.i(TAG,"结束vnc");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //conServer();
            }
        }).start();

    }






}
