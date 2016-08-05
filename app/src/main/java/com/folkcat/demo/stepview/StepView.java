package com.folkcat.demo.stepview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.folkcat.demo.R;

import org.w3c.dom.Text;

/**
 * Created by Tamas on 2016/8/5.
 */
public class StepView extends LinearLayout {

    private TextView mTvLeft;
    private TextView mTvRight;

    public StepView(Context context) {
        this(context, null);
    }
    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_step_view, this);
        mTvLeft=(TextView)view.findViewById(R.id.tv_step_left);
        mTvRight=(TextView)view.findViewById(R.id.tv_step_right);
    }
    public void setLeftText(String text){
        mTvLeft.setText(text);
    }
    public void setRightText(String text){
        mTvRight.setText(text);
    }

}
