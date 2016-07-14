package wyz.android.com.metroofdc.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.listener.LoadStationListener;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/5/16.
 */
public class StationServices extends Service{

    private LoadStationListener mLoadStationListener;
    private String mStation;
    private Timer mTimer;
    private TimerTask mRefresh;



    public void setOnLoadStationListener(LoadStationListener loadStationListener, String linecode)
    {
        this.mLoadStationListener = loadStationListener;
        setTimer(linecode);
//        try {
//            URL url = new URL("https://api.wmata.com/StationPrediction.svc/json/GetPrediction/"+ linecode +"?&api_key=8502900a4175457cbdc6a7a81acc8319");
//            metroInfoAsynctask.execute(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        metroInfoAsynctask.setmLoadDataListener(new LoadDataListener() {
//            @Override
//            public void getData(String data) {
//                mStation = data;
//                mLoadStationListener.getStation(mStation);
//            }
//
//            @Override
//            public void getDataFail() {
//
//            }
//        });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }


    public class ServiceBinder extends Binder
    {
        public StationServices getService()
        {
            return StationServices.this;
        }
    }

    //Timer
    public void setTimer(final String linecode)
    {
        mTimer = new Timer();
        mRefresh = new TimerTask() {
            @Override
            public void run() {
                launchTask(linecode);
            }
        };

        mTimer.schedule(mRefresh,0, 5000);
    }

    private void launchTask(String linecode) {
        MetroInfoAsynctask metroInfoAsynctask = new MetroInfoAsynctask(this);
        try {
            URL url = new URL("https://api.wmata.com/StationPrediction.svc/json/GetPrediction/"+ linecode +"?&api_key=8502900a4175457cbdc6a7a81acc8319");
            metroInfoAsynctask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        metroInfoAsynctask.setmLoadDataListener(new LoadDataListener() {
            @Override
            public void getData(String[] data) {
                mStation = data[0];
                mLoadStationListener.getStation(mStation);
            }

            @Override
            public void getDataFail() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
