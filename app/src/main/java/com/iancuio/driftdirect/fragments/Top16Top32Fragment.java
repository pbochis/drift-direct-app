package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.round.Round;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Top16Top32Fragment extends Fragment {

    @Bind(R.id.tabLayout_top16Top32Layout_slidingTabs)
    TabLayout top16Top32TabLayout;
    @Bind(R.id.viewPager_top16Top32Layout_drivers)
    ViewPager top16Top32ViewPager;

    private ScreenSlidePagerAdapter top16Top32PagerAdapter;


    Round roundFull;


    public Top16Top32Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_top16_top32, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();


    }

    private List<Fragment> getFragmentsForViewPager() {
        List<Fragment> fList = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();

        SubTop16Top32Fragment top32 = new SubTop16Top32Fragment();
        bundle = new Bundle();

        SubTop16Top32Fragment top16 = new SubTop16Top32Fragment();
        bundle = new Bundle();

        SubTop16Top32Fragment top8 = new SubTop16Top32Fragment();
        bundle = new Bundle();

        SubTop16Top32Fragment finals = new SubTop16Top32Fragment();
        bundle = new Bundle();

        SubTop16Top32Fragment results = new SubTop16Top32Fragment();
        bundle = new Bundle();

        fList.add(top32);
        fList.add(top16);
        fList.add(top8);
        fList.add(finals);
        fList.add(results);
        return fList;
    }

    private void initializeDriverDetailsViewPager() {
        List<Fragment> fragments = getFragmentsForViewPager();
        String tabTitles[] = new String[] {"Top 32", "Top 16", "Top 8"};

        // Instantiate a ViewPager and a PagerAdapter.

        top16Top32PagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        top16Top32ViewPager.setAdapter(top16Top32PagerAdapter);
        top16Top32ViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        top16Top32TabLayout.setupWithViewPager(top16Top32ViewPager);
    }
}
