package wyz.android.com.metroofdc.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.listener.LoadLocationListener;

/**
 * Created by wangyuzhe on 16/6/17.
 */
public class LocationFinder  implements LocationListener{

    private Context mContext;
    private LoadLocationListener mLoadLocationListener;
    private LocationManager mLocationManager;
    private LocationManager mLocationManager1;
    private boolean mGps = false;
    private boolean mNetWork = false;
    private SpotsDialog mProgressDialog;
    private boolean first;
    private boolean mIsDetecting = false;
    private final int TIMEOUT_IN_MS = 20000;


    public LocationFinder(Context context, boolean temp)
    {
        mContext = context;
        first = temp;
    }


    public void setListner(LoadLocationListener loadLocationListener)
    {
        this.mLoadLocationListener = loadLocationListener;
    }

    public void detectLocation()
    {
        mProgressDialog = new SpotsDialog(mContext, R.style.Diloag);
        if(mIsDetecting == false) {
            mIsDetecting = true;


            if (first) {
                mProgressDialog.show();
            }

            if (mLocationManager == null) {
                mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                mLocationManager1 = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                mGps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                mNetWork = mLocationManager1.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }

//            if (!mGps && !mNetWork) {
//                mProgressDialog.dismiss();
//                return;
//            }

            int permission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                if (mGps) {
                    mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
                    Log.e("a", "gps");
                }
                if (mNetWork) {
                    mLocationManager1.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                    Log.e("a", "network");
                }
                startTimer();
            } else {
                endLoadDetection();
                mLoadLocationListener.getLocationFailure("No Permission");
            }
        }
        else
        {
            Log.e("a", "Detecting");
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if(first) {
            mProgressDialog.dismiss();
        }
        endLoadDetection();
        mLoadLocationListener.getLocation(location);
        //Log.e("c", String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void endLoadDetection() {
        if(mIsDetecting) {
            mIsDetecting = false;
            mProgressDialog.dismiss();
            int permission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
            if(permission == PackageManager.PERMISSION_GRANTED) {
                mLocationManager.removeUpdates(this);
                mLocationManager1.removeUpdates(this);
            }
        }
    }

    public void startTimer()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mIsDetecting)
                {
                    endLoadDetection();
                    fallBackOnLastKnownLocation();
                }
            }
        }, TIMEOUT_IN_MS);
    }

    public void fallBackOnLastKnownLocation() {
        mProgressDialog.dismiss();
       // Log.e("a", "lastLocation");
        Location last = null;

        int permission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if(mGps) {
            if(permission == PackageManager.PERMISSION_GRANTED) {
                last = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if(mNetWork)
        {
            if(permission == PackageManager.PERMISSION_GRANTED) {
                last = mLocationManager1.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        if(last != null)
        {
            mLoadLocationListener.getLocation(last);
        }
        else
        {
            mLoadLocationListener.getLocationFailure("Time Out");
        }
    }

}
