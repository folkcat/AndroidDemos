package com.folkcat.demo;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final String TAG="MainActivity";
    private TextView mTvLoad;
    private Handler mHandler;


    //myObservable.subscribe(onNextAction);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvLoad=(TextView)findViewById(R.id.tv_load);
        mHandler=new Handler();
    }
    @Override
    public void onStart(){
        super.onStart();



    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG,"onResume Called");
        //耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvLoad.setText("加载完成");
                    }
                });

            }
        }).start();

    }


}
