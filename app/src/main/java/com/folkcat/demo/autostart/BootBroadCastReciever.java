package com.folkcat.demo.autostart;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.folkcat.demo.service.TestService;

/**
 * Created by Tamas on 2016/9/1.
 */
public class BootBroadCastReciever extends BroadcastReceiver {
    private static final String TAG="BootBroadCastReciever";
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            Log.i(TAG, "onServiceConnected");

        }
    };
    @Override
    public void onReceive(Context context, Intent intent) {

         Intent service=new Intent(context, TestService.class);

        context.bindService(service, conn, Context.BIND_AUTO_CREATE);


        /*
         * 开机启动的Activity*
         * Intent activity=new Intent(context,MyActivity.class);
         * activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );//不加此句会报错。
         * context.startActivity(activity);
         */

        /* 开机启动的应用 */
        /*
        Intent appli = context.getPackageManager().getLaunchIntentForPackage("com.folkcat.demo");
        context.startActivity(appli);
        */

    }
}
