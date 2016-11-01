package com.folkcat.demo.notification;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.folkcat.demo.R;


public class NotificationService extends Service {
    private static final String TAG="NotificationService";
    private Handler mHandler;
    private RemoteViews mContentViewBig, mContentViewSmall;

    @Override
    public void onCreate(){
        Log.i(TAG,"onCreate called");
        mHandler=new Handler();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //showNotification();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showPopWindow();
                    }
                });

                Log.i(TAG,"notification显示了哦");
            }
        }).start();
        */
        new Thread(new CheckIsLockedRunnable()).start();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand called");
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
        public NotificationService getService(){
            return NotificationService.this;
        }
    }
    private void showPopWindow(){
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.BLACK);
        textView.setText("zhang phil @ csdn");
        textView.setTextSize(10);
        textView.setTextColor(Color.RED);

        //类型是TYPE_TOAST，像一个普通的Android Toast一样。这样就不需要申请悬浮窗权限了。
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);

        //初始化后不首先获得窗口焦点。不妨碍设备上其他部件的点击、触摸事件。
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = 300;
        //params.gravity=Gravity.BOTTOM;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "不需要权限的悬浮窗实现", Toast.LENGTH_LONG).show();
            }
        });

        WindowManager windowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        windowManager.addView(textView, params);
    }
    private void showNotification() {
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)  // the status icon
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .setCustomContentView(getSmallContentView())
                .setCustomBigContentView(getBigContentView())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setOngoing(true)
                .build();

        // Send the notification.
        startForeground(10, notification);
    }
    private RemoteViews getSmallContentView() {
        if (mContentViewSmall == null) {
            mContentViewSmall = new RemoteViews(getPackageName(), R.layout.remote_view_music_player_small);
        }
        return mContentViewSmall;
    }

    private RemoteViews getBigContentView() {
        if (mContentViewBig == null) {
            mContentViewBig = new RemoteViews(getPackageName(), R.layout.remote_view_music_player);
        }
        return mContentViewBig;
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
                            Intent toLockActivity = new Intent(NotificationService.this, LockedScreenActivity.class);
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