package com.iancuio.driftdirect.championship.navigationView.rankings.fragments.driversProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriversProfileCarSpecsFragment extends Fragment {

    @Bind(R.id.textView_driverCarSpecsLayout_maker)
    TextView makerTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_model)
    TextView modelTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_engineSpecs)
    TextView engineSpecsTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_steeringAngleMods)
    TextView steeringAngleModsTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_suspensionMods)
    TextView suspensionModsTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_wheels)
    TextView wheelsTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_tires)
    TextView tiresTextView;
    @Bind(R.id.textView_driverCarSpecsLayout_other)
    TextView otherTextView;

    public DriversProfileCarSpecsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_drivers_profile_car_specs, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDriverCarsSpecs();
    }

    private void getDriverCarsSpecs() {
        Bundle bundle = this.getArguments();
        final ChampionshipDriverParticipation championshipDriverParticipation = (ChampionshipDriverParticipation) bundle.getSerializable("driver");

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getMake(), new NullCheck() {
            @Override
            public void onNotNull() {
                makerTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getMake());

            }

            @Override
            public void onNull() {
                makerTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getModel(), new NullCheck() {
            @Override
            public void onNotNull() {
                modelTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getModel());

            }

            @Override
            public void onNull() {
                modelTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getSteeringAngle(), new NullCheck() {
            @Override
            public void onNotNull() {
                steeringAngleModsTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getSteeringAngle());

            }

            @Override
            public void onNull() {
                steeringAngleModsTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getSuspensionMods(), new NullCheck() {
            @Override
            public void onNotNull() {
                suspensionModsTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getSuspensionMods());

            }

            @Override
            public void onNull() {
                suspensionModsTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getWheels(), new NullCheck() {
            @Override
            public void onNotNull() {
                wheelsTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getWheels());

            }

            @Override
            public void onNull() {
                wheelsTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getTires(), new NullCheck() {
            @Override
            public void onNotNull() {
                tiresTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getTires());

            }

            @Override
            public void onNull() {
                tiresTextView.setText("-");
            }
        });

        Utils.nullCheck(championshipDriverParticipation.getDriver().getDriverDetails().getOther(), new NullCheck() {
            @Override
            public void onNotNull() {
                otherTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getOther());

            }

            @Override
            public void onNull() {
                otherTextView.setText("-");
            }
        });
    }
}
