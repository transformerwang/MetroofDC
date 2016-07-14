package wyz.android.com.metroofdc.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import wyz.android.com.metroofdc.MainActivity;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.TempAdapter;
import wyz.android.com.metroofdc.adpater.TrainNearByAdapter;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.TrainNearby;
import wyz.android.com.metroofdc.listener.CommunicateListener;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.listener.LoadLocationListener;
import wyz.android.com.metroofdc.util.LocationFinder;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/6/17.
 */
public class FragmentNearByTrain extends Fragment implements LoadDataListener, LoadLocationListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView_nearBy_train)
    RecyclerView recyclerViewNearByTrain;
    @BindView(R.id.refresh_train)
    SwipeRefreshLayout refreshTrain;

    private List<String> mStations;
    private List<String> mColors;
    private List<String> mLineCode;
    private List<String> mCordinate;
    private List<String> mNearLine;
    private String mLat;
    private String mLog;
    private LocationFinder locationFinder;
    final private int PERMISSION_REQUEST_CODE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_train, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("view", "Created");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNearByTrain.setLayoutManager(layoutManager);
        recyclerViewNearByTrain.setAdapter(new TempAdapter());

        refreshTrain.setOnRefreshListener(this);

        locationFinder = new LocationFinder(getActivity(), true);
        locationFinder.setListner(this);
        checkPermission();
    }

    @Override
    public void getData(String[] data) {
        mStations = new ArrayList<>();
        mLineCode = new ArrayList<>();
        mColors = new ArrayList<>();
        mNearLine = new ArrayList<>();
        mCordinate = new ArrayList<>();

        Gson gson = new Gson();
        TrainNearby train = gson.fromJson(data[0], TrainNearby.class);
        if(train.getEntrances() != null) {
            if (train.getEntrances() != null && getActivity() != null) {
                for (int i = 0; i < train.getEntrances().size(); i++) {
                    String temp = train.getEntrances().get(i).getStationCode1();
                    if (!mNearLine.contains(temp)) {
                        mNearLine.add(temp);
                        Log.e("temp", temp);
                    }
                }

                String[] stations = getActivity().getResources().getStringArray(R.array.train);
                for (int j = 0; j < mNearLine.size(); j++) {
                    String line = mNearLine.get(j);
                    for (int k = 0; k < stations.length; k++) {
                        String[] lineArray = stations[k].split("\\*");
                        String linecode = lineArray[2];
                        String[] codeArray = linecode.split(",");
                        if (codeArray.length < 2) {
                            if (line.equals(codeArray[0])) {
                                mStations.add(lineArray[0]);
                                mColors.add(lineArray[1]);
                                mLineCode.add(lineArray[2]);
                                mCordinate.add(lineArray[3]);
                            }
                        } else {
                            if (line.equals(codeArray[0]) || line.equals(codeArray[1])) {
                                if (!mStations.contains(lineArray[0])) {
                                    mStations.add(lineArray[0]);
                                    mColors.add(lineArray[1]);
                                    mLineCode.add(lineArray[2]);
                                    mCordinate.add(lineArray[3]);
                                }
                            }

                        }
                    }
                }

                TrainNearByAdapter adapter = new TrainNearByAdapter(mStations, mColors, mLineCode, mCordinate, getActivity(), mLat, mLog);
                recyclerViewNearByTrain.setAdapter(adapter);
            } else {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "There is no station near you.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void getDataFail() {

    }

    public void launchTask(String lat, String lon) {
        try {
            URL url = new URL(Constant.METRO_STATIONS + Constant.METRO_API_KEY + "&Lat=" + lat + "&Lon=" + lon + "&Radius=3000");
            MetroInfoAsynctask task = new MetroInfoAsynctask(getActivity());
            task.execute(url);
            task.setmLoadDataListener(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLocation(Location location) {
        refreshTrain.setRefreshing(false);
        mLat = String.valueOf(location.getLatitude());
        mLog = String.valueOf(location.getLongitude());
        Log.e("c", "getLoation");
        //Log.e("provider", location.getProvider());
//        Toast.makeText(getActivity(), location.getProvider(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), mLog, Toast.LENGTH_SHORT).show();
        launchTask(mLat, mLog);
    }

    @Override
    public void getLocationFailure(String reason) {
        refreshTrain.setRefreshing(false);
        Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                locationFinder = new LocationFinder(getActivity(), false);
                locationFinder.setListner(FragmentNearByTrain.this);
                checkPermission();
            }
        }, 500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("d","destroy");
        if(refreshTrain != null) {
            refreshTrain.setRefreshing(false);
        }
        locationFinder.endLoadDetection();
    }

    public void checkPermission()
    {
        int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "You need to allow access to location", Toast.LENGTH_SHORT).show();
        }
        else
        {
            locationFinder.detectLocation();
        }
    }
}
