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
import com.iancuio.driftdirect.customObjects.championship.Championship;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgeDetailsFragment extends Fragment {

    @Bind(R.id.textView_judgeDetailsFragment_judgeName)
    TextView judgeName;
    @Bind(R.id.textView_judgeDetailsFragment_judgeType)
    TextView judgeType;
    @Bind(R.id.textView_judgeDetailsFragment_judgeDescription)
    TextView judgeDescription;

    Championship championshipFull;

    public JudgeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_judge_details, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();

        Bundle bundle = getArguments();
        int judgeNumber = bundle.getInt("judgeNumber");

        judgeName.setText(championshipFull.getJudges().get(judgeNumber).getJudge().getFirstName() + " " + championshipFull.getJudges().get(judgeNumber).getJudge().getLastName());
        judgeType.setText(championshipFull.getJudges().get(judgeNumber).getJudgeType());
        judgeDescription.setText(championshipFull.getJudges().get(judgeNumber).getJudge().getDescription());


    }
}
