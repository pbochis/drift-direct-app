package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapterCustomView;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.judge.ChampionshipJudgeParticipation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamOfJudgesFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.tabLayout_judginTeamLayout_judgesTabs)
    TabLayout judgesTabsTabLayout;
    @Bind(R.id.viewPager_judginTeamLayout_judgesViewPager)
    ViewPager judgesViewPager;

    private ScreenSlidePagerAdapterCustomView judgesPagerAdapter;
    Championship championshipFull;


    public TeamOfJudgesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((ChampionshipNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_team_of_judges, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();

        getJudges();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    private List<Fragment> getFragmentsForViewPager(List<ChampionshipJudgeParticipation> judgeParticipation) {
        List<Fragment> fList = new ArrayList<Fragment>();

        JudgeDetailsFragment firstJudgeDetailsFragment = new JudgeDetailsFragment();
        Bundle firstBundle = new Bundle();
        firstBundle.putInt("judgeNumber", 0);
        firstJudgeDetailsFragment.setArguments(firstBundle);
        fList.add(firstJudgeDetailsFragment);

        JudgeDetailsFragment secondJudgeDetailsFragment = new JudgeDetailsFragment();
        Bundle secondBundle = new Bundle();
        secondBundle.putInt("judgeNumber", 1);
        secondJudgeDetailsFragment.setArguments(secondBundle);
        fList.add(secondJudgeDetailsFragment);

        JudgeDetailsFragment thirdJudgeDetailsFragment = new JudgeDetailsFragment();
        Bundle thirdBundle = new Bundle();
        thirdBundle.putInt("judgeNumber", 2);
        thirdJudgeDetailsFragment.setArguments(thirdBundle);
        fList.add(thirdJudgeDetailsFragment);

        return fList;
    }



    private void getJudges() {
        initializeJudgesViewPager(championshipFull.getJudges());
    }

    private void initializeJudgesViewPager(List<ChampionshipJudgeParticipation> judgeParticipation) {
        List<Fragment> fragments = getFragmentsForViewPager(judgeParticipation);
        // Instantiate a ViewPager and a PagerAdapter.
        judgesPagerAdapter = new ScreenSlidePagerAdapterCustomView(getActivity().getSupportFragmentManager(), fragments, judgeParticipation);
        judgesViewPager.setAdapter(judgesPagerAdapter);
        judgesViewPager.setCurrentItem(0);
        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        judgesTabsTabLayout.setupWithViewPager(judgesViewPager);

        for (int i = 0; i < judgesTabsTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = judgesTabsTabLayout.getTabAt(i);
            tab.setCustomView(judgesPagerAdapter.getTabView(i, getActivity()));
        }

        judgesTabsTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setBorderSelectedColor(tab, getResources().getColor(R.color.colorChampionships));
                judgesViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setBorderUnselectedColor(tab, getResources().getColor(R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        judgesViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setBorderSelectedColor(TabLayout.Tab tab, int color) {
        RelativeLayout tabView = ((RelativeLayout) tab.getCustomView());
        CircleImageView judgeImageView = (CircleImageView) tabView.findViewById(R.id.imageView_judgesCustomTabLayout_judgePicture);
        judgeImageView.setBorderColor(color);
    }

    private void setBorderUnselectedColor(TabLayout.Tab tab, int color) {
        RelativeLayout tabView = ((RelativeLayout) tab.getCustomView());
        CircleImageView judgeImageView = (CircleImageView) tabView.findViewById(R.id.imageView_judgesCustomTabLayout_judgePicture);
        judgeImageView.setBorderColor(color);
    }




}
