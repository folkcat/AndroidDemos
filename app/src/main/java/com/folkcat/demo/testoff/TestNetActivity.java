package com.folkcat.demo.testoff;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.folkcat.demo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tamas on 2016/8/30.
 * //这个Activity测试手机熄屏后是否能访问网络
 */
public class TestNetActivity extends Activity {
    private TinyHttpClient mHttpClient;
    private Handler mHandler;
    private TextView mTvTime;
    private String mDateStr="";
    private static final String TAG="TestNetActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_net);
        mTvTime=(TextView) findViewById(R.id.tv_time);
        mHttpClient=new TinyHttpClient("utf-8");
        mHandler=new Handler();
        // 初始化DBManager
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;;i++){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mDateStr=sdf.format(new Date());
                    Map<String,String> params=new HashMap<String,String>();
                    params.put("datestr",mDateStr);
                    final String a=mHttpClient.sendGet("http://192.168.43.3/Test/s.php",params,false);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTvTime.setText(a);
                        }
                    });
                    Log.i(TAG,"net content:"+a);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
