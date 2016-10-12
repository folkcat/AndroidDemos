package com.folkcat.demo.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Tamas on 2016/9/18.
 */
public class XMPPManager {
    private static final String TAG="XMPPManager";

    private XMPPConnection mXMPPConnection;
    public boolean conServer() {
        Log.i(TAG,"conServer started");
        ConnectionConfiguration config = new ConnectionConfiguration(
                "192.168.1.106", 5222);
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
            Log.i(TAG,"连接成功了哦");
            return true;
        } catch (XMPPException e) {
            Log.i(TAG,"exception happend");
            e.printStackTrace();
        }
        return false;
    }
    public static void startVNC() {
        try {
            Log.i(TAG,"flag 1");
            Runtime.getRuntime();
            Object localObject = Runtime.getRuntime().exec("/system/xbin/su");
            DataOutputStream localDataOutputStream = new DataOutputStream(((Process)localObject).getOutputStream());
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(((Process)localObject).getErrorStream()));
            Log.i(TAG,"flag 2");
            localObject = new BufferedReader(new InputStreamReader(((Process)localObject).getInputStream()));
            Log.i("startVnc", "error: " + localBufferedReader.readLine());
            //added by tamas start
            localDataOutputStream.writeBytes("chmod 555 androidvncserver\n");
            localDataOutputStream.flush();
            //added by tamas end
            localDataOutputStream.writeBytes("/system/bin/androidvncserver -k /dev/input/event0 -t /dev/input/event1");
            Log.i(TAG,"flag 3");
            localDataOutputStream.flush();
            localDataOutputStream.close();
            Log.i("startVnc", "error: " + localBufferedReader.readLine() + "    result:" + ((BufferedReader)localObject).readLine());
            Log.i(TAG,"flag 4");
            return;
        }catch (IOException localIOException) {
            Log.e(TAG,"开启VNC出错啦");
            localIOException.printStackTrace();
        }
    }

}
