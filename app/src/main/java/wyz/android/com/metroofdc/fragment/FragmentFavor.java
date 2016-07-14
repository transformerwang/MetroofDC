package wyz.android.com.metroofdc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import wyz.android.com.metroofdc.R;
import wyz.android.com.metroofdc.adpater.FavorViewpagerAdapter;

/**
 * Created by wangyuzhe on 1/6/16.
 */
public class FragmentFavor extends Fragment {
    @BindView(R.id.vp_favor)
    ViewPager vpFavor;
    private TabLayout tabLayout;
//    @BindView(R.id.recyclerView_favor_bus)
//    RecyclerView recyclerViewFavorBus;
//    @BindView(R.id.recyclerView_favor_train)
//    RecyclerView recyclerViewFavorTrain;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favor, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorites");
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        
        FavorViewpagerAdapter viewpagerAdapter = new FavorViewpagerAdapter(getChildFragmentManager());
        vpFavor.setAdapter(viewpagerAdapter);
        tabLayout.setupWithViewPager(vpFavor);
    }

    @Override
    public void onResume() {
        super.onResume();
        tabLayout.setVisibility(View.VISIBLE);
    }
}
