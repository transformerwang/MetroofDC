package wyz.android.com.metroofdc.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.domain.TrainStationInfo;

/**
 * Created by wangyuzhe on 16/5/19.
 */
public class TrainStationInfomationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private TrainStationInfo mStationInfo;

    public TrainStationInfomationAdapter(Context context, TrainStationInfo mStationInfo)
    {
        this.mContext = context;
        this.mStationInfo = mStationInfo;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView station;
        public TextView status;

        public DataViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.station_img);
            station = (TextView) itemView.findViewById(R.id.txt_stations);
            status = (TextView) itemView.findViewById(R.id.txt_status);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item_station_info, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder newHolder = (DataViewHolder)holder;
        newHolder.station.setText(mStationInfo.getTrains().get(position).getDestinationName());
        String min = mStationInfo.getTrains().get(position).getMin();
        if(min.contains("BRD") || min.contains("ARR") || min.contains("---"))
        {
            newHolder.status.setText(min);
        }
        else
        {
            newHolder.status.setText(min + "min");
        }
        int iconRec = 0;
        switch (mStationInfo.getTrains().get(position).getLine())
        {
            case "GR":
                iconRec = R.drawable.ic_green;
                break;
            case "RD":
                iconRec = R.drawable.ic_red;
                break;
            case "BL":
                iconRec = R.drawable.ic_blue;
                break;
            case "SV":
                iconRec = R.drawable.ic_silver;
                break;
            case "OR":
                iconRec = R.drawable.ic_orange;
                break;
            case "YL":
                iconRec = R.drawable.ic_yellow;
                break;
            default:
                iconRec = R.drawable.ic_no;
                break;

        }
        newHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(iconRec));
    }

    @Override
    public int getItemCount() {
        return mStationInfo.getTrains().size();
    }
}
