package com.folkcat.demo.butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.folkcat.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tamas on 2016/10/24.
 */

public class ButterKnifeActivity extends Activity {
    @BindView(R.id.tv_content)
    TextView mTvContent;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);

        mTvContent.setText("我是从ButterKnife绑定的");
    }

}
