package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.customObjects.round.Round;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubTop16Top32Fragment extends Fragment {

    Round roundFull;


    public SubTop16Top32Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_top16_top32, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        getDrivers();
    }

    private void getDrivers() {

    }
}
