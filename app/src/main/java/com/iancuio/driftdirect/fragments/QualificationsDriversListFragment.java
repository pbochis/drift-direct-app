package com.iancuio.driftdirect.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.listViewAdapters.QualificationsDriversAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.temporary.NormalDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QualificationsDriversListFragment extends Fragment {

    @Bind(R.id.listView_qualificationsDriversListLayout_driversList)
    ListView qualificationsDriversListListView;

    Round roundFull;

    public QualificationsDriversListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qualifications_drivers_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        getDriversList();
    }

    public void getDriversList() {
        qualificationsDriversListListView.setAdapter(new QualificationsDriversAdapter(getActivity(), roundFull.getQualifiers()));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            Set<String> roles = sharedPreferences.getStringSet("roles", new HashSet<String>());

            if (roles.contains("ROLE_JUDGE")) {
                qualificationsDriversListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JudgingScoresFragment judgingScoresFragment = new JudgingScoresFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("driversList", "driversList");
                        bundle.putLong("qualifierId", roundFull.getQualifiers().get(position).getId());
                        judgingScoresFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, judgingScoresFragment)
                                .addToBackStack("judgingScoresFragment")
                                .commit();
                    }
                });
            }
        }
    }


}
