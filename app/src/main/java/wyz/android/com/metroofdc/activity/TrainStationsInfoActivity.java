package wyz.android.com.metroofdc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.TempAdapter;
import wyz.android.com.metroofdc.adpater.TrainStationInfomationAdapter;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.TrainStationInfo;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/6/12.
 */
public class TrainStationsInfoActivity extends AppCompatActivity implements LoadDataListener, SwipeRefreshLayout.OnRefreshListener{
    private FloatingActionButton fb;
    private Boolean mFb = false;
    private Toolbar toolBar;
    private RecyclerView recyclerView;
    private Handler handler;
    private Runnable run;
    private TextView mTxt;
    private SwipeRefreshLayout mRefresh;
    private TrainStationInfo station;
    private TrainStationInfomationAdapter adapter;
    private String stationName;
    private String color;
    private String lineCode;
    private String cordinate;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_train_station_infomation);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        Intent intent = getIntent();
        lineCode = intent.getStringExtra("lineCode");
        stationName = intent.getStringExtra("stationName");
        color = intent.getStringExtra("color");
        cordinate = intent.getStringExtra("cordinate");

        fb = (FloatingActionButton)findViewById(R.id.fb_station);
        setFloatButtonColor(stationName);

        //street view

        ImageView imageView = (ImageView) findViewById(R.id.img_train);
        String url = "https://maps.googleapis.com/maps/api/streetview?size=400x600&fov=120&location=" + cordinate + "&key=AIzaSyCNQJGL2muVimX6dRmNrLIoEdAj09gHdIc";
        Picasso.with(this).load(url).into(imageView);

        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapse_train);

        toolBar = (Toolbar) findViewById(R.id.tb_stationinfo);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layout.setTitle(stationName);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_station_info);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TempAdapter());

        mTxt = (TextView)findViewById(R.id.txt_time);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swipefresh);
        mRefresh.setOnRefreshListener(this);


        //定时刷新
        handler = new Handler();
        run = new Runnable() {
            @Override
            public void run() {
                lanuchTask();
                handler.postDelayed(this, 20000);
            }
        };
        handler.post(run);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Please collect station" + mLineCode, Toast.LENGTH_SHORT).show();]
                //getActivity().deleteFile("favorTrain.txt");
                if (mFb == true) {
                    mFb = false;
                    fb.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
                    try {
                        FileInputStream fis = openFileInput("favorTrain.txt");
                        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        String line = "";
                        List<String> list = new ArrayList<>();
                        while ((line = br.readLine()) != null) {
                            //System.out.println(line);
                            String temp = line.split("_")[1];
                            if (!temp.equals(stationName)) {
                                list.add(line);
                            }
                        }
                        br.close();
                        isr.close();
                        fis.close();
                        deleteFile("favorTrain.txt");
                        try {
                            FileOutputStream fos = openFileOutput("favorTrain.txt", Context.MODE_APPEND);
                            for (int i = 0; i < list.size(); i++) {
                                fos.write((list.get(i) + "\n").getBytes());
                            }
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mFb = true;
                    fb.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_focus));
                    try {
                        FileOutputStream fos = openFileOutput("favorTrain.txt", Context.MODE_APPEND);
                        fos.write((lineCode + "_" + stationName + "_" + color + "_" + cordinate + "\n").getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void setFloatButtonColor(String mStationName)
    {
        File fileTrain = new File(getApplicationContext().getFilesDir(), "favorTrain.txt");
        if(fileTrain.exists())
        {
            FileInputStream fis = null;
            Log.e("file", "file");
            try {
                fis = openFileInput("favorTrain.txt");
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = "";
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    String temp = line.split("_")[1];
                    if(temp.equals(mStationName))
                    {
                        mFb = true;
                        fb.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_focus));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void getData(String[] data) {
        Gson gson = new Gson();
        station = gson.fromJson(data[0], TrainStationInfo.class);
        if(station.getTrains() != null)
        {
            if (station.getTrains().size() == 0) {
                TextView txt = (TextView) findViewById(R.id.txt_station_null);
                txt.setVisibility(View.VISIBLE);
                txt.setText("No data found");
            }
            adapter = new TrainStationInfomationAdapter(this, station);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            TextView txt = (TextView) findViewById(R.id.txt_station_null);
            txt.setVisibility(View.VISIBLE);
            txt.setText("No data found");
        }
        String time = getCurrentTime();
        mTxt.setText("Updated at:" + time);

    }

    @Override
    public void getDataFail() {
        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

    public void lanuchTask(){
        try {
            URL url = new URL(Constant.METRO_URL + "GetPrediction/" + lineCode +Constant.METRO_API_KEY);
            MetroInfoAsynctask metroInfoAsynctask = new MetroInfoAsynctask(this);
            metroInfoAsynctask.execute(url);
            metroInfoAsynctask.setmLoadDataListener(this);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    //获取系统时间
    public String getCurrentTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mTimer.cancel();
        handler.removeCallbacks(run);
    }

    //    下拉刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lanuchTask();
                mRefresh.setRefreshing(false);
                //adapter.notifyDataSetChanged();
            }
        }, 500);
    }

    @Override
    public void finish() {
        super.finish();
        //Log.e("a", "finish");
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_train_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_map) {
            if(checkGooglePlaySerivce()) {
                Intent intent = new Intent(this, MapActivity.class);
                String lat = cordinate.split(",")[0];
                String lon = cordinate.split(",")[1];
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("name", stationName);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean checkGooglePlaySerivce()
    {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int result = googleApi.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS)
        {
            if(googleApi.isUserResolvableError(result))
            {
                googleApi.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else {
                Log.i("Alert", "This device is not supported");
            }
            return false;
        }
        return true;
    }
}
