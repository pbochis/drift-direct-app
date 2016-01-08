package com.iancuio.driftdirect.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.fragments.BuyTicketsOnlineFragment;
import com.iancuio.driftdirect.fragments.EventScheduleFragment;
import com.iancuio.driftdirect.fragments.QualificationsListFragment;
import com.iancuio.driftdirect.fragments.Top16Top32Fragment;
import com.iancuio.driftdirect.fragments.TrackLayoutFragment;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.RestUrls;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RoundNavigationViewActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_roundNavigationViewLayout_feed)
    Toolbar feedToolbar;
    @Bind(R.id.toolbar_roundNavigationViewLayout_navigationDrawerTitle)
    Toolbar navigationDrawerTitleToolbar;
    @Bind(R.id.navigationView_roundNavigationViewLayout_navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawer_roundNavigationViewLayout_drawer)
    DrawerLayout drawerLayout;
    @Bind(R.id.textView_roundNavigationViewLayout_navigationDrawerTitle)
    TextView navigationDrawerTitleTextView;

    public Fragment currentFragment;
    ProgressDialog dialog;

    public static Round roundFull;
    public static Long roundImageId;



    private static Long roundId;



    private static Championship championshipFull;



    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_navigation_view);
        ButterKnife.bind(this);


        setSupportActionBar(feedToolbar);

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.championship_actionbar_layout,
                null);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout, params);

        Toolbar parent = (Toolbar) actionBarLayout.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        setRoundImageId(getIntent().getExtras().getLong("roundImageId"));
        setChampionshipFull((Championship) getIntent().getExtras().getSerializable("championshipFull"));
        getFullRound();
        //Initialising toolbar.
        //Setting toolbar as Actionbar.


        //Initialising NavigationView
        //Setting OnNavigationItemSelectedListener to the Navigation View.
        //This is used to perform specific action when an item is clicked.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Navigation View must close when any of this item is clicked.
                //To do this we use the closeDrawers() method.
                drawerLayout.closeDrawers();

                //Using switch case to identify the ID of the menu item
                // and then performing relevant action.
                switch (menuItem.getItemId()){
                    case R.id.track_layout:
                        setFragment(new TrackLayoutFragment(), R.string.track_layout);
                        return true;
                    case R.id.qualifications_list:
                        setFragment(new QualificationsListFragment(), R.string.qualifications_list);
                        return true;
                    case R.id.top_16_32:
                        setFragment(new Top16Top32Fragment(), R.string.top_16_32);
                        return true;
                    case R.id.event_schedule:
                        setFragment(new EventScheduleFragment(), R.string.event_schedule);
                        return true;
                    case R.id.live_stream:
                        //setFragment(new LiveStreamFragment(), R.string.live_stream);
                        if (isLiveStreamUrl()) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(roundFull.getLiveStream()));
                            startActivity(browserIntent);
                        }
                        return true;
                    case R.id.buy_tickets_online_rounds:
                        setFragment(new BuyTicketsOnlineFragment(), R.string.buy_tickets_online_rounds);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }

            }
        });

        //navigationView.setItemIconTintList(null);

        //Initialising DrawerLayout.
        //Initialising ActionBarDrawerToggle and overriding its methods
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,navigationDrawerTitleToolbar,R.string.open_drawer,R.string.close_drawer){
            //We can perform a particular action when the
            // Navigation View is opened by overriding the
            // onDrawerOpened() method.
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            //We can perform a particular action when the
            // Navigation View is closed by overriding the
            // onDrawerClosed() method.
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //Finally setting up the drawer listener for DrawerLayout
        drawerLayout.setDrawerListener(drawerToggle);

        //Sync State of the navigation icon on the toolbar
        // with the drawer when the drawer is opened or closed.
        drawerToggle.syncState();

        navigationView.getMenu().getItem(0).setChecked(true);
        //setLiveStreamVisibility();
    }



    public void setFragment(Fragment fragment, int fragmentName) {
        navigationDrawerTitleTextView.setText(getString(fragmentName));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, fragment)
                .commit();
    }

    public void setInitialFragment(Fragment fragment, int fragmentName) {
        navigationDrawerTitleTextView.setText(getString(fragmentName));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, fragment)
                .commit();
    }

    public Round getRoundFull() {
        return roundFull;
    }

    public void setRoundFull(Round roundFull) {
        RoundNavigationViewActivity.roundFull = null;
        RoundNavigationViewActivity.roundFull = roundFull;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Long getRoundImageId() {
        return roundImageId;
    }

    public void setRoundImageId(Long roundImageId) {
        RoundNavigationViewActivity.roundImageId = roundImageId;
    }

    public Championship getChampionshipFull() {
        return championshipFull;
    }

    public void setChampionshipFull(Championship championshipFull) {
        RoundNavigationViewActivity.championshipFull = championshipFull;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        RoundNavigationViewActivity.roundId = roundId;
    }

    public void setLiveStreamVisibility() {
        if (roundFull.getLiveStream() == null) navigationView.getMenu().getItem(4).setVisible(false);
    }

    public boolean isLiveStreamUrl () {
        if (roundFull.getLiveStream() == null) return false;
        else {
            return true;
        }
    }

    public void getFullRound() {

        dialog = ProgressDialog.show(this, "Loading", "Getting Round info...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        RoundService roundService;
        roundService = retrofit.create(RoundService.class);

        roundId = getIntent().getExtras().getLong("roundId");
        Call<Round> roundCall = roundService.getRound(roundId);

        roundCall.enqueue(new retrofit.Callback<Round>() {
            @Override
            public void onResponse(final Response<Round> response, Retrofit retrofit) {
                setRoundFull(response.body());
                setLiveStreamVisibility();
                setInitialFragment(new TrackLayoutFragment(), R.string.track_layout);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
                super.onBackPressed();
        }
    }
}
