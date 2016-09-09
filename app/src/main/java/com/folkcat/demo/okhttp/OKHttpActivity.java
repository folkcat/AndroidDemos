package com.folkcat.demo.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.folkcat.demo.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tamas on 2016/9/9.
 */
public class OKHttpActivity extends Activity implements View.OnClickListener {

    private Button mBtnOkGet;
    private Button mBtnOkPost;
    private TextView mTvResult;
    private Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        mHandler=new Handler();
        initLayout();

    }
    private void initLayout(){
        mBtnOkGet=(Button)findViewById(R.id.btn_ok_get);
        mBtnOkPost=(Button)findViewById(R.id.btn_ok_post);
        mTvResult=(TextView)findViewById(R.id.tv_ok_content);
        mBtnOkPost.setOnClickListener(this);
        mBtnOkGet.setOnClickListener(this);
    }


    String mUrl="http://blog.csdn.net/mynameishuangshuai/article/details/51303446";
    OkHttpClient mClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //Get
    private String testOkHttp() throws IOException{
        Request request = new Request.Builder().url(mUrl).build();
        Response response = mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    //Post Json
    String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    String postKV(String url, String json) throws IOException {
        FormBody formBody =new FormBody.Builder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "XXXXXXXXXXXXXXX")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_ok_get:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            final String result=testOkHttp();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mTvResult.setText(result);
                                }
                            });
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();


                break;
            case R.id.btn_ok_post:
                break;
            default:
                break;
        }
    }
}
