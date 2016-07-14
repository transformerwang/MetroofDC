package wyz.android.com.metroofdc.fragment;

import android.Manifest;
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
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.BusNearByAdapter;
import wyz.android.com.metroofdc.adpater.TempAdapter;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.BusNearby;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.listener.LoadLocationListener;
import wyz.android.com.metroofdc.util.LocationFinder;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/6/17.
 */
public class FragmentNearByBus extends Fragment implements LoadDataListener, LoadLocationListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView_nearBy_bus)
    RecyclerView recyclerViewNearByBus;
    @BindView(R.id.refresh_bus)
    SwipeRefreshLayout refreshBus;

    private List<String> mStopId;
    private List<String> mName;
    private List<String> mCordinate;
    private List<String> mRoute;
    private String mLat;
    private String mLog;
    private LocationFinder locationFinder;
    final private int PERMISSION_REQUEST_CODE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_bus, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNearByBus.setLayoutManager(layoutManager);
        recyclerViewNearByBus.setAdapter(new TempAdapter());

        refreshBus.setOnRefreshListener(this);

        //get your current location
        locationFinder = new LocationFinder(getActivity(), true);
        locationFinder.setListner(this);
        checkPermission();
    }

    @Override
    public void getData(String[] data) {

        mName = new ArrayList<>();
        mStopId = new ArrayList<>();
        mCordinate = new ArrayList<>();
        mRoute = new ArrayList<>();

        Gson gson = new Gson();
        BusNearby bus = gson.fromJson(data[0], BusNearby.class);
        if(bus.getStops() != null) {
            if (getActivity() != null) {
                BusNearByAdapter adapter = new BusNearByAdapter(bus, getActivity(), mLat, mLog);
                recyclerViewNearByBus.setAdapter(adapter);
            }
        }
    }

    @Override
    public void getDataFail() {

    }

    public void launchTask(String lat, String lon) {
        try {
            URL url = new URL(Constant.BUS_URL + "jStops" + Constant.METRO_API_KEY + "&Lat=" + lat + "&Lon=" + lon + "&Radius=500");
            MetroInfoAsynctask task = new MetroInfoAsynctask(getActivity());
            task.execute(url);
            task.setmLoadDataListener(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLocation(Location location) {
        refreshBus.setRefreshing(false);
        mLat = String.valueOf(location.getLatitude());
        mLog = String.valueOf(location.getLongitude());
        launchTask(mLat, mLog);
    }

    @Override
    public void getLocationFailure(String reason) {
        refreshBus.setRefreshing(false);
        Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                locationFinder = new LocationFinder(getActivity(), false);
                locationFinder.setListner(FragmentNearByBus.this);
                checkPermission();
            }
        }, 500);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("d","destroy");
        if(refreshBus !=null) {
            refreshBus.setRefreshing(false);
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
