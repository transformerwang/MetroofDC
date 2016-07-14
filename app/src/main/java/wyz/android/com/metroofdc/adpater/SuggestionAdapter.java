package wyz.android.com.metroofdc.adpater;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangyuzhe on 16/7/3.
 */
public class SuggestionAdapter extends ArrayAdapter<String> {
    private ArrayList<String> items;
    private ArrayList<String> itemsAll;
    private ArrayList<String> suggest;
    private ArrayFilter mFilter;

    public SuggestionAdapter(Context context, int resource, ArrayList<String> item) {
        super(context, resource, item);
        this.items = item;
        if(item != null) {
            this.itemsAll = (ArrayList<String>) item.clone();
        }
        suggest = new ArrayList<>();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public Filter getFilter() {
        if(mFilter == null)
        {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public class ArrayFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           if(constraint != null)
           {
               suggest.clear();

               for(String temp : itemsAll)
               {
                    if(temp.toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        suggest.add(temp);
                    }
               }

               FilterResults results = new FilterResults();
               results.values = suggest;
               results.count = suggest.size();
               return results;
           }
           else
           {
               return new FilterResults();
           }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> array = (ArrayList<String>) results.values;
            ArrayList<String> list = new ArrayList<String>();
            if(results != null && results.count > 0)
            {
                clear();
                for(String item : array)
                {
                    list.add(item);
                }
                Iterator<String> iterator = list.iterator();

                while(iterator.hasNext())
                {
                    String str = iterator.next();
                    add(str);
                }
                notifyDataSetChanged();
            }
        }
    }
}
