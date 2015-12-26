package com.iancuio.driftdirect.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.adapters.gridViewAdapters.OfficialSponsorsAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialSponsorsFragment extends Fragment {

    @Bind(R.id.gridView_officialSponsorsLayout_sponsors)
    GridView officialSponsorsGridView;

    Championship championshipFull;


    public OfficialSponsorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((ChampionshipNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_official_sponsors, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();

        getSponsors();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getSponsors() {
        officialSponsorsGridView.setAdapter(new OfficialSponsorsAdapter(getActivity(), championshipFull.getSponsors()));

        officialSponsorsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(championshipFull.getSponsors().get(position).getUrl()));
                startActivity(browserIntent);
            }
        });
    }

}
