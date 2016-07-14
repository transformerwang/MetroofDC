package wyz.android.com.metroofdc.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import wyz.android.com.metroofdc.R;


class ViewHolderList{
        public ImageView mImageView;
        public TextView mTextView;
        }
class ViewHolderImage
{
    public ImageView imageMap;
}

/**
 * Created by wangyuzhe on 16/5/10.
 */
public class TrainListAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mLines = {"Blue", "Red", "Green", "Yellow", "Orange", "Silver"};
    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;

    public TrainListAdapter(Context context)
    {
        mContext = context;
    }


    @Override
    public int getCount() {
        return mLines.length + 1;
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
        ViewHolderList holder = null;
        ViewHolderImage holderImage = null;

        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ONE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_item_map, null);
                    holderImage = new ViewHolderImage();
                    holderImage.imageMap = (ImageView) convertView.findViewById(R.id.map_img);
                    convertView.setTag(holderImage);
                } else {
                    holderImage = (ViewHolderImage) convertView.getTag();
                }
                holderImage.imageMap.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_metro));
                break;
            case TYPE_TWO:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_item_metrolines, null);
                    holder = new ViewHolderList();
                    holder.mTextView = (TextView) convertView.findViewById(R.id.color_txt);
                    holder.mImageView = (ImageView) convertView.findViewById(R.id.color_img);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderList) convertView.getTag();
                }
                holder.mTextView.setText(mLines[position - 1]);
                int iconRec;
                switch (mLines[position - 1]) {
                    case "Green":
                        iconRec = R.drawable.ic_green;
                        break;
                    case "Red":
                        iconRec = R.drawable.ic_red;
                        break;
                    case "Blue":
                        iconRec = R.drawable.ic_blue;
                        break;
                    case "Silver":
                        iconRec = R.drawable.ic_silver;
                        break;
                    case "Orange":
                        iconRec = R.drawable.ic_orange;
                        break;
                    case "Yellow":
                        iconRec = R.drawable.ic_yellow;
                        break;
                    default:
                        iconRec = R.drawable.ic_red;
                        break;

                }
                holder.mImageView.setImageDrawable(mContext.getResources().getDrawable(iconRec));
                break;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
        {
            return TYPE_ONE;
        }
        else
        {
            return TYPE_TWO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
