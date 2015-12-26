package com.iancuio.driftdirect.fragments;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.listViewAdapters.JudgePointsAllocationAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;
import com.iancuio.driftdirect.customObjects.round.Round;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgingScoresJudgeScores extends Fragment {

    @Bind(R.id.editText_judgingScoresJudgeScoresLayout_entrySpeed)
    EditText entrySpeedEditText;
    @Bind(R.id.textView_judgingScoresJudgeScoresLayout_judgeName)
    TextView judgeNameTextView;
    @Bind(R.id.listView_judgingScoresJudgeScoresLayout_pointsAllocationList)
    ListView pointsAllocationListListView;
    @Bind(R.id.textView_judgingScoresJudgeScoresLayout_judgeType)
    TextView judgeTypeTextView;

    Round roundFull;
    Championship championshipFull;


    public JudgingScoresJudgeScores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_judging_scores_judge_scores, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        roundFull = ((RoundNavigationViewActivity) getActivity()).getRoundFull();

        championshipFull = ((RoundNavigationViewActivity) getActivity()).getChampionshipFull();

        judgeNameTextView.setText(championshipFull.getJudges().get(0).getJudge().getFirstName());
        judgeTypeTextView.setText(championshipFull.getJudges().get(0).getJudgeType());

        getAllocationPoints();
        //Log.e("points", " " + ((JudgePointsAllocationAdapter)pointsAllocationListListView.getAdapter()).getPoints(0));
    }

    private void getAllocationPoints() {
        pointsAllocationListListView.setAdapter(new JudgePointsAllocationAdapter(getActivity(), championshipFull.getJudges().get(0).getPointsAllocations()));
    }
}
