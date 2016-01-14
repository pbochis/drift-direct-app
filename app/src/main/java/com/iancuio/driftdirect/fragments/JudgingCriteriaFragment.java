package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.utils.NullCheck;
import com.iancuio.driftdirect.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgingCriteriaFragment extends Fragment {

    @Bind(R.id.textView_judgingCriteriaFragment_judgingCriteria)
    TextView judgingCriteriaTextView;

    Round roundFull;

    public JudgingCriteriaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_judging_criteria, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        Utils.nullCheck(roundFull.getTrack().getJudgingCriteria(), new NullCheck() {
            @Override
            public void onNotNull() {
                judgingCriteriaTextView.setText(roundFull.getTrack().getJudgingCriteria());
            }

            @Override
            public void onNull() {
                judgingCriteriaTextView.setText("-");
            }
        });
        judgingCriteriaTextView.setText(roundFull.getTrack().getJudgingCriteria());
    }
}
