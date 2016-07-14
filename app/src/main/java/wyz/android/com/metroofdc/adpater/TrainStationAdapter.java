package wyz.android.com.metroofdc.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wyz.android.com.metroofdc.activity.TrainStationsInfoActivity;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.activity.TrainsStationActivity;

/**
 * Created by wangyuzhe on 16/5/14.
 */
public class TrainStationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mcontext;
    private List<String> mStations = new ArrayList<>();
    private List<String> mColors = new ArrayList<>();
    private List<String> mLineCode = new ArrayList<>();
    private List<String> mCordinate = new ArrayList<>();
    private int temp;

    public TrainStationAdapter(Context context, List<String> stations, List<String> colors, List<String> linecode, List<String> cordiante, int stemp)
    {
        this.mcontext = context;
        this.mStations = stations;
        this.mColors = colors;
        this.mLineCode = linecode;
        this.mCordinate = cordiante;
        this.temp = stemp;
    }


    public static class DataViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView stationName;
        public LinearLayout linearLayout;
        public Context context;
        public List<String> linecode;
        public List<String> stations;
        public List<String> colorLine;
        public List<String> cordiantes;
        public int temps;
        public OnItemClickListener ClickListener;
//        public ImageView imageView_1;
//        public ImageView imageView_2;
//        public ImageView imageView_3;
//        public ImageView imageView_4;
//        public ImageView imageView_5;
//        public ImageView imageView_6;
        public DataViewHolder(View itemview, Context scontext, List<String> slinecode , List<String> sStations, List<String> color, List<String> cordinate, int tep)
        {
            super(itemview);
            itemview.setOnClickListener(this);
            itemview.setOnLongClickListener(this);
            stationName = (TextView) itemview.findViewById(R.id.tv_st_name);
            linearLayout = (LinearLayout) itemview.findViewById(R.id.linear);
            context = scontext;
            linecode = slinecode;
            stations = sStations;
            colorLine = color;
            cordiantes = cordinate;
            temps = tep;
        }

        public void setOnItemClickListener(OnItemClickListener itemClickListener)
        {
            this.ClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            if(temps == 0)
            {
                Intent intents = new Intent(context, TrainStationsInfoActivity.class);
                intents.putExtra("lineCode", linecode.get(getLayoutPosition()));
                intents.putExtra("stationName", stations.get(getLayoutPosition()));
                intents.putExtra("color", colorLine.get(getLayoutPosition()));
                intents.putExtra("cordinate", cordiantes.get(getLayoutPosition()));
                context.startActivity(intents);
                ((TrainsStationActivity)context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
            else
            {
                Intent intent = new Intent(context, TrainStationsInfoActivity.class);
                intent.putExtra("lineCode", linecode.get(getLayoutPosition()));
                intent.putExtra("stationName", stations.get(getLayoutPosition()));
                intent.putExtra("color", colorLine.get(getLayoutPosition()));
                intent.putExtra("cordinate", cordiantes.get(getLayoutPosition()));
                context.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(temps == 1) {
                ClickListener.onItemClick(v, getPosition());
            }
            return true;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_trainstations, parent, false);
        return new DataViewHolder(view, mcontext, mLineCode, mStations, mColors, mCordinate, temp);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataViewHolder newHolder = (DataViewHolder)holder;
        newHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("Do you want to delete this station?");
                builder.setTitle("Reminder");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mLineCode.remove(position);
                        mStations.remove(position);
                        mColors.remove(position);
                        mCordinate.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mLineCode.size());
                        mcontext.deleteFile("favorTrain.txt");
                        try {
                            FileOutputStream fos = mcontext.openFileOutput("favorTrain.txt", Context.MODE_APPEND);
                            for (int i = 0; i < mLineCode.size(); i++) {
                                fos.write((mLineCode.get(i) + "_" + mStations.get(i) + "_" + mColors.get(i) + "_" + mCordinate.get(i) + "\n").getBytes());
                            }
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

            }
        });
        newHolder.linearLayout.removeAllViews();
        setImage(position, newHolder);
        newHolder.stationName.setText(mStations.get(position));
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
            ImageView imageView = new ImageView(mcontext);
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

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
