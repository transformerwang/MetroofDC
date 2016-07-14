package wyz.android.com.metroofdc.adpater;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.activity.BusStationInfoActivity;

/**
 * Created by wangyuzhe on 16/6/9.
 */
public class BusFavorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> mStopId = new ArrayList<>();
    private List<String> mStopName = new ArrayList<>();
    private List<String> mCordiante = new ArrayList<>();
    private Context mContext;

    public BusFavorAdapter(List<String> id, List<String> name, List<String> cordinate, Context context)
    {
        this.mStopId = id;
        this.mStopName = name;
        this.mCordiante = cordinate;
        this.mContext = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textName;
        public TextView textId;
        public List<String> stopName;
        public List<String> stopId;
        public List<String> cordiantes;
        public OnItemClickListener mItemClick;

        public ViewHolder(View itemView, List<String> mStopName, List<String> mStopId, List<String> cordinate) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_stop_name);
            textId = (TextView) itemView.findViewById(R.id.text_stop_id);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            stopName = mStopName;
            stopId = mStopId;
            cordiantes = cordinate;
        }

        public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
            this.mItemClick = mItemClickListener;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), BusStationInfoActivity.class);
            intent.putExtra("stopId", stopId.get(getLayoutPosition()));
            intent.putExtra("stationName", stopName.get(getLayoutPosition()));
            intent.putExtra("lat", cordiantes.get(getLayoutPosition()).split(",")[0]);
            intent.putExtra("lon", cordiantes.get(getLayoutPosition()).split(",")[1]);
            v.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            mItemClick.onItemClick(v, getPosition());
            return true;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_bus_detail, parent, false);
        return new ViewHolder(v, mStopName, mStopId, mCordiante);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder newHolder = (ViewHolder)holder;
        newHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                new android.app.AlertDialog.Builder(mContext).setMessage("Do you want to delete this station?")
                        .setTitle("Reminder").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mStopName.remove(position);
                        mStopId.remove(position);
                        mCordiante.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mStopId.size());
                        mContext.deleteFile("favorBus.txt");
                        try {
                            FileOutputStream fos = mContext.openFileOutput("favorBus.txt", Context.MODE_APPEND);
                            for(int i = 0; i< mStopId.size(); i++)
                            {
                                fos.write((mStopId.get(i) + "_" + mStopName.get(i) + "_" + mCordiante.get(i) + "\n").getBytes());
                            }
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();


            }
        });
        newHolder.textId.setText(mStopId.get(position));
        newHolder.textName.setText(mStopName.get(position));
    }

    @Override
    public int getItemCount() {
        return mStopId.size();
    }


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

}
