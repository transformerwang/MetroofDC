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
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.SuggestionAdapter;
import wyz.android.com.metroofdc.adpater.TrainStationAdapter;

/**
 * Created by wangyuzhe on 16/6/12.
 */
public class TrainsStationActivity extends AppCompatActivity {


    private ArrayList<String> mStation = new ArrayList<>();
    private List<String> mColors = new ArrayList<>();
    private List<String> mLineCode = new ArrayList<>();
    private List<String> mCordinate = new ArrayList<>();
    private ArrayList<String> mStationAll = new ArrayList<>();
    private Toolbar toolbar;
    private MenuItem searchItem;
    private SearchView searchView;
    private SearchView.SearchAutoComplete search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_train_stations);

        toolbar = (Toolbar) findViewById(R.id.tb_stations);
        toolbar.setTitle("TrainStation");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        String line = intent.getStringExtra("line");

        getAllLines(line);

//        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                TextView tv = (TextView) view.findViewById(android.R.id.text1);
////                Log.e("tv", tv.getText().toString());
//                TextView tx = (TextView) view.findViewById(R.id.tv_str);
//                String query = tx.getText().toString();
//                int location = mStation.indexOf(query);
//                Intent intentSearch = new Intent(TrainsStationActivity.this, TrainStationsInfoActivity.class);
//                intentSearch.putExtra("lineCode", mLineCode.get(location));
//                intentSearch.putExtra("stationName", query);
//                intentSearch.putExtra("color", mColors.get(location));
//                intentSearch.putExtra("cordinate", mCordinate.get(location));
//                startActivity(intentSearch);
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//                search.closeSearch();
//            }
//        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_stations);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TrainStationAdapter(this, mStation, mColors, mLineCode, mCordinate, 0));

    }


    public void getAllLines(String mLine) {
        String txt = null;
        switch (mLine) {
            case "Blue":
                txt = "blue.txt";
                break;
            case "Green":
                txt = "green.txt";
                break;
            case "Yellow":
                txt = "yellow.txt";
                break;
            case "Silver":
                txt = "silver.txt";
                break;
            case "Orange":
                txt = "orange.txt";
                break;
            case "Red":
                txt = "red.txt";
                break;
            default:
                break;
        }
        try {
            InputStream is = getResources().getAssets().open(txt);
            InputStreamReader ir = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(ir);
            String line;
            while ((line = br.readLine()) != null) {
                String name = line.split("\\*")[0];
                String color = line.split("\\*")[1];
                String linecode = line.split("\\*")[2];
                String cordinate = line.split("\\*")[3];
                mStation.add(name);
                mColors.add(color);
                mLineCode.add(linecode);
                mCordinate.add(cordinate);
            }
            br.close();
            ir.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mStationAll = (ArrayList<String>) mStation.clone();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_train_station, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        search = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search.setThreshold(1);
        search.setAdapter(new SuggestionAdapter(this, R.layout.tab_suggestion_dropdown, mStationAll));

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                int location = mStation.indexOf(query);
                Intent intentSearch = new Intent(TrainsStationActivity.this, TrainStationsInfoActivity.class);
                intentSearch.putExtra("lineCode", mLineCode.get(location));
                intentSearch.putExtra("stationName", query);
                intentSearch.putExtra("color", mColors.get(location));
                intentSearch.putExtra("cordinate", mCordinate.get(location));
                startActivity(intentSearch);
                searchView.setIconified(true);
                MenuItemCompat.collapseActionView(searchItem);
            }
        });

        return true;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

}
