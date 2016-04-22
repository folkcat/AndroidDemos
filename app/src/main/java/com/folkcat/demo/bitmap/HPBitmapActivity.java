package com.folkcat.demo.bitmap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/4/23.
 * High performance bitmap load
 */
public class HPBitmapActivity extends Activity {
    private GridView mGvPhoto;
    private PhotoGridAdapter mAdapter;


    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_hpbitmap);
        mGvPhoto=(GridView)findViewById(R.id.gv_photo);
        mAdapter=new PhotoGridAdapter(getApplicationContext());
        mGvPhoto.setAdapter(mAdapter);

    }

}
