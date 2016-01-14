package com.iancuio.driftdirect.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.adapters.listViewAdapters.ChampionshipsAdapter;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ChampionshipsActivity extends AppCompatActivity {

    @Bind(R.id.listView_championshipsActivityLayout_championshipsListView)
    ListView championshipsListView;

    ProgressDialog dialog;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championships);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_championshipsActivityLayout_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        getChampionships();
    }

    private void getChampionships() {

        dialog = ProgressDialog.show(this, "Loading", "Getting Championships...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        ChampionshipService championshipService;
        championshipService = retrofit.create(ChampionshipService.class);

        Call<List<ChampionshipShort>> championshipsCall = championshipService.getChampionshipsDetails();

        championshipsCall.enqueue(new retrofit.Callback<List<ChampionshipShort>>() {
            @Override
            public void onResponse(final Response<List<ChampionshipShort>> response, Retrofit retrofit) {
                championshipsListView.setAdapter(new ChampionshipsAdapter(ChampionshipsActivity.this, response.body()));
                dialog.dismiss();

                championshipsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ChampionshipsActivity.this, ChampionshipNavigationViewActivity.class);
                        bundle = new Bundle();
                        bundle.putSerializable("championship", response.body().get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("failure", t.toString());
                Utils.showAlertDialog(ChampionshipsActivity.this, "No internet!", "Please check your internet connection!", null);
            }
        });
    }
}
