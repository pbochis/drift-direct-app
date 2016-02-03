package com.iancuio.driftdirect.round.navigationView.buyTicketsOnline;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.customObjects.round.Round;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyTicketsOnlineFragment extends Fragment {

    Round roundFull;


    public BuyTicketsOnlineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        return inflater.inflate(R.layout.fragment_buy_tickets_online, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();
    }
}
