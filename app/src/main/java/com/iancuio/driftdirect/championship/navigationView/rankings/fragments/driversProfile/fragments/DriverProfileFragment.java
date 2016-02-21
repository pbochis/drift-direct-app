package com.iancuio.driftdirect.championship.navigationView.rankings.fragments.driversProfile.fragments;


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
import com.iancuio.driftdirect.championship.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.sharedAdapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

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
    @Bind(R.id.textView_driverProfileLayout_driverSeasonPoints)
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
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        championshipFull = ((RoundNavigationViewActivity)getActivity()).getChampionshipFull();
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

    private void configureSlider(ChampionshipDriverParticipation championshipDriverParticipation) {
        HashMap<String,String> url_maps = new HashMap<String, String>();

        if (championshipDriverParticipation.getDriver().getGallery() != null) {
            for (int i = 0; i<championshipDriverParticipation.getDriver().getGallery().size(); i++) {
                url_maps.put("Drift"+i, RestUrls.FILE + championshipDriverParticipation.getDriver().getGallery().get(i));
            }

            for (String name : url_maps.keySet()) {
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

        Call<ChampionshipDriverParticipation> championshipDriverParticipationCall = championshipService.getDriver(((RoundNavigationViewActivity) getActivity()).getChampionshipFull().getId(), personId);

        championshipDriverParticipationCall.enqueue(new retrofit.Callback<ChampionshipDriverParticipation>() {
            @Override
            public void onResponse(Response<ChampionshipDriverParticipation> response, Retrofit retrofit) {
                final ChampionshipDriverParticipation championshipDriverParticipation = response.body();
                configureSlider(championshipDriverParticipation);

                Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getCarName(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getMake(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                driverCarModelTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getCarName());
                            }

                            @Override
                            public void onNull() {
                                driverCarModelTextView.setText("-");
                            }
                        });

                    }

                    @Override
                    public void onNull() {
                        driverCarModelTextView.setText("-");
                    }
                });

                Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverCarModelHPTextView.setText(String.valueOf(championshipDriverParticipation.getDriver().getDriverDetails().getHorsePower()) + " HP");

                    }

                    @Override
                    public void onNull() {
                        driverCarModelHPTextView.setText("- HP");
                    }
                });

                Utils.nullCheck(championshipDriverParticipation.getDriver().getFirstName(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverNameTextView.setText(championshipDriverParticipation.getDriver().getFirstName() + " " + championshipDriverParticipation.getDriver().getLastName());

                    }

                    @Override
                    public void onNull() {
                        driverNameTextView.setText("-");
                    }
                });
                    Utils.nullCheck(getArguments().getInt("rank"), new NullCheck() {
                        @Override
                        public void onNotNull() {
                                driverRankTextView.setText(String.valueOf(getArguments().getInt("rank")));
                        }

                        @Override
                        public void onNull() {
                            driverRankTextView.setText("-");
                        }
                    });

                Utils.nullCheck(championshipDriverParticipation.getDriver().getBirthDate(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverAgeTextView.setText(String.valueOf(Years.yearsBetween(championshipDriverParticipation.getDriver().getBirthDate(), DateTime.now()).getYears()));

                    }

                    @Override
                    public void onNull() {
                        driverAgeTextView.setText("-");

                    }
                });

                Utils.nullCheck(championshipDriverParticipation.getResults(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        if (championshipDriverParticipation.getResults().getTotalPoints() != null || championshipDriverParticipation.getResults().getTotalPoints() != 0) {
                            driverSessionPointsTextView.setText(String.valueOf(championshipDriverParticipation.getResults().getTotalPoints()));
                        } else {
                            driverSessionPointsTextView.setText("-");
                        }
                    }

                    @Override
                    public void onNull() {
                        driverSessionPointsTextView.setText("-");
                    }
                });

                ImageUtils.loadNormalImage(200, 200, getActivity(), RestUrls.FILE + championshipDriverParticipation.getDriver().getProfilePicture(), driverPictureImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("succes", "image succes");
                        driverImageProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Log.e("error", "imageError");
                        driverImageProgressBar.setVisibility(View.GONE);
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
