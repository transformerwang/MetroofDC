package wyz.android.com.metroofdc.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import wyz.android.com.metroofdc.fragment.FragmentFavorBus;
import wyz.android.com.metroofdc.fragment.FragmentFavorTrain;

/**
 * Created by wangyuzhe on 16/6/10.
 */
public class FavorViewpagerAdapter extends FragmentPagerAdapter {

    public FavorViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position)
        {
            case 0:
                frag = new FragmentFavorTrain();
                break;
            case 1:
                frag = new FragmentFavorBus();
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
        switch (position)
        {
            case 0:
                return "Train";
            case 1:
                return "Bus";
            default:
                return null;
        }
    }
}
