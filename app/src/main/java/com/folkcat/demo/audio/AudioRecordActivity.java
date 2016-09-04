package com.folkcat.demo.audio;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.folkcat.demo.R;

public class AudioRecordActivity extends Activity implements View.OnClickListener{
    private final static int FLAG_WAV = 0;
    private final static int FLAG_AMR = 1;
    private int mState = -1;    //-1:没再录制，0：录制wav，1：录制amr


    private Button mBtnRecordWav;
    private Button mBtnRecordAmr;
    private Button mBtnRecordStop;
    private TextView mTvText;
    private UIHandler uiHandler;
    private UIThread uiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);
        findViewByIds();
        setListeners();
        init();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_record_amr:
                record(FLAG_AMR);
                break;
            case R.id.btn_record_stop:
                stop();
                break;
            case R.id.btn_record_wav:
                record(FLAG_WAV);
                break;
            default:
                break;
        }

    }

    private void findViewByIds(){
        mBtnRecordWav = (Button)this.findViewById(R.id.btn_record_wav);
        mBtnRecordAmr = (Button)this.findViewById(R.id.btn_record_amr);
        mBtnRecordStop = (Button)this.findViewById(R.id.btn_record_stop);
        mTvText = (TextView)this.findViewById(R.id.tv_text);
    }
    private void setListeners(){
        mBtnRecordWav.setOnClickListener(this);
        mBtnRecordAmr.setOnClickListener(this);
        mBtnRecordStop.setOnClickListener(this);
    }
    private void init(){
        uiHandler = new UIHandler();
    }
    /**
     * 开始录音
     * @param flag，0：录制wav格式，1：录音amr格式
     */
    private void record(int flag){
        if(mState != -1){
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd",CMD_RECORDFAIL);
            b.putInt("msg", ErrorCode.E_STATE_RECODING);
            msg.setData(b);
            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            return;
        }
        int result = -1;
        switch(flag){
            case FLAG_WAV:
                AudioRecordFunc record_1 = AudioRecordFunc.getInstance();
                result = record_1.startRecordAndFile();
                break;
            case FLAG_AMR:
                MediaRecordFunc record_2 = MediaRecordFunc.getInstance();
                result = record_2.startRecordAndFile();
                break;
        }
        if(result == ErrorCode.SUCCESS){
            uiThread = new UIThread();
            new Thread(uiThread).start();
            mState = flag;
        }else{
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd",CMD_RECORDFAIL);
            b.putInt("msg", result);
            msg.setData(b);

            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
        }
    }
    /**
     * 停止录音
     */
    private void stop(){
        if(mState != -1){
            switch(mState){
                case FLAG_WAV:
                    AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                    mRecord_1.stopRecordAndFile();
                    break;
                case FLAG_AMR:
                    MediaRecordFunc mRecord_2 = MediaRecordFunc.getInstance();
                    mRecord_2.stopRecordAndFile();
                    break;
            }
            if(uiThread != null){
                uiThread.stopThread();
            }
            if(uiHandler != null)
                uiHandler.removeCallbacks(uiThread);
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd",CMD_STOP);
            b.putInt("msg", mState);
            msg.setData(b);
            uiHandler.sendMessageDelayed(msg,1000); // 向Handler发送消息,更新UI
            mState = -1;
        }
    }
    private final static int CMD_RECORDING_TIME = 2000;
    private final static int CMD_RECORDFAIL = 2001;
    private final static int CMD_STOP = 2002;
    class UIHandler extends Handler{
        public UIHandler() {
        }
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int vCmd = b.getInt("cmd");
            switch(vCmd){
                case CMD_RECORDING_TIME:
                    int vTime = b.getInt("msg");
                    AudioRecordActivity.this.mTvText.setText("正在录音中，已录制："+vTime+" s");
                    break;
                case CMD_RECORDFAIL:
                    int vErrorCode = b.getInt("msg");
                    String vMsg = ErrorCode.getErrorInfo(AudioRecordActivity.this, vErrorCode);
                    AudioRecordActivity.this.mTvText.setText("录音失败："+vMsg);
                    break;
                case CMD_STOP:
                    int vFileType = b.getInt("msg");
                    switch(vFileType){
                        case FLAG_WAV:
                            AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                            long mSize = mRecord_1.getRecordFileSize();
                            AudioRecordActivity.this.mTvText.setText("录音已停止.录音文件:"+AudioFileFunc.getWavFilePath()+"\n文件大小："+mSize);
                            break;
                        case FLAG_AMR:
                            MediaRecordFunc mRecord_2 = MediaRecordFunc.getInstance();
                            mSize = mRecord_2.getRecordFileSize();
                            AudioRecordActivity.this.mTvText.setText("录音已停止.录音文件:"+AudioFileFunc.getAMRFilePath()+"\n文件大小："+mSize);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    class UIThread implements Runnable {
        int mTimeMill = 0;
        boolean vRun = true;
        public void stopThread(){
            vRun = false;
        }
        public void run() {
            while(vRun){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mTimeMill ++;
                Log.d("thread", "mThread........"+mTimeMill);
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putInt("cmd",CMD_RECORDING_TIME);
                b.putInt("msg", mTimeMill);
                msg.setData(b);

                AudioRecordActivity.this.uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            }

        }
    }

}