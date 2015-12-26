package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.listViewAdapters.QualificationsResultsAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.QualifierShort;
import com.iancuio.driftdirect.customObjects.temporary.ResultsDriver;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QualificationsResultsListFragment extends Fragment {

    @Bind(R.id.listView_qualificationsResultListLayout_qualificationsResultList)
    ListView qualificationsResultListListView;

    Round roundFull;


    public QualificationsResultsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qualifications_results_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        getResultsList();
    }

    public void getResultsList() {

        List<QualifierShort> judgedQualifiersList = new ArrayList<>();

        for (int i = 0; i<roundFull.getQualifiers().size(); i++) {
            if (roundFull.getQualifiers().get(i).getPoints() != null) {
                judgedQualifiersList.add(roundFull.getQualifiers().get(i));
            }
        }
        qualificationsResultListListView.setAdapter(new QualificationsResultsAdapter(getActivity(), judgedQualifiersList));
        //qualificationsResultListListView.setEmptyView();


    }


}
