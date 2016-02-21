package com.iancuio.driftdirect.round.navigationView.drivers.driversProfile.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iancuio.driftdirect.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriversProfileSponsorsFragment extends Fragment {


    public DriversProfileSponsorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drivers_profile_sponsors, container, false);
    }
}
