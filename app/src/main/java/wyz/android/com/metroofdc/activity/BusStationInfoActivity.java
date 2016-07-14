package wyz.android.com.metroofdc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.BusRealTimeAdapter;
import wyz.android.com.metroofdc.adpater.TempAdapter;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.BusRealTime;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/6/13.
 */
public class BusStationInfoActivity extends AppCompatActivity implements LoadDataListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.txt_update)
    TextView txtUpdate;
    @BindView(R.id.tb_businfo)
    Toolbar tbBusinfo;
    @BindView(R.id.appbarlay)
    AppBarLayout appbarlay;
    @BindView(R.id.recyclerView_next_bus)
    RecyclerView recyclerViewNextBus;
    @BindView(R.id.swipefreshbus)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fb_bus)
    FloatingActionButton fbBus;
    @BindView(R.id.img_bus)
    ImageView imgBus;
    @BindView(R.id.txt_stop)
    TextView txtStop;

    private Toolbar toolBar;
    private Handler handler;
    private String stopId;
    private String stationName;
    private String lat;
    private String lon;
    private Runnable run;
    private Boolean mFb = false;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_next_bus);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

        Intent intent = getIntent();
        stopId = intent.getStringExtra("stopId");
        stationName = intent.getStringExtra("stationName");
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");

        setFloatButton();
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        txtStop.setText(stopId);
        toolBar = (Toolbar) findViewById(R.id.tb_businfo);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout.setTitle(stationName);

        layout.setExpandedTitleTextAppearance(R.style.Toolbar_ExpendedTitleText);
//        layout.setCollapsedTitleTextAppearance(R.style.Toolbar_CollapsedTitleText);
//        layout.setExpandedTitleMarginStart(20);
//        layout.setExpandedTitleMarginBottom(200);
        //toolBar.setTitle(stationName);
        //setSupportActionBar(toolBar);


        ImageView imageView = (ImageView) findViewById(R.id.img_bus);
        String url = "https://maps.googleapis.com/maps/api/streetview?size=400x600&fov=120&location=" + lat + "," + lon + "&key=AIzaSyCNQJGL2muVimX6dRmNrLIoEdAj09gHdIc";
        Picasso.with(this).load(url).into(imageView);

        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewNextBus.setLayoutManager(layoutManager);
        recyclerViewNextBus.setAdapter(new TempAdapter());

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

        setFloatButtonFavor();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void setFloatButton() {
        File fileTrain = new File(getApplicationContext().getFilesDir(), "favorBus.txt");
        if (fileTrain.exists()) {
            FileInputStream fis = null;
            try {
                fis = openFileInput("favorBus.txt");
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String line = "";
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    String temp = line.split("_")[0];
                    if (temp.equals(stopId)) {
                        mFb = true;
                        fbBus.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_focus));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setFloatButtonFavor() {
        fbBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().deleteFile("favorBus.txt");
                if (mFb == true) {
                    mFb = false;
                    fbBus.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
                    try {
                        FileInputStream fis = openFileInput("favorBus.txt");
                        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        String line = "";
                        List<String> list = new ArrayList<String>();
                        while ((line = br.readLine()) != null) {
                            //System.out.println(line);
                            String temp = line.split("_")[0];
                            if (!temp.equals(stopId)) {
                                list.add(line);
                            }
                        }
                        br.close();
                        isr.close();
                        fis.close();
                        deleteFile("favorBus.txt");
                        try {
                            FileOutputStream fos = openFileOutput("favorBus.txt", Context.MODE_APPEND);
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
                    fbBus.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_focus));
                    try {
                        FileOutputStream fos = openFileOutput("favorBus.txt", Context.MODE_APPEND);
                        fos.write((stopId + "_" + stationName + "_" + lat + "," + lon + "\n").getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void lanuchTask() {
        try {
            URL url = new URL(Constant.BUS_NEXT_URL + Constant.METRO_API_KEY + "&StopID=" + stopId);
            MetroInfoAsynctask metroInfoAsynctask = new MetroInfoAsynctask(this);
            metroInfoAsynctask.execute(url);
            metroInfoAsynctask.setmLoadDataListener(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData(String[] data) {
        Gson gson = new Gson();
        BusRealTime bus = gson.fromJson(data[0], BusRealTime.class);

        if(bus.getPredictions() != null)
        {
            if(bus.getPredictions().size() == 0)
            {
                TextView txt = (TextView) findViewById(R.id.txt_busstation_null);
                txt.setVisibility(View.VISIBLE);
                txt.setText("No data found");
            }
            BusRealTimeAdapter adapter = new BusRealTimeAdapter(bus);
            recyclerViewNextBus.setAdapter(adapter);
        }
        else
        {
            TextView txt = (TextView) findViewById(R.id.txt_busstation_null);
            txt.setVisibility(View.VISIBLE);
            txt.setText("No data found");
        }
        txtUpdate.setText("Upadated at" + getCurrentTime());

    }

    @Override
    public void getDataFail() {
        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lanuchTask();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    //获取系统时间
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_train_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_map) {
            if (checkGooglePlaySerivce()) {
                Intent intent = new Intent(this, MapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("name", stationName);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(run);
    }

    public boolean checkGooglePlaySerivce() {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int result = googleApi.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleApi.isUserResolvableError(result)) {
                googleApi.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("Alert", "This device is not supported");
            }
            return false;
        }
        return true;
    }
}
