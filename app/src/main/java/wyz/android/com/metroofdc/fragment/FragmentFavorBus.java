package wyz.android.com.metroofdc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.BusFavorAdapter;

/**
 * Created by wangyuzhe on 16/6/10.
 */
public class FragmentFavorBus extends Fragment {
    @BindView(R.id.recyclerView_favor_bus)
    RecyclerView recyclerViewFavorBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favor_bus, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<String> mStopId = new ArrayList<>();
        List<String> mName = new ArrayList<>();
        List<String> mCordinate = new ArrayList<>();

        //Bus
        try {
            File fileBus = new File(getActivity().getApplicationContext().getFilesDir(), "favorBus.txt");
            //Log.e("b", getApplicationContext().getFilesDir().toString());
            if (!fileBus.exists()) {
                try {
                    fileBus.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileInputStream fisbus = getContext().openFileInput("favorBus.txt");
            InputStreamReader isrbus = new InputStreamReader(fisbus, "UTF-8");
            BufferedReader brbus = new BufferedReader(isrbus);
            String linebus = "";
            while ((linebus = brbus.readLine()) != null) {
                String[] info = linebus.split("_");
                mStopId.add(info[0]);
                mName.add(info[1]);
                mCordinate.add(info[2]);
            }
            brbus.close();
            isrbus.close();
            fisbus.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager layoutManagerBus = new LinearLayoutManager(getActivity());
        recyclerViewFavorBus.setLayoutManager(layoutManagerBus);
        BusFavorAdapter adapter = new BusFavorAdapter(mStopId, mName, mCordinate, getActivity());
        recyclerViewFavorBus.setAdapter(adapter);
    }
}
