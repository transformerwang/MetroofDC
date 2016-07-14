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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.activity.TrainStationsInfoActivity;

/**
 * Created by wangyuzhe on 16/6/19.
 */
public class TrainNearByAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mStations = new ArrayList<>();
    private List<String> mColors = new ArrayList<>();
    private List<String> mLineCode = new ArrayList<>();
    private List<String> mCordinate = new ArrayList<>();
    private Context mContext;
    private String mLat;
    private String mLog;

    public TrainNearByAdapter(List<String> stations, List<String> colors, List<String> lineCode, List<String> cordinate, Context context, String lat, String log)
    {
        this.mStations = stations;
        this.mColors = colors;
        this.mLineCode = lineCode;
        this.mCordinate = cordinate;
        this.mContext = context;
        this.mLat = lat;
        this.mLog = log;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView stationName;
        public TextView distance;
        public LinearLayout linearLayout;
        public Context context;
        public List<String> linecode;
        public List<String> stations;
        public List<String> colorLine;
        public List<String> cordinate;
        public ImageView imageView;
        public String mlat;
        public String mlon;

        public DataViewHolder(View itemView, Context scontext, List<String> slineCode, List<String> sStations, List<String> color, List<String> scordinate, String lats, String lons) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = scontext;
            linecode = slineCode;
            stations = sStations;
            colorLine = color;
            cordinate = scordinate;
            mlat = lats;
            mlon = lons;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
            stationName = (TextView) itemView.findViewById(R.id.tv_st_name);
            distance = (TextView) itemView.findViewById(R.id.tv_st_distance);
            imageView = (ImageView) itemView.findViewById(R.id.img_distance);
            imageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.img_distance:
                    String lat = cordinate.get(getLayoutPosition()).split(",")[0];
                    String lon = cordinate.get(getLayoutPosition()).split(",")[1];
                    Uri uri = Uri.parse("http://maps.google.com/maps?saddr=" + mlat +"," + mlon + "&daddr=" + lat + "," + lon);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    //mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
                    break;
                case R.id.distance:
                    Intent intent = new Intent(context, TrainStationsInfoActivity.class);
                    intent.putExtra("lineCode", linecode.get(getLayoutPosition()));
                    intent.putExtra("stationName", stations.get(getLayoutPosition()));
                    intent.putExtra("color", colorLine.get(getLayoutPosition()));
                    intent.putExtra("cordinate", cordinate.get(getLayoutPosition()));
                    context.startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_train_nearby, parent, false);
        return new DataViewHolder(view, mContext,mLineCode,mStations,mColors,mCordinate, mLat, mLog);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder newHolder = (DataViewHolder)holder;
        newHolder.linearLayout.removeAllViews();
        setImage(position, newHolder);
        newHolder.stationName.setText(mStations.get(position));
        float[] result = new float[1];
        double lat = Double.valueOf(mCordinate.get(position).split(",")[0]);
        double log = Double.valueOf(mCordinate.get(position).split(",")[1]);
        Location.distanceBetween(Double.valueOf(mLat), Double.valueOf(mLog), lat, log, result);
        DecimalFormat df = new DecimalFormat("##0.00");
        double res = result[0]/1609.344;
        newHolder.distance.setText("Approx Distance: " + df.format(res) + " miles");
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    public void setImage(int position, DataViewHolder newHolder)
    {
        String[] line = mColors.get(position).split(",");
        //List<String> colorList = Arrays.asList(line);
        for(String color : line)
        {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(4, 0, 4, 0);
            imageView.setLayoutParams(lp);
            int img = 0;
            switch(color)
            {
                case "BL":
                    img = R.mipmap.ic_blue;
                    break;
                case "YL":
                    img = R.mipmap.ic_yellow;
                    break;
                case "GR":
                    img = R.mipmap.ic_green;
                    break;
                case "RD":
                    img = R.mipmap.ic_red;
                    break;
                case "OR":
                    img = R.mipmap.ic_orange;
                    break;
                case "SV":
                    img = R.mipmap.ic_silver;
                    break;
                default:
                    break;
            }
            imageView.setImageResource(img);
            newHolder.linearLayout.addView(imageView);
        }
    }
}
