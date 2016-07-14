package wyz.android.com.metroofdc.listener;

import android.location.Location;

/**
 * Created by wangyuzhe on 16/6/17.
 */
public interface LoadLocationListener {

    void getLocation(Location location);
    void getLocationFailure(String reason);
}
