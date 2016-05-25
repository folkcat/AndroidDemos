package com.folkcat.demo.location;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.folkcat.demo.R;

import java.util.List;

/**
 * Created by Tamas on 2016/5/25.
 */
public class LocationActivity extends Activity implements LocationListener {

    private TextView mTvLat;
    private TextView mTvLng;
    private TextView mTvProvider;
    private TextView mTvAccuracy;
    private TextView mTvEnabledProviders;

    private LocationManager mLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mTvLat=(TextView)findViewById(R.id.tv_lat);
        mTvLng=(TextView)findViewById(R.id.tv_lng);

        mTvProvider=(TextView)findViewById(R.id.tv_loc_provider);
        mTvAccuracy=(TextView)findViewById(R.id.tv_loc_accuracy);

        mTvEnabledProviders=(TextView)findViewById(R.id.tv_enabled_providers);

        mLocationManager=(LocationManager)getSystemService(LOCATION_SERVICE);


    }
    public void onLocationChanged(Location location){
        mTvLat.setText(String.valueOf(location.getLatitude()));
        mTvLng.setText(String.valueOf(location.getLongitude()));

        mTvAccuracy.setText(String.valueOf(location.getAccuracy()));
        mTvProvider.setText(String.valueOf(location.getProvider()));

    }
    public void onProviderDisabled(String provider){

    }
    public void onProviderEnabled(String provider){

    }
    public void onStatusChanged(String provider,int status,Bundle extras){

    }
    protected void onResume(){
        super.onResume();
        StringBuffer stringBuffer=new StringBuffer();

        Criteria criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度
        List<String> enabledProviders;
        enabledProviders=mLocationManager.getProviders(criteria,true);
        if(enabledProviders.isEmpty()){
            mTvEnabledProviders.setText("空空如也");
        }else {
            for(String enabledProvider:enabledProviders){
                stringBuffer.append(enabledProvider).append("   ");
                mLocationManager.requestSingleUpdate(enabledProvider,this,null);
            }
            mTvEnabledProviders.setText(stringBuffer);
        }


    }
}
