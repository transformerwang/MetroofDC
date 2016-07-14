package wyz.android.com.metroofdc;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import wyz.android.com.metroofdc.fragment.FragmentBus;
import wyz.android.com.metroofdc.fragment.FragmentFavor;
import wyz.android.com.metroofdc.fragment.FragmentNearBy;
import wyz.android.com.metroofdc.fragment.FragmentTrain;

public class MainActivity extends AppCompatActivity {

    private int[] mImageArray = {R.drawable.tab_train_bt, R.drawable.tab_bus_bt, R.drawable.tab_favor_bt, R.drawable.tab_set_bt};
    private Class[] mFragmentArray = {FragmentTrain.class, FragmentBus.class, FragmentFavor.class, FragmentNearBy.class};
    private String[] mTextArray = {"Metro", "Bus", "Favourites", "Nearby"};
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_stations);
        setSupportActionBar(toolbar);
        //openBusTxt();


        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId == "Metro" || tabId == "Bus")
                {
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                    tabLayout.setVisibility(View.GONE);
                }
            }
        });

        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (int i = 0; i < 4; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
            tabHost.addTab(tabSpec, mFragmentArray[i], null);
        }
    }

    private View getTabItemView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_img);
        TextView textView = (TextView) view.findViewById(R.id.tab_text);

        imageView.setImageResource(mImageArray[index]);
        textView.setText(mTextArray[index]);

        return view;
    }

}
