package com.folkcat.demo.jni;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/8/28.
 */
public class JNIActivity extends Activity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        mTextView = (TextView) this.findViewById(R.id.tv_test_jni);

        NdkJniUtils jni = new NdkJniUtils();

        mTextView.setText(jni.getCLanguageString());
    }

}
