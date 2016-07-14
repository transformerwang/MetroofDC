package wyz.android.com.metroofdc.adpater;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.domain.BusStations;

/**
 * Created by wangyuzhe on 16/5/22.
 */
public class BusStationAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mStations;

    public BusStationAdapter(Context conext, List<String> station) {
        this.mContext = conext;
        this.mStations = station;
    }

    @Override
    public int getCount() {
        return mStations.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_item_bus_stations, null);
            holder = new ViewHolder();
            holder.routeName = (TextView) convertView.findViewById(R.id.tv_bus_name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = mStations.get(position);
        holder.routeName.setText(name);
        return convertView;
    }
}

class ViewHolder
{
    public TextView routeName;
}
