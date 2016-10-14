package com.folkcat.demo.xmpp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/9/18.
 */
public class TestXMPPActivity extends Activity implements View.OnClickListener{
    private static final String TAG="TestXMPPActivity";

    private EditText mEtUserName;
    private EditText mEtPasswd;
    private XMPPManager mXMPPManager;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_xmpp);
        mEtUserName=(EditText)findViewById(R.id.et_xmpp_username);
        mEtPasswd=(EditText)findViewById(R.id.et_xmpp_passwd);

        findViewById(R.id.btn_xmpp_register).setOnClickListener(this);
        findViewById(R.id.btn_xmpp_login).setOnClickListener(this);
        findViewById(R.id.btn_xmpp_change_passwd).setOnClickListener(this);
        findViewById(R.id.btn_xmpp_logout).setOnClickListener(this);

        Log.i(TAG,"开始vnc");
        Log.i(TAG,"结束vnc");

        mXMPPManager=new XMPPManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mXMPPManager.conServer();
            }
        }).start();

    }


    @Override
    public void onClick(final View v) {
        final String userName=mEtUserName.getText().toString();
        final String passwd=mEtPasswd.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (v.getId()){
                    case R.id.btn_xmpp_register:
                        String registerResult=mXMPPManager.register(userName,passwd);
                        Log.i(TAG,"register result:"+registerResult);
                        break;
                    case R.id.btn_xmpp_login:
                        boolean loginResult=mXMPPManager.login(userName,passwd);
                        Log.i(TAG,"login result:"+loginResult);
                        break;
                    case R.id.btn_xmpp_logout:
                        boolean logoutResult=mXMPPManager.logout();
                        Log.i(TAG,"logout result:"+logoutResult);
                        break;
                    case R.id.btn_xmpp_change_passwd:
                        boolean changePasswdResult=mXMPPManager.changePassword(passwd);
                        Log.i(TAG,"changePasswd result:"+changePasswdResult);
                        break;
                    default:
                        break;

                }
            }
        }).start();

    }
}
