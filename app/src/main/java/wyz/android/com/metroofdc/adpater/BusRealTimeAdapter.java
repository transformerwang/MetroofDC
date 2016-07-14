package wyz.android.com.metroofdc.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.domain.BusRealTime;


/**
 * Created by wangyuzhe on 16/5/22.
 */
public class BusRealTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private BusRealTime mBus;

    public BusRealTimeAdapter(BusRealTime bus) {
        this.mBus = bus;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_route_id)
        TextView textRouteId;
        @BindView(R.id.text_bus_direction)
        TextView textBusDirection;
        @BindView(R.id.txt_bus_time)
        TextView textTime;

        public DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_bus_realtime, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder newHolder = (DataViewHolder) holder;
        if(mBus.getPredictions().get(position) != null)
        {
            newHolder.textRouteId.setText(mBus.getPredictions().get(position).getRouteID());
            newHolder.textBusDirection.setText(mBus.getPredictions().get(position).getDirectionText());
            newHolder.textTime.setText(String.valueOf(mBus.getPredictions().get(position).getMinutes()) + "min");
        }
    }

    @Override
    public int getItemCount() {
        return mBus.getPredictions() == null ? 0 : mBus.getPredictions().size();
    }
}
