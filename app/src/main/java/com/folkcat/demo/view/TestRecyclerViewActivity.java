package com.folkcat.demo.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.folkcat.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamas on 2016/4/23.
 */
public class TestRecyclerViewActivity extends Activity {
    private RecyclerView mRv;
    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRv=(RecyclerView)findViewById(R.id.rv_test);
        //mRv.setHasFixedSize(true);
        List<String> strList=new ArrayList<String>();
        for(int i=0;i<80;i++){
            strList.add("Item No."+i);
        }
        MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(strList);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mRv.setLayoutManager(layoutManager);//这一步必不可少

        mRv.setAdapter(adapter);
    }
}
