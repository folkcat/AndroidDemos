package com.folkcat.demo.stepview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.folkcat.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamas on 2016/8/5.
 */
public class StepViewActivityType2 extends Activity{
    RecyclerView mRvStep;
    List<Step> mStepList;
    StepRecyclerViewAdapter mStepRvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view_type2);
        LinearLayout stepContainer=(LinearLayout)findViewById(R.id.ll_step_container);
        for(int i=0;i<10;i++){
            StepView stepView=new StepView(getApplicationContext());
            stepView.setLeftText("Step:"+i);
            stepView.setRightText("hahaha");
            stepContainer.addView(stepView);
        }


    }

}
