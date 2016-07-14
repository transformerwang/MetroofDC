package wyz.android.com.metroofdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import wyz.android.com.metroofdc.MainActivity;
import wyz.android.com.metroofdc.R;

/**
 * Created by wangyuzhe on 16/6/21.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.tb_stations)
    Toolbar tbStations;
    private String lat;
    private String lon;
    private String name;
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

        Intent intent = getIntent();
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");
        name = intent.getStringExtra("name");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);


        tbStations.setTitle(name);
        setSupportActionBar(tbStations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbStations.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng station = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
        googleMap.addMarker(new MarkerOptions().position(station).title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(station, 15));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }


}
