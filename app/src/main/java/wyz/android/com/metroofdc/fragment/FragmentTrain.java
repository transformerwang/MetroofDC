package wyz.android.com.metroofdc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.ButterKnife;
import wyz.android.com.metroofdc.activity.AlertMetroActivity;
import wyz.android.com.metroofdc.activity.MetroMapActivity;
import wyz.android.com.metroofdc.activity.TrainsStationActivity;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.TrainListAdapter;

/**
 * Created by wangyuzhe on 1/6/16.
 */
public class FragmentTrain extends ListFragment{
    private String[] mLines = {"Blue", "Red", "Green", "Yellow", "Orange", "Silver"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TrainListAdapter trainListAdapter = new TrainListAdapter(getActivity());
        setListAdapter(trainListAdapter);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Train");

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(position == 0)
        {
            Intent intent = new Intent(getActivity(), MetroMapActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getActivity(), TrainsStationActivity.class);
            intent.putExtra("line", mLines[position-1]);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_train, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_notice_train)
        {
            Intent intent = new Intent(getActivity(), AlertMetroActivity.class);
            startActivity(intent);
            return true;
        }
//        if(id == R.id.action_search_train)
//        {
////            Intent intent = new Intent(getActivity(), SearchTrainActivity.class);
////            startActivity(intent);
//            searchView.openSearch();
//            searchView.clearSuggestions();
//            String[] arrary = getResources().getStringArray(R.array.stations);
//            searchView.addSuggestions(arrary);
//        }
        return super.onOptionsItemSelected(item);
    }
}
