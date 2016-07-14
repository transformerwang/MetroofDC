package wyz.android.com.metroofdc.adpater;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.activity.BusStationActivity;
import wyz.android.com.metroofdc.activity.BusStationInfoActivity;
import wyz.android.com.metroofdc.domain.BusStationsRoute;

/**
 * Created by wangyuzhe on 16/5/22.
 */
public class BusStationDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BusStationsRoute.Direction0Bean direction0Entity;
    private BusStationsRoute.Direction1Bean direction1Entity;
    private int mTag;
    private FragmentManager mFm;

    public BusStationDetailAdapter(BusStationsRoute.Direction0Bean direction0Entity, int tag, FragmentManager fm) {
        this.direction0Entity = direction0Entity;
        this.mTag = tag;
        this.mFm = fm;
    }

    public BusStationDetailAdapter(BusStationsRoute.Direction1Bean direction1Entity, int tag, FragmentManager fm) {
        this.direction1Entity = direction1Entity;
        this.mTag = tag;
        this.mFm = fm;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textId;
        public RelativeLayout relativeLayout;

        public DataViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_stop_name);
            textId = (TextView) itemView.findViewById(R.id.text_stop_id);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_bus);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_bus_detail, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mTag == 0) {
            ((DataViewHolder) holder).textName.setText(direction0Entity.getStops().get(position).getName());
            ((DataViewHolder) holder).textId.setText(direction0Entity.getStops().get(position).getStopID());
            ((DataViewHolder) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BusStationInfoActivity.class);
                    intent.putExtra("stopId", direction0Entity.getStops().get(position).getStopID());
                    intent.putExtra("stationName", direction0Entity.getStops().get(position).getName());
                    intent.putExtra("lat", String.valueOf(direction0Entity.getStops().get(position).getLat()));
                    intent.putExtra("lon", String.valueOf(direction0Entity.getStops().get(position).getLon()));
                    v.getContext().startActivity(intent);
                    ((BusStationActivity)v.getContext()).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                }
            });
        } else {
            ((DataViewHolder) holder).textName.setText(direction1Entity.getStops().get(position).getName());
            ((DataViewHolder) holder).textId.setText(direction1Entity.getStops().get(position).getStopID());
            ((DataViewHolder) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BusStationInfoActivity.class);
                    intent.putExtra("stopId",direction1Entity.getStops().get(position).getStopID());
                    intent.putExtra("stationName", direction1Entity.getStops().get(position).getName());
                    intent.putExtra("lat", String.valueOf(direction1Entity.getStops().get(position).getLat()));
                    intent.putExtra("lon", String.valueOf(direction1Entity.getStops().get(position).getLon()));
                    v.getContext().startActivity(intent);
                    ((BusStationActivity)v.getContext()).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTag == 0 ? direction0Entity.getStops().size() : direction1Entity.getStops().size();
    }
}
