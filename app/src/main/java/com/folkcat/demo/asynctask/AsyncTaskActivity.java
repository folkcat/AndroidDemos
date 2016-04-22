package com.folkcat.demo.asynctask;

/**
 * Created by Tamas on 2016/4/20.
 */
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.folkcat.demo.R;

public class AsyncTaskActivity extends Activity {

    private static final String TAG = "ASYNC_TASK";

    private Button mBtnExecute;
    private Button mBtnCancel;
    private ProgressBar progressBar;
    private TextView textView;

    private MyTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        mBtnExecute = (Button) findViewById(R.id.execute);
        mBtnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
                mTask = new MyTask();

                //execute必须在UI线程中调用
                mTask.execute("http://blog.csdn.net/liuhe688/article/details/6532519");

                mBtnExecute.setEnabled(false);
                mBtnCancel.setEnabled(true);
            }
        });
        mBtnCancel = (Button) findViewById(R.id.cancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.text_view);

    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        //
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");
            textView.setText("loading...");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground(Params... params) called");
            for (int i=0;i<=10;i++){
                publishProgress(i*10);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "this returned from doInBackground";//返回值是onPostExecute方法的实参
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
            progressBar.setProgress(progresses[0]);
            textView.setText("loading..." + progresses[0] + "%");
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called"+result);
            textView.setText(result);

            mBtnExecute.setEnabled(true);
            mBtnCancel.setEnabled(false);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled() called");
            textView.setText("cancelled");
            progressBar.setProgress(0);
            mBtnExecute.setEnabled(true);
            mBtnCancel.setEnabled(false);
        }
    }
}