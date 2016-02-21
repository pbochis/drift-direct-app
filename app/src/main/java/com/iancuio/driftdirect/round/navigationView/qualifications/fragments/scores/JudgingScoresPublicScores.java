package com.iancuio.driftdirect.round.navigationView.qualifications.fragments.scores;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.publicBattle.adapters.PublicRunDetailsAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.Qualifier;
import com.iancuio.driftdirect.service.QualifierService;
import com.iancuio.driftdirect.utils.RestUrls;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgingScoresPublicScores extends Fragment {

    @Bind(R.id.listView_publicScoresJudgeScoresLayout_runDetails)
    ListView runDetailsListView;
    @Bind(R.id.textView_publicScoresJudgeScoresLayout_entrySpeed)
    TextView entrySpeedTextView;
    @Bind(R.id.textView_publicScoresJudgeScoresLayout_totalPoints)
    TextView totalPointsTextView;

    Bundle bundle;

    Round roundFull;
    ProgressDialog dialog;

    public JudgingScoresPublicScores() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_judging_scores_public_scores, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();
        getPublicRunDetails();
    }

    private void getPublicRunDetails() {

        dialog = ProgressDialog.show(getActivity(), "Loading", "Getting info...");

        bundle = getArguments();
        Long personId = bundle.getLong("qualifierId");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        QualifierService qualifierService;
        qualifierService = retrofit.create(QualifierService.class);

        Call<Qualifier> qualifierCall = qualifierService.getQualifier(personId);

        qualifierCall.enqueue(new retrofit.Callback<Qualifier>() {
            @Override
            public void onResponse(Response<Qualifier> response, Retrofit retrofit) {
                Qualifier qualifier = response.body();

                if (getArguments().getString("run1") != null) {
                    runDetailsListView.setAdapter(new PublicRunDetailsAdapter(getActivity(), qualifier.getFirstRun(), getView()));
                    entrySpeedTextView.setText(String.valueOf(qualifier.getFirstRun().getEntrySpeed()));
                    totalPointsTextView.setText(String.valueOf(qualifier.getFirstRun().getTotalPoints()));
                } else {
                    runDetailsListView.setAdapter(new PublicRunDetailsAdapter(getActivity(), qualifier.getSecondRun(), getView()));
                    entrySpeedTextView.setText(String.valueOf(qualifier.getSecondRun().getEntrySpeed()));
                    totalPointsTextView.setText(String.valueOf(qualifier.getSecondRun().getTotalPoints()));
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PersonResponse", t.toString());
            }
        });
    }
}
