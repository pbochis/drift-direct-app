package com.iancuio.driftdirect.round.navigationView.qualifications.fragments.scores;


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

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.sharedAdapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.Qualifier;
import com.iancuio.driftdirect.service.QualifierService;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.ArrayList;
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
public class JudgingScoresFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

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
    @Bind(R.id.imageView_judgingScoresLayout_driverFlag)
    ImageView driverFlagImageView;

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

        roundFull = ((RoundNavigationViewActivity) getActivity()).getRoundFull();
        getDriver();
    }

    private void initializeDriverDetailsViewPager() {
        List<Fragment> fragments = getFragmentsForViewPager();
        String tabTitles[];

        if (getArguments().getString("driversList") == null) {
            tabTitles = new String[]{"RUN 1", "RUN 2"};
        } else {
            tabTitles = new String[]{"RUN 1"};
        }

        // Instantiate a ViewPager and a PagerAdapter.
        driverRunDetailsPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), fragments, tabTitles);
        driverRunDetailsViewPager.setAdapter(driverRunDetailsPagerAdapter);
        driverRunDetailsViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        driverRunDetailsTabLayout.setupWithViewPager(driverRunDetailsViewPager);
    }

    private List<Fragment> getFragmentsForViewPager() {

        List<Fragment> fList = new ArrayList<Fragment>();

        if (getArguments().getString("driversList") == null) {
            JudgingScoresPublicScores judgingScoresPublicScoresRun1 = new JudgingScoresPublicScores();
            Bundle run1 = new Bundle();
            run1.putString("run1", "run1");
            run1.putLong("qualifierId", getArguments().getLong("qualifierId"));
            judgingScoresPublicScoresRun1.setArguments(run1);

            JudgingScoresPublicScores judgingScoresPublicScoresRun2 = new JudgingScoresPublicScores();
            Bundle run2 = new Bundle();
            run2.putString("run2", "run2");
            run2.putLong("qualifierId", getArguments().getLong("qualifierId"));
            judgingScoresPublicScoresRun2.setArguments(run2);

            fList.add(judgingScoresPublicScoresRun1);
            fList.add(judgingScoresPublicScoresRun2);
            return fList;
        } else {
            JudgingScoresJudgeScores judgingScoresJudgeScoresFragmentRun1 = new JudgingScoresJudgeScores();
            Bundle run = new Bundle();
            run.putLong("qualifierId", getArguments().getLong("qualifierId"));
            judgingScoresJudgeScoresFragmentRun1.setArguments(run);

            fList.add(judgingScoresJudgeScoresFragmentRun1);
            return fList;
        }
    }

    private void getDriver() {

        bundle = getArguments();
        Long qualifierId = bundle.getLong("qualifierId");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        QualifierService qualifierService;
        qualifierService = retrofit.create(QualifierService.class);

        Call<Qualifier> qualifierCall = qualifierService.getQualifier(qualifierId);

        qualifierCall.enqueue(new retrofit.Callback<Qualifier>() {
            @Override
            public void onResponse(Response<Qualifier> response, Retrofit retrofit) {
                final Qualifier qualifier = response.body();

                Utils.nullCheck(qualifier.getDriver().getDriverDetails().getModel(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverCarModelTextView.setText(qualifier.getDriver().getDriverDetails().getModel());

                    }

                    @Override
                    public void onNull() {
                        driverCarModelTextView.setText("-");
                    }
                });

                Utils.nullCheck(qualifier.getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverCarModelHPTextView.setText(String.valueOf(qualifier.getDriver().getDriverDetails().getHorsePower()) + " HP");

                    }

                    @Override
                    public void onNull() {
                        driverCarModelHPTextView.setText("-");
                    }
                });

                Utils.nullCheck(qualifier.getDriver().getFirstName(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverNameTextView.setText(qualifier.getDriver().getFirstName() + " " + qualifier.getDriver().getLastName());

                    }

                    @Override
                    public void onNull() {
                        driverNameTextView.setText("-");
                    }
                });


                Utils.nullCheck(qualifier.getDriver().getDriverDetails().getTeam(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverTeamTextView.setText(qualifier.getDriver().getDriverDetails().getTeam().getName());

                    }

                    @Override
                    public void onNull() {
                        driverTeamTextView.setText("-");

                    }
                });

                Utils.nullCheck(qualifier.getDriver().getBirthDate(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverAgeTextView.setText(String.valueOf(Years.yearsBetween(qualifier.getDriver().getBirthDate(), DateTime.now()).getYears()));

                    }

                    @Override
                    public void onNull() {
                        driverTeamTextView.setText("-");

                    }
                });

                Utils.nullCheck(qualifier.getFinalScore(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        driverSessionPointsTextView.setText(String.valueOf(qualifier.getFinalScore()));

                    }

                    @Override
                    public void onNull() {
                        driverSessionPointsTextView.setText("-");
                    }
                });

                ImageUtils.loadNormalImage(200, 200, getActivity(), RestUrls.FILE + qualifier.getDriver().getProfilePicture(), driverPictureImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        driverImageProgressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        driverImageProgressBar.setVisibility(View.GONE);

                    }
                });

                ImageUtils.loadNormalImage(100, 100, getActivity(), RestUrls.FILE + qualifier.getDriver().getCountry(), driverFlagImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

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
