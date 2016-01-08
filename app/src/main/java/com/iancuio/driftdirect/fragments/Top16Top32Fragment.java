package com.iancuio.driftdirect.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.JudgeBattleActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.playoffs.PlayoffTreeGraphicDisplay;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Top16Top32Fragment extends Fragment {

    @Bind(R.id.tabLayout_top16Top32Layout_slidingTabs)
    TabLayout top16Top32TabLayout;
    @Bind(R.id.viewPager_top16Top32Layout_drivers)
    ViewPager top16Top32ViewPager;
    @Bind(R.id.relativeLayout_top16Top32Layout_battleLauncher)
    RelativeLayout battleLauncher;
    @Bind(R.id.imageView_top16Top32Layout_firstDriverFlag)
    ImageView firstDriverFlag;
    @Bind(R.id.textView_top16Top32Layout_firstDriverName)
    TextView firstDriverName;
    @Bind(R.id.imageView_top16Yop32Layout_firstBadgePicture)
    CircleImageView firstDriverPicture;
    @Bind(R.id.progressBar_top16Yop32Layout_firstBadgePictureProgressBar)
    ProgressBar firstDriverPictureProgressBar;
    @Bind(R.id.textView_top16Yop32Layout_firstBadgeText)
    TextView firstDriverStatus;
    @Bind(R.id.textView_top16Yop32Layout_firstBadgeOrder)
    TextView firstDriverQualificationOrder;
    @Bind(R.id.textView_top16Top32Layout_firstDriverCarModel)
    TextView firstDriverCarModel;
    @Bind(R.id.textView_top16Top32Layout_firstDriverCarHP)
    TextView firstDriverCarHP;

    @Bind(R.id.imageView_top16Top32Layout_secondDriverFlag)
    ImageView secondDriverFlag;
    @Bind(R.id.textView_top16Top32Layout_secondDriverName)
    TextView secondDriverName;
    @Bind(R.id.imageView_top16Yop32Layout_secondBadgePicture)
    CircleImageView secondDriverPicture;
    @Bind(R.id.progressBar_top16Yop32Layout_secondBadgePictureProgressBar)
    ProgressBar secondDriverPictureProgressBar;
    @Bind(R.id.textView_top16Yop32Layout_secondBadgeText)
    TextView secondDriverStatus;
    @Bind(R.id.textView_top16Yop32Layout_secondBadgeOrder)
    TextView secondDriverQualificationOrder;
    @Bind(R.id.textView_top16Top32Layout_secondDriverCarModel)
    TextView secondDriverCarModel;
    @Bind(R.id.textView_top16Top32Layout_secondDriverCarHP)
    TextView secondDriverCarHP;


    private ScreenSlidePagerAdapter top16Top32PagerAdapter;


    Round roundFull;

    ProgressDialog dialog;

    Bundle bundle;


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
        if (top16Top32PagerAdapter != null) {
            getPlayoffs();
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        if (top16Top32PagerAdapter == null) {
            getPlayoffs();
        }

    }

    private List<Fragment> getFragmentsForViewPager(PlayoffTreeGraphicDisplay playoffTreeGraphicDisplay) {
        List<Fragment> fList = new ArrayList<Fragment>();

        SubTop16Top32Fragment top24 = new SubTop16Top32Fragment();
        bundle = new Bundle();
        if (playoffTreeGraphicDisplay.getStages().get(0) != null) {
            bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(0));
            bundle.putLong("topNumber", 24);
            top24.setArguments(bundle);
        }

        SubTop16Top32Fragment top16 = new SubTop16Top32Fragment();
        bundle = new Bundle();
        if (playoffTreeGraphicDisplay.getStages().get(1) != null) {
            bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(1));
            bundle.putLong("topNumber", 16);
            top16.setArguments(bundle);
        }

        SubTop16Top32Fragment top8 = new SubTop16Top32Fragment();
        bundle = new Bundle();
        if (playoffTreeGraphicDisplay.getStages().get(2) != null) {
            bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(2));
            bundle.putLong("topNumber", 8);
            top8.setArguments(bundle);
        }

        SubTop16Top32Fragment top4 = new SubTop16Top32Fragment();
        bundle = new Bundle();
        if (playoffTreeGraphicDisplay.getStages().get(3) != null) {
            bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(3));
            bundle.putLong("topNumber", 4);
            top4.setArguments(bundle);
        }

        SubTop16Top32Fragment finals = new SubTop16Top32Fragment();
        bundle = new Bundle();
        if (playoffTreeGraphicDisplay.getStages().get(4) != null) {
            bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(4));
            bundle.putLong("topNumber", 0);
            finals.setArguments(bundle);
        }

        QualificationsResultsListFragment results = new QualificationsResultsListFragment();
        bundle = new Bundle();
        if (playoffTreeGraphicDisplay.getRoundResults() != null) {
            bundle.putSerializable("results", (Serializable) playoffTreeGraphicDisplay.getRoundResults());
            results.setArguments(bundle);
        }

        fList.add(top24);
        fList.add(top16);
        fList.add(top8);
        fList.add(top4);
        fList.add(finals);
        fList.add(results);
        return fList;
    }

    private void initializeDriverDetailsViewPager(PlayoffTreeGraphicDisplay playoffTreeGraphicDisplay) {
        List<Fragment> fragments = getFragmentsForViewPager(playoffTreeGraphicDisplay);
        String tabTitles[] = new String[] {"Top 24", "Top 16", "Top 8", "Top 4", "Finals", "Results"};

        // Instantiate a ViewPager and a PagerAdapter.

        top16Top32PagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        top16Top32ViewPager.setAdapter(top16Top32PagerAdapter);
        top16Top32ViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        top16Top32TabLayout.setupWithViewPager(top16Top32ViewPager);
    }

    @OnClick(R.id.relativeLayout_top16Top32Layout_battleLauncher)
    public void battleLauncherClick() {
        Intent intent = new Intent(getActivity(), JudgeBattleActivity.class);
        startActivity(intent);
    }

    public void getPlayoffs() {
        dialog = ProgressDialog.show(getActivity(), "Loading", "Getting playoffs...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        RoundService roundService;
        roundService = retrofit.create(RoundService.class);

        long roundId = roundFull.getId();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "token");
        Call<PlayoffTreeGraphicDisplay> playoffTreeGraphicDisplayCall = roundService.getPlayoffs(token, roundId);

        playoffTreeGraphicDisplayCall.enqueue(new retrofit.Callback<PlayoffTreeGraphicDisplay>() {
            @Override
            public void onResponse(final Response<PlayoffTreeGraphicDisplay> response, Retrofit retrofit) {
                PlayoffTreeGraphicDisplay playoffTreeGraphicDisplay = response.body();
                initializeDriverDetailsViewPager(playoffTreeGraphicDisplay);

                if (playoffTreeGraphicDisplay.getCurrentBattle() != null) {
                    Utils.loadImage(200, 200, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getProfilePicture(), firstDriverPicture, new Callback() {
                        @Override
                        public void onSuccess() {
                            firstDriverPictureProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

                    firstDriverName.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getFirstName());

                    Utils.loadImage(100, 100, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getCountry(), firstDriverFlag, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {

                        }
                    });

                    firstDriverQualificationOrder.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getRanking()));
                    //viewHolder.firstDriverStatus
                    firstDriverCarModel.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getMake() + " " + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getModel());
                    firstDriverCarHP.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getHorsePower()));

                    Utils.loadImage(200, 200, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getProfilePicture(), secondDriverPicture, new Callback() {
                        @Override
                        public void onSuccess() {
                            secondDriverPictureProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

                    secondDriverName.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getFirstName());

                    Utils.loadImage(100, 100, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getCountry(), secondDriverFlag, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });

                    secondDriverQualificationOrder.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getRanking()));
                    //viewHolder.firstDriverStatus
                    secondDriverCarModel.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getMake() + " " + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getModel());
                    secondDriverCarHP.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getHorsePower()));
                    dialog.dismiss();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("top16top32", t.toString());
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (top16Top32PagerAdapter != null) {
            top16Top32PagerAdapter = null;
            getPlayoffs();
        }
    }
}
