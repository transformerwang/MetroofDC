package wyz.android.com.metroofdc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import wyz.android.com.metroofdc.adpater.TrainStationAdapter;

/**
 * Created by wangyuzhe on 16/6/10.
 */
public class FragmentFavorTrain extends Fragment {
    @BindView(R.id.recyclerView_favor_train)
    RecyclerView recyclerViewFavorTrain;

    List<String> mStations;
    List<String> mColors;
    List<String> mLineCode;
    List<String> mCordinate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favor_train, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        RecyclerView.LayoutManager layoutManagerTrain = new LinearLayoutManager(getActivity());
//        recyclerViewFavorTrain.setLayoutManager(layoutManagerTrain);
//        TrainStationAdapter trainStationAdapter = new TrainStationAdapter(getActivity(), mStations, mColors, mLineCode, getFragmentManager(), 1);
//        recyclerViewFavorTrain.setAdapter(trainStationAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("a", "activity");
        mStations = new ArrayList<>();
        mColors = new ArrayList<>();
        mLineCode = new ArrayList<>();
        mCordinate = new ArrayList<>();
        try {
            //Train
            File fileTrain = new File(getActivity().getApplicationContext().getFilesDir(), "favorTrain.txt");
            if (!fileTrain.exists()) {
                try {
                    fileTrain.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileInputStream fis = getContext().openFileInput("favorTrain.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                String[] temp = line.split("_");
                mLineCode.add(temp[0]);
                mStations.add(temp[1]);
                mColors.add(temp[2]);
                mCordinate.add(temp[3]);
            }
            br.close();
            isr.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManagerTrain = new LinearLayoutManager(getActivity());
        recyclerViewFavorTrain.setLayoutManager(layoutManagerTrain);
        TrainStationAdapter trainStationAdapter = new TrainStationAdapter(getActivity(), mStations, mColors, mLineCode, mCordinate, 1);
        recyclerViewFavorTrain.setAdapter(trainStationAdapter);
    }
}
