package wyz.android.com.metroofdc.adpater;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import wyz.android.com.metroofdc.fragment.FragmentNearByBus;
import wyz.android.com.metroofdc.fragment.FragmentNearByTrain;

/**
 * Created by wangyuzhe on 16/6/17.
 */
public class NearByFavorAdapter extends  FragmentStatePagerAdapter {

    public NearByFavorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch(position)
        {
            case 0:
                frag = new FragmentNearByTrain();
                break;
            case 1:
                frag = new FragmentNearByBus();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Train" : "Bus";
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
