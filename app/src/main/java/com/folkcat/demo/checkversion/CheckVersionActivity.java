package com.folkcat.demo.checkversion;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.folkcat.demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tamas on 2016/9/27.
 */
/* mCheckVersionUrl内容
{
	"VersionCode":"10",
	"VersionName":"2.1.3",
	"NotSupportBefore":"5",
	"NewFeather":"解决了一些BUG",
	"Url":"http://blogstatic.folkcat.com/app_21days_latest.apk"
}
 */

public class CheckVersionActivity extends AppCompatActivity {
    private static final int REQUEST_WRITE_EXTRAL_STORAGE=1;
    private static final String TAG="CheckVersionActivity";
    private String mCheckVersionUrl="http://7qnat8.com1.z0.glb.clouddn.com/check_version3.json";
    private Button mBtnCheckVersion;
    OkHttpClient mClient = new OkHttpClient();//不知道为什么OKHttpClient对象必须为成员变量，如果是局部变量会发生SocketTimeoutException


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_version);
        mBtnCheckVersion=(Button)findViewById(R.id.btn_check_version);
        mBtnCheckVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckVersionObservable.subscribe(mCheckVersionSubscriber);

            }
        });
    }
    Observable mCheckVersionObservable = Observable.create(new Observable.OnSubscribe<CheckVersion>() {
        @Override
        public void call(Subscriber subscriber) {
            Log.i(TAG,"call() is called, in thread:"+Thread.currentThread().toString());
            Request request = new Request.Builder().url(mCheckVersionUrl).build();
            Response response = null;
            try {
                response = mClient.newCall(request).execute();
                if (!response.isSuccessful()) return;

                PackageManager pm = getPackageManager();
                PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
                int localVersioncode = pi.versionCode;

                Log.i(TAG,"HAHAHAHA Fuck");
                String content=response.body().string();
                int length=content.length();
                Log.i(TAG,"Contentttt"+content);
                JSONObject jsonObj=new JSONObject(content);
                CheckVersion checkVersion=new CheckVersion();
                checkVersion.setLocalVersionCode(localVersioncode);
                checkVersion.setNewFeather(jsonObj.getString("NewFeather"));
                checkVersion.setNotSupportBefore(jsonObj.getInt("NotSupportBefore"));
                checkVersion.setServerVersionCode(jsonObj.getInt("VersionCode"));
                checkVersion.setUrl(jsonObj.getString("Url"));
                checkVersion.setVersionName(jsonObj.getString("VersionName"));
                subscriber.onNext(checkVersion);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            } catch(PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }

        }

    }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    Subscriber mCheckVersionSubscriber = new Subscriber<CheckVersion>() {
        @Override
        public void onCompleted() {
            Log.i(TAG,"Complete! ");
        }
        @Override
        public void onError(Throwable e) {
            Log.i(TAG,"onError");
        }
        @Override
        public void onNext(CheckVersion checkVersion) {
            Log.i(TAG,"onNext called " );
            Log.i(TAG,"serverVersionCode:"+checkVersion.getServerVersionCode());
            Log.i(TAG,"localVersionCode:"+checkVersion.getLocalVersionCode());
            Log.i(TAG,"notSupportBefore:"+checkVersion.getNotSupportBefore());
            Log.i(TAG,"versionName:"+checkVersion.getVersionName());
            Log.i(TAG,"newFeather:"+checkVersion.getNewFeather());
            Log.i(TAG,"url:"+checkVersion.getUrl());

            if(checkVersion.getNotSupportBefore()>checkVersion.getLocalVersionCode()-1){
                //TODO 强制用户升级，否则应用版本应为太旧不可使用
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckVersionActivity.this);
                builder.setMessage("您的版本太旧，我们已不再提供支持，请升级到最新版本");
                builder.setTitle("警告");
                builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }else if(checkVersion.getServerVersionCode()>checkVersion.getLocalVersionCode()){
                //TODO 提示用户升级、忽略此版本
                //SharePreference中应该存储用户最近忽略的版本
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckVersionActivity.this);
                builder.setMessage(checkVersion.getNewFeather());
                builder.setTitle("版本升级");
                builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tryWriteExtralStoragePermission();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("忽略此版本",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        }
    };
    public void tryWriteExtralStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTRAL_STORAGE);
        } else {
            startDownLoadService();
        }
    }
    void startDownLoadService(){
        Intent toStartDownloadService=new Intent(CheckVersionActivity.this,DownloadService.class);
        startService(toStartDownloadService);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTRAL_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownLoadService();
            } else {
                // Permission Denied
                Toast.makeText(getApplicationContext(), "被拒绝了", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
