package com.folkcat.demo.eventpatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/10/24.
 */

public class EventPatchActivity extends Activity {
    private static final String TAG="EventPatchActivity";
    private LinearLayout mViewGroup;
    private View mTopLevelView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_patch);

        mViewGroup=(LinearLayout)findViewById(R.id.ll_view_group);
        mTopLevelView=findViewById(R.id.v_top_level);

        mViewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG,"11111ViewGroup's onTouch");
                return true;
            }
        });
        mTopLevelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG,"22222TopLevelView's onTouch");
                return false;//此处返回true时，说明这个view处理这一事件串，将不再给父view处理。
                            //若此处返回false，说明这个view不处理ondown事件串，后续的事件串将不再交给他处理，
            }
        });

    }
}
