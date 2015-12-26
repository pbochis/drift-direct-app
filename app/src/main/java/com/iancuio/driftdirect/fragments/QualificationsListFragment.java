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
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.customObjects.round.Round;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QualificationsListFragment extends Fragment {

    @Bind(R.id.tabLayout_qualificationListLayout_slidingTabs)
    TabLayout qualificationsTabLayout;
    @Bind(R.id.viewPager_qualificationListLayout_drivers)
    ViewPager qualificationViewPager;

    Round roundFull;

    private ScreenSlidePagerAdapter qualificationPagerAdapter;


    public QualificationsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_qualifications_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        initializeDriverDetailsViewPager();
    }

    private List<Fragment> getFragmentsForViewPager() {
        List<Fragment> fList = new ArrayList<Fragment>();

        QualificationsResultsListFragment qualificationsResultsListFragment = new QualificationsResultsListFragment();
        QualificationsDriversListFragment qualificationsDriversListFragment = new QualificationsDriversListFragment();

        fList.add(qualificationsResultsListFragment);
        fList.add(qualificationsDriversListFragment);
        return fList;
    }

    private void initializeDriverDetailsViewPager() {
        List<Fragment> fragments = getFragmentsForViewPager();
        String tabTitles[] = new String[] {"Results", "Qualification List"};

        // Instantiate a ViewPager and a PagerAdapter.
        qualificationPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        qualificationViewPager.setAdapter(qualificationPagerAdapter);
        qualificationViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        qualificationsTabLayout.setupWithViewPager(qualificationViewPager);
    }


}
