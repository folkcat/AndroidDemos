package com.folkcat.demo.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.folkcat.demo.R;

import org.w3c.dom.Text;

public class AnimationActivity extends AppCompatActivity {
    private TextView mTvText;
    private TextView mTvText2;
    private Button mBtnStartAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mBtnStartAnimation=(Button)findViewById(R.id.btn_start_animate);
        mTvText=(TextView)findViewById(R.id.tv_text);
        mTvText2=(TextView)findViewById(R.id.tv_text2);

        mBtnStartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_roll);
                mTvText.startAnimation(animation);
                mTvText2.startAnimation(animation);

            }
        });

    }
}
