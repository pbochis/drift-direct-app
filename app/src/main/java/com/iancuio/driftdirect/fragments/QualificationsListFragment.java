package com.iancuio.driftdirect.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.w3c.dom.Text;

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
public class QualificationsListFragment extends Fragment {

    @Bind(R.id.tabLayout_qualificationListLayout_slidingTabs)
    TabLayout qualificationsTabLayout;
    @Bind(R.id.viewPager_qualificationListLayout_drivers)
    ViewPager qualificationViewPager;

    @Bind(R.id.button_qualificationListLayout_refreshButton)
    Button refreshButton;
    @Bind(R.id.imageView_qualificationListLayout_driverFlag)
    ImageView driverFlagImageView;
    @Bind(R.id.imageView_qualificationListLayout_driverImage)
    CircleImageView driverImageImageView;
    @Bind(R.id.textView_qualificationListLayout_driverName)
    TextView driverNameTextView;
    @Bind(R.id.textView_qualificationListLayout_driverAge)
    TextView driverAgeTextView;
    @Bind(R.id.textView_qualificationListLayout_driverCarModel)
    TextView driverCarModelTextView;
    @Bind(R.id.textView_qualificationListLayout_driverCarHp)
    TextView driverCarHpTextView;
    @Bind(R.id.textView_qualificationListLayout_driverSpeed)
    TextView driverSpeedTextView;
    @Bind(R.id.textView_qualificationListLayout_driverFirstRunPoints)
    TextView driverFirstRunPointsTextView;
    @Bind(R.id.textView_qualificationListLayout_driverSecondRunPoints)
    TextView driverSecondRunPointsTextView;
    @Bind(R.id.progressBar_qualificationListLayout_progressBar)
    ProgressBar driverPictureProgressBar;




    ProgressDialog dialog;

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
        if (qualificationPagerAdapter != null) {
            requestRoundUpdate();
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        if (qualificationPagerAdapter == null) {
            requestRoundUpdate();
        }

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
        qualificationPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), fragments, tabTitles);
        qualificationViewPager.setAdapter(null);
        qualificationViewPager.setAdapter(qualificationPagerAdapter);
        qualificationViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        qualificationsTabLayout.setupWithViewPager(qualificationViewPager);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initializeDriverDetailsViewPager();
//    }

    @OnClick(R.id.button_qualificationListLayout_refreshButton)
    public void refreshButtonClick() {
        requestRoundUpdate();
    }

    private void requestRoundUpdate() {
        dialog = ProgressDialog.show(getActivity(), "Loading", "Updating...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        RoundService roundService;
        roundService = retrofit.create(RoundService.class);

        final Call<Round> roundCall = roundService.getRound(((RoundNavigationViewActivity)getActivity()).getRoundId());

        roundCall.enqueue(new retrofit.Callback<Round>() {
            @Override
            public void onResponse(final Response<Round> response, Retrofit retrofit) {
                ((RoundNavigationViewActivity)getActivity()).setRoundFull(response.body());
                initializeDriverDetailsViewPager();

                if (roundFull.getCurrentDriver() != null) {
                    Utils.loadImage(200, 200, getActivity(), RestUrls.FILE + roundFull.getCurrentDriver().getDriver().getProfilePicture(), driverImageImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("succes", "image succes");
                            driverPictureProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Log.e("error", "imageError");
                        }
                    });

                    Utils.loadImage(100, 100, getActivity(), RestUrls.FILE + roundFull.getCurrentDriver().getDriver().getCountry(), driverFlagImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("succes", "image succes");
                        }

                        @Override
                        public void onError() {
                            Log.e("error", "imageError");
                        }
                    });

                    driverNameTextView.setText(roundFull.getCurrentDriver().getDriver().getFirstName() + " " + roundFull.getCurrentDriver().getDriver().getLastName());
                    driverAgeTextView.setText("Age: " + String.valueOf(Years.yearsBetween(roundFull.getCurrentDriver().getDriver().getBirthDate(), DateTime.now()).getYears()));
                    driverCarModelTextView.setText(roundFull.getCurrentDriver().getDriver().getDriverDetails().getMake() + " " + roundFull.getCurrentDriver().getDriver().getDriverDetails().getModel());
                    driverSpeedTextView.setText("-");

                    if (roundFull.getCurrentDriver().getFirstRunScore() != null && roundFull.getCurrentDriver().getFirstRunScore() != 0) {
                        driverFirstRunPointsTextView.setText(String.valueOf(roundFull.getCurrentDriver().getFirstRunScore()) + "P");
                    } else {
                        driverFirstRunPointsTextView.setText("-");
                    }

                    if (roundFull.getCurrentDriver().getSecondRunScore() != null && roundFull.getCurrentDriver().getSecondRunScore() != 0) {
                        driverSecondRunPointsTextView.setText(String.valueOf(roundFull.getCurrentDriver().getSecondRunScore()) + "P");
                    } else {
                        driverSecondRunPointsTextView.setText("-");
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
