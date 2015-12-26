package com.iancuio.driftdirect.fragments;


import android.content.Context;
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
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
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


public class DriverProfileFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @Bind(R.id.sliderLayout_driverProfileLayout_carImagesSlider)
    SliderLayout mDemoSlider;
    @Bind(R.id.viewPager_driverProfileLayout_driverProfileDetails)
    ViewPager driverProfileDetailsViewPager;
    @Bind(R.id.tabLayout_driverProfileLayout_slidingTabs)
    TabLayout driverDetailsTabLayout;
    @Bind(R.id.imageView_driverProfileLayout_driverPicture)
    ImageView driverPictureImageView;
    @Bind(R.id.textView_driverProfileLayout_driverCarModel)
    TextView driverCarModelTextView;
    @Bind(R.id.textView_driverProfileLayout_driverCarModelHP)
    TextView driverCarModelHPTextView;
    @Bind(R.id.textView_driverProfileLayout_driverName)
    TextView driverNameTextView;
    @Bind(R.id.textView_driverProfileLayout_driverRank)
    TextView driverRankTextView;
    @Bind(R.id.textView_driverProfileLayout_driverAge)
    TextView driverAgeTextView;
    @Bind(R.id.textView_driverProfileLayout_driverSessionPoints)
    TextView driverSessionPointsTextView;
    @Bind(R.id.progressBar_driverProfileLayout_progressBar)
    ProgressBar driverImageProgressBar;


    private ScreenSlidePagerAdapter driverProfileDetailsPagerAdapter;
    private Bundle bundle;

    Championship championshipFull;




    public DriverProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_driver_profile, container, false);
        ButterKnife.bind(this, v);
        ((ChampionshipNavigationViewActivity) getActivity()).setCurrentFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();
        configureSlider();
        getDriver();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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

    private void configureSlider() {
        //        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal",R.drawable.hannibal);
//        file_maps.put("Big Bang Theory",R.drawable.bigbang);
//        file_maps.put("House of Cards",R.drawable.house);
//        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Drift", "http://www.speedhunters.com/wp-content/uploads/2011/12/1_IuB6_339.jpg");
        url_maps.put("Drift2", "http://www.sneakerfreaker.com/content/uploads/2013/11/insane-drifters-2014-drift-cars-1.jpg");
        //url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        //url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


        for(String name : url_maps.keySet()){
            DefaultSliderView sliderView = new DefaultSliderView(getActivity());
            // initialize a SliderLayout
            sliderView
                    //.description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);

            mDemoSlider.addSlider(sliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }



    private void initializeDriverDetailsViewPager(ChampionshipDriverParticipation championshipDriverParticipation) {
        List<Fragment> fragments = getFragmentsForViewPager(championshipDriverParticipation);
        String tabTitles[] = new String[] { "Biography", "Car Specs", "Sponsors" };

        // Instantiate a ViewPager and a PagerAdapter.
        driverProfileDetailsPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        driverProfileDetailsViewPager.setAdapter(driverProfileDetailsPagerAdapter);
        driverProfileDetailsViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        driverDetailsTabLayout.setupWithViewPager(driverProfileDetailsViewPager);
    }

    private List<Fragment> getFragmentsForViewPager(ChampionshipDriverParticipation championshipDriverParticipation) {
        List<Fragment> fList = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("driver", championshipDriverParticipation);

        DriversProfileBiographyFragment driversProfileBiographyFragment = new DriversProfileBiographyFragment();
        driversProfileBiographyFragment.setArguments(bundle);

        DriversProfileCarSpecsFragment driversProfileCarSpecsFragment = new DriversProfileCarSpecsFragment();
        driversProfileCarSpecsFragment.setArguments(bundle);

        DriversProfileSponsorsFragment driversProfileSponsorsFragment = new DriversProfileSponsorsFragment();
        driversProfileSponsorsFragment.setArguments(bundle);

        fList.add(driversProfileBiographyFragment);
        fList.add(driversProfileCarSpecsFragment);
        fList.add(driversProfileSponsorsFragment);
        return fList;
    }

    private void getDriver() {

        bundle = getArguments();
        Long personId = bundle.getLong("personId");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        ChampionshipService championshipService;
        championshipService = retrofit.create(ChampionshipService.class);

        Call<ChampionshipDriverParticipation> championshipDriverParticipationCall = championshipService.getDriver(((ChampionshipNavigationViewActivity) getActivity()).getSelectedChampionship().getId(), personId);

        championshipDriverParticipationCall.enqueue(new retrofit.Callback<ChampionshipDriverParticipation>() {
            @Override
            public void onResponse(Response<ChampionshipDriverParticipation> response, Retrofit retrofit) {
                ChampionshipDriverParticipation championshipDriverParticipation = response.body();
                driverCarModelTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getCarName());
                driverCarModelHPTextView.setText(String.valueOf(championshipDriverParticipation.getDriver().getDriverDetails().getHorsePower()) + " HP");
                driverNameTextView.setText(championshipDriverParticipation.getDriver().getFirstName() + " " + championshipDriverParticipation.getDriver().getLastName());
                driverRankTextView.setText(String.valueOf(championshipDriverParticipation.getResults().getRank()));
                driverAgeTextView.setText(String.valueOf(Years.yearsBetween(championshipDriverParticipation.getDriver().getBirthDate(), DateTime.now()).getYears()));
                driverSessionPointsTextView.setText(String.valueOf(championshipDriverParticipation.getResults().getTotalPoints()));

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

                initializeDriverDetailsViewPager(championshipDriverParticipation);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PersonResponse", t.toString());
            }
        });
    }
}
