package com.iancuio.driftdirect.championship.navigationView.rankings.fragments.driversProfile.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriversProfileBiographyFragment extends Fragment {

    @Bind(R.id.textView_driverProfileBiographyLayout_countryName)
    TextView countryNameTextView;
    @Bind(R.id.imageView_driverProfileBiographyLayout_countryFlag)
    ImageView countryFlagImageView;
    @Bind(R.id.textView_driverProfileBiographyLayout_team)
    TextView teamTextView;
    @Bind(R.id.textView_driverProfileBiographyLayout_driftingFrom)
    TextView driftingFromTextView;
    @Bind(R.id.textView_driverProfileBiographyLayout_portofolio)
    TextView portofolioTextView;
    @Bind(R.id.textView_driverProfileBiographyLayout_description)
    TextView descriptionTextView;


    public DriversProfileBiographyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drivers_profile_biography, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDriverBiography();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getDriverBiography() {
        Bundle bundle = this.getArguments();
        final ChampionshipDriverParticipation championshipDriverParticipation = (ChampionshipDriverParticipation) bundle.getSerializable("driver");

//        Utils.nullCheck(championshipDriverParticipation.getDriver().getCountry().getName(), new NullCheck() {
//            @Override
//            public void onNotNull() {
//
//            }
//
//            @Override
//            public void onNull() {
//
//            }
//        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getCountry(), new NullCheck() {
            @Override
            public void onNotNull() {
                countryNameTextView.setText(championshipDriverParticipation.getDriver().getCountry().getName());

            }

            @Override
            public void onNull() {
                countryNameTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getTeam(), new NullCheck() {
            @Override
            public void onNotNull() {
                teamTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getTeam().getName());

            }

            @Override
            public void onNull() {
                teamTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getCareerStartDate(), new NullCheck() {
            @Override
            public void onNotNull() {
                driftingFromTextView.setText(String.valueOf(championshipDriverParticipation.getDriver().getCareerStartDate().year().get()));

            }

            @Override
            public void onNull() {
                driftingFromTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getPortfolio(), new NullCheck() {
            @Override
            public void onNotNull() {
                portofolioTextView.setText(championshipDriverParticipation.getDriver().getPortfolio());

            }

            @Override
            public void onNull() {
                portofolioTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDescription(), new NullCheck() {
            @Override
            public void onNotNull() {
                descriptionTextView.setText(championshipDriverParticipation.getDriver().getDescription());

            }

            @Override
            public void onNull() {
                descriptionTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getCountry(), new NullCheck() {
            @Override
            public void onNotNull() {
                Picasso.with(getActivity()).load(RestUrls.FILE + championshipDriverParticipation.getDriver().getCountry().getFlag()).noPlaceholder().into(countryFlagImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
            }

            @Override
            public void onNull() {

            }
        });

    }
}
