package com.folkcat.demo.audio;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.folkcat.demo.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tamas on 2016/9/2.
 */
public class RecordActivity extends Activity implements View.OnClickListener {
    private static final String TAG="RecordActivity";
    private Button mBtnRecordWav;
    private Button mBtnRecordAmr;
    private Button mBtnRecordStop;
    private TextView mTvText;

    private int mState = STATE_FREE;//其值约束为下列三种情况
    private static final int STATE_RECORDING_WAV=1;
    private static final int STATE_RECORDING_AMR=2;
    private static final int STATE_FREE=3;

    private static final int FLAG_RECORDING_WAV=101;
    private static final int FLAG_RECORDING_AMR=102;

    private MediaRecorder mMediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);
        initView();
    }

    private void initView(){
        mBtnRecordWav = (Button)this.findViewById(R.id.btn_record_wav);
        mBtnRecordAmr = (Button)this.findViewById(R.id.btn_record_amr);
        mBtnRecordStop = (Button)this.findViewById(R.id.btn_record_stop);
        mTvText = (TextView)this.findViewById(R.id.tv_text);

        mBtnRecordAmr.setOnClickListener(this);
        mBtnRecordStop.setOnClickListener(this);
        mBtnRecordWav.setOnClickListener(this);
        mTvText.setText("这里是RecordActivity啦");
    }

    //
    private void initMediaRecorder(){
        mMediaRecorder=new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String amrFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"aaa.amr";
        Log.e(TAG,"amrFilePath:"+amrFilePath);
        File amrFile=new File(amrFilePath);
        if(amrFile.exists()) amrFile.delete();
        mMediaRecorder.setOutputFile(amrFilePath);

        File f=new File("/mnt/sdcard"+ File.separator+"aaaDir");
        if(!f.exists())f.mkdir();

    }
    //
    private void startRecordAmr(){
        Log.i(TAG,"startRecordAmr called");
        if(mMediaRecorder==null) initMediaRecorder();
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            mTvText.setText("IO异常");
        } catch(Exception e){
            e.printStackTrace();
            mTvText.setText("未知异常");
        }
        mMediaRecorder.start();
        mState=STATE_RECORDING_AMR;

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_record_amr:
                record(FLAG_RECORDING_AMR);
                break;
            case R.id.btn_record_stop:
                //stop();
                closeMediaRecorder();
                break;
            case R.id.btn_record_wav:
                record(FLAG_RECORDING_WAV);
                break;
            default:
                break;
        }

    }

    private void record(int flag){
        if (mState!=STATE_FREE) {
            mTvText.setText("已经在录制，不能打断");
            return;
        }
        if(flag==FLAG_RECORDING_AMR){
            //TODO 记录AMR格式音频
            mTvText.setText("正在记录AMR格式音频");
            startRecordAmr();

            mState=STATE_RECORDING_AMR;
        }else if(flag==FLAG_RECORDING_WAV){
            //TODO 记录WAV格式音频
            mTvText.setText("正在记录WAV格式音频");
            mState=STATE_RECORDING_WAV;
        }

    }
    //停止录音
    private void closeMediaRecorder(){
        if (mMediaRecorder != null) {
            mState=STATE_FREE;
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        mTvText.setText("资源空闲");
    }



}
