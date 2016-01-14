package com.iancuio.driftdirect.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;


import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.listViewAdapters.RoundsAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.round.Round;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    @Bind(R.id.listView_calendarFragmentLayout_roundsList)
    ListView roundsListView;

    ChampionshipShort selectedChampionship;
    Championship championshipFull;


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);
        ((ChampionshipNavigationViewActivity) getActivity()).setCurrentFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        selectedChampionship = ((ChampionshipNavigationViewActivity)getActivity()).getSelectedChampionship();
        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();
        getRounds();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getRounds() {
                roundsListView.setAdapter(new RoundsAdapter(getActivity(), championshipFull.getRounds()));

                roundsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putLong("roundId", championshipFull.getRounds().get(position).getId());
                        bundle.putLong("roundImageId", championshipFull.getRounds().get(position).getLogo());
                        bundle.putSerializable("championshipFull", championshipFull);
                        Intent intent = new Intent(getActivity(), RoundNavigationViewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
    }


}
