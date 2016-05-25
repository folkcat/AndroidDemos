package com.folkcat.demo.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.folkcat.demo.R;

/**
 * Created by Tamas on 2016/5/25.
 */
public class LocationActivity2 extends Activity {
    private Button mBtnUpdateLocation;
    private PendingIntent mPendingIntent;

    private LocationManager mLocationManger;

    private  final static String TAG="LocationActivity2";

    @Override
    public void onCreate(Bundle savedInstanceStates){
        super.onCreate(savedInstanceStates);
        mBtnUpdateLocation=(Button)findViewById(R.id.btn_update_locaion);

        mLocationManger=(LocationManager)getSystemService(LOCATION_SERVICE);

        Intent intent=new Intent("com.folkcat.UPDATE_LOCATION");
        mPendingIntent=PendingIntent.getBroadcast(getApplicationContext(),2,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        mBtnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Criteria criteria=new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                for(String provider:mLocationManger.getProviders(criteria,true)){
                    Log.i(TAG, "Enabling provider" + provider);
                    try{
                        mLocationManger.requestLocationUpdates(provider,0,0,mPendingIntent);
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}
