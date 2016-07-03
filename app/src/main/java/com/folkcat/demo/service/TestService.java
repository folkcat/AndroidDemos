package com.folkcat.demo.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.folkcat.demo.lockscreen.LockedScreenActivity;


public class TestService extends Service {
    private static final String TAG="TestService";
    private Handler mHandler;

    @Override
    public void onCreate(){
        mHandler=new Handler();
        new Thread(new CheckIsLockedRunnable()).start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Override
    public void onDestroy(){
        Log.i(TAG, "onDestroy called");
    }



    //判断屏幕是否为锁定状态
    private boolean isScreenLocked(){
        KeyguardManager mKeyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 返回一个Binder对象
     */
    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public TestService getService(){
            return TestService.this;
        }
    }

    private class CheckIsLockedRunnable implements Runnable{
        private boolean isShowed=false;
        public void run(){
            while(!isShowed){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isScreenLocked()){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent toLockActivity = new Intent(TestService.this, LockedScreenActivity.class);
                            toLockActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(toLockActivity);
                        }
                    });
                    Log.i(TAG, "锁屏啦");

                }else{
                    Log.i(TAG, "还没锁屏呢");

                }
            }
        }
    }



}