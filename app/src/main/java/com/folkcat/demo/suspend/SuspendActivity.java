package com.folkcat.demo.suspend;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/9/8.
 */
public class SuspendActivity extends Activity {
    private static final String TAG="SuspendActivity";
    private Button mBtnOpenSuspend;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);
        mBtnOpenSuspend=(Button)findViewById(R.id.btn_open_suspend);

        mBtnOpenSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.showPopupWindow(SuspendActivity.this);
            }
        });

    }
}
