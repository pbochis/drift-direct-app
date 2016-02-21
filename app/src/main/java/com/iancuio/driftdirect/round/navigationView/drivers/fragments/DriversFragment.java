package com.iancuio.driftdirect.round.navigationView.drivers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.championship.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.championship.navigationView.rankings.adapter.RankingsAdapter;
import com.iancuio.driftdirect.championship.navigationView.rankings.fragments.driversProfile.fragments.DriverProfileFragment;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriversFragment extends Fragment {

    @Bind(R.id.listView_driversFragmentLayout_driversList)
    ListView driversListView;

    ChampionshipShort selectedChampionship;
    Championship championshipFull;

    public DriversFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drivers, container, false);
        ButterKnife.bind(this, v);
//        ((ChampionshipNavigationViewActivity) getActivity()).setCurrentFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //selectedChampionship = ((ChampionshipNavigationViewActivity)getActivity()).getSelectedChampionship();
        championshipFull = ((RoundNavigationViewActivity)getActivity()).getChampionshipFull();

        getDrivers();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void getDrivers() {

        driversListView.setAdapter(new RankingsAdapter(getActivity(), championshipFull.getDrivers()));

        driversListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("personId", championshipFull.getDrivers().get(position).getDriver().getId());
                bundle.putInt("rank", position+1);
                DriverProfileFragment driverProfileFragment = new DriverProfileFragment();
                driverProfileFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, driverProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
