package com.folkcat.demo.checkversion;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.io.File;

/**
 * Created by Tamas on 2016/9/27.
 */
public class DownloadService extends Service {
    private String mApkFileName="newest.apk";
    private DownloadManager dm;
    private long enqueue;
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent2) {
                Intent toInstallApkActivity = new Intent(Intent.ACTION_VIEW);
                toInstallApkActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                toInstallApkActivity.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/myApp.apk")),
                        "application/vnd.android.package-archive");
                startActivity(toInstallApkActivity);
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload("http://blogstatic.folkcat.com/app_21days_latest.apk");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void startDownload(String url) {
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mApkFileName);
        enqueue = dm.enqueue(request);
    }

}