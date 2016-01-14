package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackLayoutFragment extends Fragment {

    @Bind(R.id.tabLayout_trackLayoutLayout_slidingTabs)
    TabLayout trackLayoutTabLayout;
    @Bind(R.id.viewPager_trackLayoutLayout_trackDetailsViewPager)
    ViewPager trackLayoutViewPager;
    @Bind(R.id.imageView_trackLayoutLayout_trackImage)
    ImageView trackImageImageView;
    @Bind(R.id.progressBar_trackLayoutLayout_progressBar)
    ProgressBar trackImageProgressBar;

    private ScreenSlidePagerAdapter trackLayoutPagerAdapter;

    Round roundFull;
    Long roundImageId;


    public TrackLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_track_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();
        roundImageId = ((RoundNavigationViewActivity)getActivity()).getRoundImageId();
        getTrack();

        Utils.loadNormalImage(600, 600, getActivity(), RestUrls.FILE + roundImageId, trackImageImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                trackImageProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
                trackImageProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initializeTrackLayoutViewPager() {
        List<Fragment> fragments = getFragmentsForViewPager();
        String tabTitles[] = new String[] { "TRACK LAYOUT", "JUDGING CRITERIA"};

        // Instantiate a ViewPager and a PagerAdapter.
        trackLayoutPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        trackLayoutViewPager.setAdapter(trackLayoutPagerAdapter);
        trackLayoutViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        trackLayoutTabLayout.setupWithViewPager(trackLayoutViewPager);
    }

    private List<Fragment> getFragmentsForViewPager() {
        List<Fragment> fList = new ArrayList<Fragment>();

        SubTrackLayoutFragment subTrackLayoutFragment = new SubTrackLayoutFragment();
        JudgingCriteriaFragment judgingCriteriaFragment = new JudgingCriteriaFragment();

        fList.add(subTrackLayoutFragment);
        fList.add(judgingCriteriaFragment);
        return fList;
    }

    private void getTrack() {
        initializeTrackLayoutViewPager();
    }
}
