package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.customObjects.person.Person;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        ChampionshipDriverParticipation championshipDriverParticipation = (ChampionshipDriverParticipation) bundle.getSerializable("driver");

        makerTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getMake());
        modelTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getModel());
        steeringAngleModsTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getSteeringAngle());
        suspensionModsTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getSuspensionMods());
        wheelsTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getWheels());
        tiresTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getTires());
        otherTextView.setText(championshipDriverParticipation.getDriver().getDriverDetails().getOther());
    }
}
