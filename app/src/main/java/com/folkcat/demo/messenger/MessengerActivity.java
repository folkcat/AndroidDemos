package com.folkcat.demo.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/8/2.
 * 未完成
 */
public class MessengerActivity extends Activity {
    static final int MSG_SAY_HELLO = 1;

    Messenger mService = null;

    private Button mBtnSayHello;
    boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            //接收onBind()传回来的IBinder，并用它构造Messenger
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            mBound = false;
        }
    };

    //调用此方法时会发送信息给服务端
    public void sayHello() {
        if (!mBound) return;
        //发送一条信息给服务端
        Message msg = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        mBtnSayHello=(Button)findViewById(R.id.btn_sayhello);
        mBtnSayHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //绑定服务端的服务，此处的action是service在Manifests文件里面声明的
        /*
        Intent intent = new Intent();
        intent.setAction("com.folkcat.messenger");
        intent.setPackage("com.folkcat.demo");
        */
        Intent intent =new Intent(MessengerActivity.this,MessengerService.class);
        intent.setPackage("com.folkcat.demo");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
