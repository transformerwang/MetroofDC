package wyz.android.com.metroofdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.BusStationDetailAdapter;
import wyz.android.com.metroofdc.adpater.SuggestionAdapter;
import wyz.android.com.metroofdc.adpater.TempAdapter;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.BusStationsRoute;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/6/13.
 */
public class BusStationActivity extends AppCompatActivity implements LoadDataListener, View.OnClickListener {


    @BindView(R.id.tv_bus_direction_1)
    TextView tvBusDirection1;
    @BindView(R.id.text_direction)
    TextView textDirection;
    @BindView(R.id.image_up)
    ImageView imageUp;
    @BindView(R.id.recyclerView_bus_detail)
    RecyclerView recyclerViewBusDetailUp;
    @BindView(R.id.tv_bus_direction_2)
    TextView tvBusDirection2;
    @BindView(R.id.text_direction_a)
    TextView textDirectionA;
    @BindView(R.id.image_down)
    ImageView imageDown;
    @BindView(R.id.down)
    RelativeLayout down;
    @BindView(R.id.recyclerView_bus_detail_a)
    RecyclerView recyclerViewBusDetailDown;


    private Toolbar toolBar;
    private String mRouteId;
    private String mName;
    private BusStationsRoute stations;
    private Boolean mUp = true;
    private Boolean mDown = true;
    private ArrayList<String> mStopId;
    private List<String> mCordinate;
    private MenuItem mSearchItem;
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bus_stations);

        ButterKnife.bind(BusStationActivity.this);

        Intent intent = getIntent();
        mRouteId = intent.getStringExtra("routeId");
        mName = intent.getStringExtra("name");

        toolBar = (Toolbar) findViewById(R.id.tb_stations);
        toolBar.setTitle(mName);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        try {
            URL url = new URL(Constant.BUS_URL + "jRouteDetails" + Constant.METRO_API_KEY + "&RouteID=" + mRouteId);
            MetroInfoAsynctask asynctask = new MetroInfoAsynctask(this);
            asynctask.execute(url);
            asynctask.setmLoadDataListener(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager managerUp = new LinearLayoutManager(this);
        RecyclerView.LayoutManager managerDown = new LinearLayoutManager(this);
        recyclerViewBusDetailUp.setLayoutManager(managerUp);
        recyclerViewBusDetailDown.setLayoutManager(managerDown);

        recyclerViewBusDetailUp.setAdapter(new TempAdapter());
        recyclerViewBusDetailDown.setAdapter(new TempAdapter());
    }

    @Override
    public void getData(String[] data) {
        mStopId = new ArrayList<>();
        mCordinate = new ArrayList<>();

        Gson gson = new Gson();
        stations = gson.fromJson(data[0], BusStationsRoute.class);
        if (stations.getDirection0() != null) {
            tvBusDirection1.setText(stations.getDirection0().getTripHeadsign());
            textDirection.setText(stations.getDirection0().getDirectionText());

            for(int i = 0; i < stations.getDirection0().getStops().size(); i++)
            {
                BusStationsRoute.Direction0Bean.StopsBean stop = stations.getDirection0().getStops().get(i);
                mStopId.add(stop.getName() + "\n" + stop.getStopID());
                mCordinate.add(stop.getLat() + "," + stop.getLon());
            }

            recyclerViewBusDetailUp.setAdapter(new BusStationDetailAdapter(stations.getDirection0(), 0, getSupportFragmentManager()));
        }

        if (stations.getDirection1() != null) {
            tvBusDirection2.setText(stations.getDirection1().getTripHeadsign());
            textDirectionA.setText(stations.getDirection1().getDirectionText());

            for(int i = 0; i < stations.getDirection1().getStops().size(); i++)
            {
                BusStationsRoute.Direction1Bean.StopsBean stop = stations.getDirection1().getStops().get(i);
                mStopId.add(stop.getName()+ "\n" + stop.getStopID());
                mCordinate.add(stop.getLat() + "," + stop.getLon());
            }

            recyclerViewBusDetailDown.setAdapter(new BusStationDetailAdapter(stations.getDirection1(), 1, getSupportFragmentManager()));

        }
        search.setAdapter(new SuggestionAdapter(this, R.layout.tab_suggestion_dropdown, mStopId));
//        searchBusStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = (TextView) view.findViewById(R.id.tv_str);
//                String query = textView.getText().toString();
//                int location = mStopId.indexOf(query);
//                Intent intent = new Intent(BusStationActivity.this, BusStationInfoActivity.class);
//                intent.putExtra("stopId", mStopId.get(location).split("\\n")[0]);
//                intent.putExtra("stationName", mStopId.get(location).split("\\n")[1]);
//                intent.putExtra("lat", mCordinate.get(location).split(",")[0]);
//                intent.putExtra("lon", mCordinate.get(location).split(",")[1]);
//                startActivity(intent);
//                searchBusStations.closeSearch();
//            }
//        });
    }

    @Override
    public void getDataFail() {
        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();

    }

    @OnClick({R.id.up, R.id.down})
    public void onClick(View view) {
        if (stations != null) {
            switch (view.getId()) {
                case R.id.up:
                    if (mUp) {
                        imageUp.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                        mUp = false;
                    } else {
                        imageUp.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                        mUp = true;
                    }

                    if (stations.getDirection0() != null && recyclerViewBusDetailUp.getVisibility() == View.GONE && stations.getDirection0().getStops().size() != 0) {
                        recyclerViewBusDetailUp.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewBusDetailUp.setVisibility(View.GONE);
                    }
                    break;
                case R.id.down:
                    if (mDown) {
                        imageDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                        mDown = false;
                    } else {
                        imageDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                        mDown = true;
                    }

                    if (stations.getDirection1() != null && recyclerViewBusDetailDown.getVisibility() == View.GONE && stations.getDirection1().getStops().size() != 0) {
                        recyclerViewBusDetailDown.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewBusDetailDown.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bus_station, menu);
        mSearchItem = menu.findItem(R.id.action_search_bus_stations);
        mSearchView = (SearchView)mSearchItem.getActionView();
        search = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        search.setThreshold(2);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                int pos = mStopId.indexOf(query);

                Intent intent = new Intent(BusStationActivity.this, BusStationInfoActivity.class);
                intent.putExtra("stopId", mStopId.get(pos).split("\\n")[1]);
                intent.putExtra("stationName", mStopId.get(pos).split("\\n")[0]);
                intent.putExtra("lat", mCordinate.get(pos).split(",")[0]);
                intent.putExtra("lon", mCordinate.get(pos).split(",")[1]);
                startActivity(intent);
                mSearchView.setIconified(true);
                MenuItemCompat.collapseActionView(mSearchItem);
            }
        });
        return true;
    }


}
