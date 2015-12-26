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
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgingScoresFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    @Bind(R.id.sliderLayout_judgingScoresLayout_carImagesSlider)
    SliderLayout mDemoSlider;
    @Bind(R.id.viewPager_judgingScoresLayout_runDetails)
    ViewPager driverRunDetailsViewPager;
    @Bind(R.id.tabLayout_judgingScoresLayout_slidingTabs)
    TabLayout driverRunDetailsTabLayout;
    @Bind(R.id.imageView_judgingScoresLayout_driverPicture)
    ImageView driverPictureImageView;
    @Bind(R.id.textView_judgingScoresLayout_driverCarModel)
    TextView driverCarModelTextView;
    @Bind(R.id.textView_judgingScoresLayout_driverCarModelHP)
    TextView driverCarModelHPTextView;
    @Bind(R.id.textView_judgingScoresLayout_driverName)
    TextView driverNameTextView;
    @Bind(R.id.textView_judgingScoresLayout_driverTeam)
    TextView driverTeamTextView;
    @Bind(R.id.textView_judgingScoresLayout_driverAge)
    TextView driverAgeTextView;
    @Bind(R.id.textView_judgingScoresLayout_driverSessionPoints)
    TextView driverSessionPointsTextView;
    @Bind(R.id.progressBar_judgingScoresLayout_progressBar)
    ProgressBar driverImageProgressBar;


    private ScreenSlidePagerAdapter driverRunDetailsPagerAdapter;
    private Bundle bundle;

    Round roundFull;


    public JudgingScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_judging_scores, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();
        configureSlider();
        getDriver();
    }

    private void configureSlider() {

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Drift", "http://www.speedhunters.com/wp-content/uploads/2011/12/1_IuB6_339.jpg");
        url_maps.put("Drift2", "http://www.sneakerfreaker.com/content/uploads/2013/11/insane-drifters-2014-drift-cars-1.jpg");


        for(String name : url_maps.keySet()){
            DefaultSliderView sliderView = new DefaultSliderView(getActivity());
            // initialize a SliderLayout
            sliderView
                    //.description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            mDemoSlider.addSlider(sliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }



    private void initializeDriverDetailsViewPager() {
        List<Fragment> fragments = getFragmentsForViewPager();
        String tabTitles[] = new String[] { "RUN 1", "RUN 2"};

        // Instantiate a ViewPager and a PagerAdapter.
        driverRunDetailsPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        driverRunDetailsViewPager.setAdapter(driverRunDetailsPagerAdapter);
        driverRunDetailsViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        driverRunDetailsTabLayout.setupWithViewPager(driverRunDetailsViewPager);
    }

    private List<Fragment> getFragmentsForViewPager() {
        List<Fragment> fList = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();

        JudgingScoresJudgeScores judgingScoresJudgeScoresFragmentRun1 = new JudgingScoresJudgeScores();
        JudgingScoresJudgeScores judgingScoresJudgeScoresFragmentRun2 = new JudgingScoresJudgeScores();


        fList.add(judgingScoresJudgeScoresFragmentRun1);
        fList.add(judgingScoresJudgeScoresFragmentRun2);
        return fList;
    }

    private void getDriver() {

        bundle = getArguments();
        Long personId = bundle.getLong("personId");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        ChampionshipService championshipService;
        championshipService = retrofit.create(ChampionshipService.class);

        Call<ChampionshipDriverParticipation> championshipDriverParticipationCall = championshipService.getDriver(((RoundNavigationViewActivity) getActivity()).getChampionshipFull().getId(), personId);

        championshipDriverParticipationCall.enqueue(new retrofit.Callback<ChampionshipDriverParticipation>() {
            @Override
            public void onResponse(Response<ChampionshipDriverParticipation> response, Retrofit retrofit) {
                ChampionshipDriverParticipation championshipDriverParticipation = response.body();
                driverCarModelTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getCarName());
                driverCarModelHPTextView.setText(String.valueOf(championshipDriverParticipation.getDriver().getDriverDetails().getHorsePower()) + " HP");
                driverNameTextView.setText(championshipDriverParticipation.getDriver().getFirstName() + " " + championshipDriverParticipation.getDriver().getLastName());
                //driverTeamTextView.setText(String.valueOf(championshipDriverParticipation.()));
                driverAgeTextView.setText(String.valueOf(Years.yearsBetween(championshipDriverParticipation.getDriver().getBirthDate(), DateTime.now()).getYears()));
                //driverSessionPointsTextView.setText(String.valueOf(championshipDriverParticipation.getResults().getTotalPoints()));

                Picasso.with(getActivity()).load(RestUrls.FILE + championshipDriverParticipation.getDriver().getProfilePicture()).noPlaceholder().into(driverPictureImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("driverProfilePicture", "e.x.c.e.l.e.n.t");
                        driverImageProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Log.e("driverProfilePicture", "PROSTULE");
                    }
                });

                initializeDriverDetailsViewPager();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PersonResponse", t.toString());
            }
        });
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
}
