package com.iancuio.driftdirect.round.navigationView.eventSchedule.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.round.navigationView.eventSchedule.adapter.EventScheduleAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import org.joda.time.DateTime;
import org.joda.time.Years;

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
public class EventScheduleFragment extends Fragment {

    @Bind(R.id.listView_eventScheduleFragmentLayout_scheduleList)
    ListView scheduleListListView;

    Round roundFull;

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

    public EventScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_event_schedule, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();
        getSchedule();
        requestRoundUpdate();
    }

    private void getSchedule() {
        scheduleListListView.setAdapter(new EventScheduleAdapter(getActivity(), roundFull.getSchedule()));
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
                roundFull = ((RoundNavigationViewActivity) getActivity()).getRoundFull();

                if (roundFull.getCurrentDriver() != null) {
                    ImageUtils.loadNormalImage(200, 200, getActivity(), RestUrls.FILE + roundFull.getCurrentDriver().getDriver().getProfilePicture(), driverImageImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("succes", "image succes");
                            driverPictureProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Log.e("error", "imageError");
                            driverPictureProgressBar.setVisibility(View.GONE);
                        }
                    });

                    ImageUtils.loadNormalImage(100, 100, getActivity(), RestUrls.FILE + roundFull.getCurrentDriver().getDriver().getCountry(), driverFlagImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("succes", "image succes");
                        }

                        @Override
                        public void onError() {
                            Log.e("error", "imageError");
                        }
                    });

                    Utils.nullCheck(roundFull.getCurrentDriver().getDriver().getFirstName(), new NullCheck() {
                        @Override
                        public void onNotNull() {
                            driverNameTextView.setText(roundFull.getCurrentDriver().getDriver().getFirstName() + " " + roundFull.getCurrentDriver().getDriver().getLastName());

                        }

                        @Override
                        public void onNull() {
                            driverNameTextView.setText("-");
                        }
                    });

                    Utils.nullCheck(roundFull.getCurrentDriver().getDriver().getBirthDate(), new NullCheck() {
                        @Override
                        public void onNotNull() {
                            driverAgeTextView.setText("Age: " + String.valueOf(Years.yearsBetween(roundFull.getCurrentDriver().getDriver().getBirthDate(), DateTime.now()).getYears()));
                        }

                        @Override
                        public void onNull() {
                            driverAgeTextView.setText("-");
                        }
                    });

                    Utils.nullCheck(roundFull.getCurrentDriver().getDriver().getDriverDetails().getMake(), new NullCheck() {
                        @Override
                        public void onNotNull() {
                            driverCarModelTextView.setText(roundFull.getCurrentDriver().getDriver().getDriverDetails().getMake() + " " + roundFull.getCurrentDriver().getDriver().getDriverDetails().getModel());
                        }

                        @Override
                        public void onNull() {
                            driverCarModelTextView.setText("-");
                        }
                    });
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
                } else {
                    driverPictureProgressBar.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.button_qualificationListLayout_refreshButton)
    public void refreshButtonClick() {
        requestRoundUpdate();
        getSchedule();
    }
}
