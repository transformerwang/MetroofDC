package wyz.android.com.metroofdc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ooo.oxo.library.widget.TouchImageView;
import wyz.android.com.metroofdc.R;

/**
 * Created by wangyuzhe on 16/5/27.
 */
public class MetroMapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_map);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        TouchImageView map = (TouchImageView) findViewById(R.id.map_img);
        map.setImageDrawable(getResources().getDrawable(R.drawable.img_metro));
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_stations);
        toolbar.setTitle("Metro Map");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
