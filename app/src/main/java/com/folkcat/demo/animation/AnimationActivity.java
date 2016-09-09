package com.folkcat.demo.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.folkcat.demo.R;

import org.w3c.dom.Text;

public class AnimationActivity extends AppCompatActivity {
    private static final String TAG="AnimationActivity";
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
        mTvText.postDelayed(new AnimateRunnable1(),2000);

        mBtnStartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvText.layout(mTvText.getLeft(),mTvText.getTop(),mTvText.getRight(),mTvText.getBottom());
                Log.i(TAG,"Left:"+mTvText.getLeft()+"  Right:"+mTvText.getRight()+"  Top:"+mTvText.getTop()+"  Bottom:"+mTvText.getBottom());
                mTvText2.layout(mTvText2.getLeft(),mTvText2.getTop(),mTvText2.getRight(),mTvText2.getBottom());
            }
        });
    }
    private class AnimateRunnable1 implements Runnable{
        public void run(){
            Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_roll);
            Animation animation2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_roll2);
            mTvText.startAnimation(animation);
            mTvText2.startAnimation(animation2);
            mBtnStartAnimation.postDelayed(new AnimateRunnable2(),2000);
        }
    }
    private class AnimateRunnable2 implements Runnable{
        public void run(){
            Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_roll);
            Animation animation2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text_roll2);
            mTvText.startAnimation(animation2);
            mTvText2.startAnimation(animation);
            mBtnStartAnimation.postDelayed(new AnimateRunnable1(),2000);
        }
    }
}
