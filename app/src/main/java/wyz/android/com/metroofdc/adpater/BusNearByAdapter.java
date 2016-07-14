package wyz.android.com.metroofdc.adpater;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.activity.BusStationInfoActivity;
import wyz.android.com.metroofdc.domain.BusNearby;

/**
 * Created by wangyuzhe on 16/6/20.
 */
public class BusNearByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    private List<String> mStopId = new ArrayList<>();
//    private List<String> mStopName = new ArrayList<>();
//    private List<String> mCordinate = new ArrayList<>();
//    private List<String> mRoute = new ArrayList<>();
    private Context mContext;
    private String mLat;
    private String mLog;
    private BusNearby mBus;

    public BusNearByAdapter(BusNearby bus, Context context, String lat, String log)
    {
//        this.mStopId = stopId;
//        this.mStopName = stopName;
//        this.mCordinate = distance;
//        this.mRoute = route;
        this.mBus = bus;
        this.mContext = context;
        this.mLat = lat;
        this.mLog = log;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView textName;
        public TextView textId;
        public TextView textDistance;
        public TextView textRoute;
//        public List<String> stopId;
//        public List<String> stopName;
        public BusNearby bus;
        public String lat;
        public String lon;
        public ImageView imageView;

        public DataViewHolder(View itemView,BusNearby sBus, String lats, String lons) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_stop_name);
            textId = (TextView) itemView.findViewById(R.id.text_stop_id);
            textDistance = (TextView) itemView.findViewById(R.id.text_stop_dis);
            textRoute = (TextView) itemView.findViewById(R.id.text_stop_route);
            imageView = (ImageView) itemView.findViewById(R.id.img_bus_near);
            itemView.setOnClickListener(this);
            imageView.setOnClickListener(this);
//            this.stopId = mStopId;
//            this.stopName = mStopName;
            this.bus = sBus;
            this.lat = lats;
            this.lon = lons;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.relative_bus:
                    Intent intent = new Intent(v.getContext(), BusStationInfoActivity.class);
                    intent.putExtra("stopId", bus.getStops().get(getLayoutPosition()).getStopID());
                    intent.putExtra("stationName", bus.getStops().get(getLayoutPosition()).getName());
                    intent.putExtra("lat", String.valueOf(bus.getStops().get(getLayoutPosition()).getLat()));
                    intent.putExtra("lon", String.valueOf(bus.getStops().get(getLayoutPosition()).getLon()));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.img_bus_near:
                    String mlat = String.valueOf(bus.getStops().get(getLayoutPosition()).getLat());
                    String mlon = String.valueOf(bus.getStops().get(getLayoutPosition()).getLon());
                    Uri uri = Uri.parse("http://maps.google.com/maps?saddr=" + lat +"," + lon + "&daddr=" + mlat + "," + mlon);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    //mapIntent.setPackage("com.google.android.apps.maps");
                    v.getContext().startActivity(mapIntent);
                    break;
            }
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_bus_nearby, parent, false);
        return new DataViewHolder(view, mBus, mLat, mLog);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder newHolder = (DataViewHolder)holder;
        BusNearby.StopsBean stop = mBus.getStops().get(position);
        newHolder.textName.setText(stop.getName());
        float[] result = new float[1];
        Location.distanceBetween(Double.valueOf(mLat), Double.valueOf(mLog), stop.getLat(), stop.getLon(), result);
        DecimalFormat df = new DecimalFormat("##0.00");
        double res = result[0]/1609.344;
        newHolder.textDistance.setText("Approx Distance: " + df.format(res) + " miles");
        newHolder.textId.setText("Stop ID: " +stop.getStopID());
        String Routes = "";
        for(String route : stop.getRoutes())
        {
            Routes += route + ", ";
        }
        newHolder.textRoute.setText("Routes:" + Routes.substring(0,Routes.length()-2));
    }

    @Override
    public int getItemCount() {
        return mBus.getStops().size();
    }
}
