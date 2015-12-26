package com.iancuio.driftdirect.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.adapters.listViewAdapters.DriversAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;

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
        ((ChampionshipNavigationViewActivity) getActivity()).setCurrentFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedChampionship = ((ChampionshipNavigationViewActivity)getActivity()).getSelectedChampionship();
        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();
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

//        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();
//
//        ChampionshipService championshipService;
//        championshipService = retrofit.create(ChampionshipService.class);
//
//        Call<List<PersonShort>> championshipsCall = championshipService.getDrivers(selectedChampionship.getId());
//
//        championshipsCall.enqueue(new retrofit.Callback<List<PersonShort>>() {
//            @Override
//            public void onResponse(final Response<List<PersonShort>> response, Retrofit retrofit) {
//                if (response.code() == 200) {
        driversListView.setAdapter(new DriversAdapter(getActivity(), championshipFull.getDrivers()));

        driversListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("personId", championshipFull.getDrivers().get(position).getId());
                DriverProfileFragment driverProfileFragment = new DriverProfileFragment();
                driverProfileFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.linearcontainer, driverProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
//                } else {
//                    Toast.makeText(getActivity(), "Something went wrong " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("failure", t.toString());
//
//            }
//        });
//    }
    }
}