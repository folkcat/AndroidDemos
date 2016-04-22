package com.folkcat.demo.handlerlopper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.folkcat.demo.R;
import com.folkcat.demo.util.TinyHttpUtil;


/**
 * Created by Tamas on 2016/4/20.
 */
public class HandlerLooperActivity extends Activity {
    private TextView mTvText;
    private static final String TAG="HandlerLooperActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlerlooper);
        mTvText=(TextView)findViewById(R.id.tv_text);
        new Thread("haha"){
            @Override
            public void run(){
                //mTvText.setText("看到我了吗");
                TinyHttpUtil tinyHttpUtil=new TinyHttpUtil("utf-8");
                String a=tinyHttpUtil.sendGet("https://apicn.faceplusplus.com/v2/train/identify?api_key=a6c47cf631bf9f55e4e688ebfeba4eaa&api_secret=XiyIbiYGOLm6yjjDVQzQAjfooyP125NY&group_name=SmartHome",null,false);
                Log.i(TAG, a);
                Looper.prepare();
                Handler handler=new Handler();
                Looper.loop();
                mTvText.setText(a);

            }
        }.start();
    }

}
