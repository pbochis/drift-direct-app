package com.iancuio.driftdirect.round.navigationView.top32_16.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.JudgeBattleActivity;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.publicBattle.PublicBattleActivity;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.round.navigationView.top32_16.adapters.Top16Top32Adapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.playoffs.BattleGraphicDisplay;
import com.iancuio.driftdirect.customObjects.round.playoffs.PlayoffStageGraphicDisplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubTop16Top32Fragment extends Fragment {

    @Bind(R.id.listView_subTop16Top32Layout_driversList)
    ListView playoffsListListView;

    Round roundFull;

    PlayoffStageGraphicDisplay playoffStageGraphicDisplay;
    long topNumber;

    public SubTop16Top32Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_top16_top32, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();



        getDrivers();
    }

    private void getDrivers() {
        if (getArguments() != null) {

            playoffStageGraphicDisplay = (PlayoffStageGraphicDisplay) getArguments().getSerializable("playoffStage");
            topNumber = getArguments().getLong("topNumber");

            final List<BattleGraphicDisplay> battles = new ArrayList<>();
            for (BattleGraphicDisplay battle: playoffStageGraphicDisplay.getBattles()){
                if (battle.getDriver1() != null && battle.getDriver2() != null){
                    battles.add(battle);
                }
            }
            if (battles.size() != 0) {
                playoffsListListView.setAdapter(new Top16Top32Adapter(getActivity(), battles));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    playoffsListListView.setNestedScrollingEnabled(true);
                }
            }
            playoffsListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (battles.get(position).getWinner() != null) {
                        Intent intent = new Intent(getActivity(), PublicBattleActivity.class);
                        intent.putExtra("battleId", battles.get(position).getId());
                        intent.putExtra("topNumber", topNumber);
                        startActivity(intent);
                    } else {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
                        if (sharedPreferences != null) {
                            Set<String> roles = sharedPreferences.getStringSet("roles", new HashSet<String>());
                            if (roles.contains("ROLE_JUDGE")) {
                                Intent intent = new Intent(getActivity(), JudgeBattleActivity.class);
                                intent.putExtra("battleId", battles.get(position).getId());
                                intent.putExtra("token", sharedPreferences.getString("token", "token"));
                                intent.putExtra("topNumber", topNumber);
                                startActivity(intent);
                            }
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "No playoffs yet!", Toast.LENGTH_SHORT).show();
        }
    }
}
