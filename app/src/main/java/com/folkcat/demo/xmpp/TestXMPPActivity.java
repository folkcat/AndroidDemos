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
    private XMPPConnection mXMPPConnection;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_xmpp);
        new Thread(new Runnable() {
            @Override
            public void run() {
                conServer();
            }
        }).start();

    }


    public boolean conServer() {
        Log.i(TAG,"conServer started");
        ConnectionConfiguration config = new ConnectionConfiguration(
                "192.168.1.102", 5222);
        /** 是否启用安全验证 */
        config.setSASLAuthenticationEnabled(false);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

        /** 是否启用调试 */
        // config.setDebuggerEnabled(true);
        /** 创建connection链接 */
        try {
            mXMPPConnection = new XMPPConnection(config);
            /** 建立连接 */
            mXMPPConnection.connect();
            return true;
        } catch (XMPPException e) {
            Log.i(TAG,"exception happend");
            e.printStackTrace();
        }
        return false;
    }



}
