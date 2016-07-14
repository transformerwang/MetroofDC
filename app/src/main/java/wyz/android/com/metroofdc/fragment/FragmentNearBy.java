package wyz.android.com.metroofdc.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.NearByFavorAdapter;
import wyz.android.com.metroofdc.listener.CommunicateListener;
import wyz.android.com.metroofdc.listener.LoadLocationListener;
import wyz.android.com.metroofdc.util.LocationFinder;

/**
 * Created by wangyuzhe on 1/6/16.
 */
public class FragmentNearBy extends Fragment{

    @BindView(R.id.vp_nearBy)
    ViewPager vpNearBy;
    private TabLayout mTabLayout;
    final private int PERMISSION_REQUEST_CODE = 100;

    private NearByFavorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_by, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("NearBy");

        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        mTabLayout.setVisibility(View.VISIBLE);
        adapter = new NearByFavorAdapter(getChildFragmentManager());
        checkPermission();
    }

    public void checkPermission()
    {
        int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getActivity()).setMessage("You need to allow access to location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                            }
                        })
                        .create()
                        .show();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            vpNearBy.setAdapter(adapter);
            mTabLayout.setupWithViewPager(vpNearBy);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //locationFinder.detectLocation();
                } else {
                    Toast.makeText(getActivity(), "Location permission is needed", Toast.LENGTH_SHORT).show();
                }
        }
        vpNearBy.setAdapter(adapter);
        mTabLayout.setupWithViewPager(vpNearBy);
    }
}
