package wyz.android.com.metroofdc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.TrainIncident;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;

/**
 * Created by wangyuzhe on 16/5/27.
 */
public class AlertMetroActivity extends AppCompatActivity implements LoadDataListener {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_incident);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

        linearLayout = (LinearLayout) findViewById(R.id.linear_incident);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_stations);
        toolbar.setTitle("Train Incident");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            URL url = new URL(Constant.METRO_INCIDENT + Constant.METRO_API_KEY);
            MetroInfoAsynctask asynctask = new MetroInfoAsynctask(this);
            asynctask.execute(url);
            asynctask.setmLoadDataListener(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData(String[] data) {
        Gson gson = new Gson();
        TrainIncident trainIncident = gson.fromJson(data[0], TrainIncident.class);
        if(trainIncident.getIncidents() != null)
        {
            if (trainIncident.getIncidents().size() == 0) {
                Toast.makeText(this, "Metro is running normally", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < trainIncident.getIncidents().size(); i++) {
                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(30, 50, 30, 50);
                    textView.setLayoutParams(lp);
                    textView.setTextSize(16);
                    textView.setText(trainIncident.getIncidents().get(i).getDescription());
                    linearLayout.addView(textView);
                }
            }
        }
    }

    @Override
    public void getDataFail() {
        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
