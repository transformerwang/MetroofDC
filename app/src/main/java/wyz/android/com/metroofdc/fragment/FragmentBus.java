package wyz.android.com.metroofdc.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import wyz.android.com.metroofdc.activity.AlertBusActivity;
import wyz.android.com.metroofdc.activity.BusStationActivity;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.activity.BusStationInfoActivity;
import wyz.android.com.metroofdc.adpater.BusStationAdapter;
import wyz.android.com.metroofdc.adpater.SuggestionAdapter;
import wyz.android.com.metroofdc.constant.Constant;
import wyz.android.com.metroofdc.domain.BusStations;
import wyz.android.com.metroofdc.domain.BusStop;
import wyz.android.com.metroofdc.listener.LoadDataListener;
import wyz.android.com.metroofdc.util.MetroInfoAsynctask;


/**
 * Created by wangyuzhe on 1/6/16.
 */
public class FragmentBus extends ListFragment implements LoadDataListener{
    private List<BusStop.StopsBean> list;
    private ArrayList<String> mStopId;
    private boolean mIsBusRoute;
    private ArrayList<String> Routes;
    private ArrayList<String> RoutesAll;
    private List<String> RouteId;
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete search;
    private FragmentTabHost tabHost;
    private MenuItem searchItem;
    private List<String> StationName;
    private List<String> lat;
    private List<String> lon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("a","created");
        mIsBusRoute = false;

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bus");

        //stations = getResources().getStringArray(R.array.stations);
        lanuchTaskStation();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), BusStationActivity.class);
        String[] data = Routes.get(position).split("-");
        intent.putExtra("routeId", RouteId.get(position));
        intent.putExtra("name", data[1] + data[2]);
        startActivity(intent);
        mSearchView.setIconified(true);
        MenuItemCompat.collapseActionView(searchItem);
        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_bus, menu);
        searchItem = menu.findItem(R.id.action_search_bus);
        mSearchView = (SearchView) searchItem.getActionView();
        search = (SearchView.SearchAutoComplete)mSearchView.findViewById(R.id.search_src_text);
        search.setThreshold(5);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                Log.e("a", query);
                if(search.getThreshold() == 2)
                {
                    int pos = Routes.indexOf(query);
                    String[] data = query.split("-");
                    Intent intent = new Intent(getActivity(), BusStationActivity.class);
                    intent.putExtra("routeId", RouteId.get(pos));
                    intent.putExtra("name", data[1] + data[2]);
                    startActivity(intent);
                }
                else
                {
                    int pos = mStopId.indexOf(query);
                    Intent in = new Intent(getActivity(), BusStationInfoActivity.class);
                    in.putExtra("stopId", query.split("\\n")[1]);
                    in.putExtra("stationName",query.split("\\n")[0]);
                    in.putExtra("lat", lat.get(pos));
                    in.putExtra("lon", lon.get(pos));
                    startActivity(in);
                }
                mSearchView.setIconified(true);
                MenuItemCompat.collapseActionView(searchItem);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });

        tabHost = (FragmentTabHost)getActivity().findViewById(android.R.id.tabhost);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                tabHost.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                tabHost.setVisibility(View.VISIBLE);
                return true;
            }
        });
       super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_notice_bus)
        {
            Intent intent = new Intent(getActivity(), AlertBusActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_search_bus)
        {
            final ArrayList templist;

            if(RoutesAll != null) {
                templist = (ArrayList) RoutesAll.clone();
            }
            else
            {
                templist = null;
            }

            mIsBusRoute = true;

            String[] option = new String[]{"Routes ID or Name", "STOP ID NO."};
            Dialog dilaog = new AlertDialog.Builder(getActivity()).
                    setTitle("Choose your search category").
                    setItems(option, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which)
                            {
                                case 0:
                                    search.setThreshold(2);
                                    //Log.e("a", String.valueOf(RoutesAll.size()));
                                    search.setAdapter(new SuggestionAdapter(getActivity(), R.layout.tab_suggestion_dropdown, templist));
                                    break;
                                case 1:
                                    lanuchTask();
                                    search.setThreshold(5);
                                    break;
                            }
                        }
                    }).create();
            dilaog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getData(String[] data) {
        if(!mIsBusRoute)
        {
            Gson gson = new Gson();
            BusStations busStations = gson.fromJson(data[0], BusStations.class);
            if(busStations.getRoutes() != null) {
                RoutesAll = new ArrayList<>();
                Routes = new ArrayList<>();
                RouteId = new ArrayList<>();

                for (int i = 0; i < busStations.getRoutes().size(); i++) {
                    Routes.add(busStations.getRoutes().get(i).getName());
                    RouteId.add(busStations.getRoutes().get(i).getRouteID());
                    RoutesAll.add(busStations.getRoutes().get(i).getName());
                }
                search.setThreshold(2);
                search.setAdapter(new SuggestionAdapter(getActivity(), R.layout.tab_suggestion_dropdown, RoutesAll));
                BusStationAdapter busStationAdapter = new BusStationAdapter(getActivity(), Routes);
                setListAdapter(busStationAdapter);
            }
        }
        else
        {
            Gson gson1 = new Gson();
            BusStop bus = gson1.fromJson(data[0], BusStop.class);
            if(bus.getStops() != null) {
                list = new ArrayList<>();
                mStopId = new ArrayList<>();
                //StationName = new ArrayList<>();
                lat = new ArrayList<>();
                lon = new ArrayList<>();
                list = bus.getStops();

                for (int i = 0; i < list.size(); i++) {
                    mStopId.add(list.get(i).getName() + "\n" + list.get(i).getStopID());
                    //StationName.add(list.get(i).getName());
                    lat.add(String.valueOf(list.get(i).getLat()));
                    lon.add(String.valueOf(list.get(i).getLon()));
                }
                search.setAdapter(new SuggestionAdapter(getActivity(), R.layout.tab_suggestion_dropdown, mStopId));
            }
        }
    }

    @Override
    public void getDataFail() {
        Toast.makeText(getActivity(), "Please check you Internet connection!", Toast.LENGTH_SHORT).show();
    }

    public void lanuchTaskStation()
    {
        try {
            URL url = new URL(Constant.BUS_URL + "/jRoutes" + Constant.METRO_API_KEY);
            MetroInfoAsynctask metroInfoAsynctask = new MetroInfoAsynctask(getActivity());
            metroInfoAsynctask.execute(url);
            metroInfoAsynctask.setmLoadDataListener(this);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void lanuchTask(){
        try {
//            URL url = new URL(Constant.BUS_URL + "/jRoutes" + Constant.METRO_API_KEY);
            URL url1 = new URL(Constant.BUS_STOP + Constant.METRO_API_KEY);
            MetroInfoAsynctask metroInfoAsynctask = new MetroInfoAsynctask(getActivity());
            metroInfoAsynctask.execute(url1);
            metroInfoAsynctask.setmLoadDataListener(this);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //searchView.clearAll();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                searchView.addSuggestions(Routes);
//            }
//        }).start();
    }
}
