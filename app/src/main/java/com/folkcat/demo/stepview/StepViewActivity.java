package com.folkcat.demo.stepview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.folkcat.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamas on 2016/8/5.
 */
public class StepViewActivity extends Activity{
    RecyclerView mRvStep;
    List<Step> mStepList;
    StepRecyclerViewAdapter mStepRvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);
        mRvStep=(RecyclerView)findViewById(R.id.rv_stepview);

        mStepList=new ArrayList<Step>();
        for(int i=0;i<15;i++){
            Step step = new Step();
            step.setLeftString(i+" Left");
            step.setRightString(i+" Right");
            mStepList.add(step);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mStepRvAdapter=new StepRecyclerViewAdapter(mStepList);


        mRvStep.setLayoutManager(layoutManager);//这一步必不可少
        mRvStep.setAdapter(mStepRvAdapter);


    }

}
