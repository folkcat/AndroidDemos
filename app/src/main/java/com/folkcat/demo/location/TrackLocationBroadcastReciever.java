package com.folkcat.demo.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by Tamas on 2016/5/25.
 */
public class TrackLocationBroadcastReciever extends BroadcastReceiver {
    private static final String TAG="TrackLocationBroadcastReciever";
    @Override
    public void onReceive(Context ctx,Intent intent){
        if(intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)){
            //Log.i(TAG, "KEY_LOCATION_CHANGED"+intent.getExtras(LocationManager.KEY_LOCATION_CHANGED));

        }else if(intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)){

        }else if(intent.hasExtra(LocationManager.KEY_PROXIMITY_ENTERING)){

        }else if(intent.hasExtra(LocationManager.KEY_STATUS_CHANGED)){

        }

    }
}
