package com.iancuio.driftdirect.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.fragments.CalendarFragment;
import com.iancuio.driftdirect.fragments.DriversFragment;
import com.iancuio.driftdirect.fragments.JudgingScoresJudgeScores;
import com.iancuio.driftdirect.fragments.NewsFragment;
import com.iancuio.driftdirect.fragments.NotificationsFragment;
import com.iancuio.driftdirect.fragments.OfficialSponsorsFragment;
import com.iancuio.driftdirect.fragments.RankingsFragment;
import com.iancuio.driftdirect.fragments.SetOfRulesFragment;
import com.iancuio.driftdirect.fragments.TeamOfJudgesFragment;
import com.iancuio.driftdirect.fragments.TrackLayoutFragment;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ChampionshipNavigationViewActivity extends AppCompatActivity {

    public static ChampionshipShort selectedChampionship = null;
    public static Championship championshipFull = null;
    public static Round roundFull;
    public Fragment currentFragment;
    @Bind(R.id.toolbar_navigationViewLayout_feed)
    Toolbar feedToolbar;
    @Bind(R.id.toolbar_championshipNavigationViewLayout_navigationDrawerTitle)
    Toolbar navigationDrawerTitleToolbar;
    @Bind(R.id.navigationView_championshipNavigationViewLayout_navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawer_championshipNavigationViewLayout_drawer)
    DrawerLayout drawerLayout;
    @Bind(R.id.textView_championshipNavigationViewLayout_navigationDrawerTitle)
    TextView navigationDrawerTitleTextView;
    @Bind(R.id.textView_championshipNavigationViewLayout_navigationDrawerLoggedInName)
    TextView toolbarLoggedInName;
    @Bind(R.id.imageView_championshipNavigationViewLayout_driverPicture)
    CircleImageView loggedInProfilePicture;
    @Bind(R.id.progressBar_championshipNavigationViewLayout_progressBar)
    ProgressBar loggedInProgressBar;
    ProgressDialog dialog;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship_navigation_view);
        ButterKnife.bind(this);

        setSelectedChampionship((ChampionshipShort) getIntent().getSerializableExtra("championship"));
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

        TextView website = (TextView) findViewById(R.id.textView_actionBsrLayout_website);
        website.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // profit
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.d1nz.com/"));
                startActivity(browserIntent);
            }
        });

        ImageView website2 = (ImageView) findViewById(R.id.textView_actionBsrLayout_logo);
        website2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // profit
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.d1nz.com/"));
                startActivity(browserIntent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("username", "token");

        if (!token.equals("token")) {
            toolbarLoggedInName.setText(token);
            Utils.loadProfileImage(100, 100, this, "TO ADD LINK", loggedInProfilePicture, new Callback() {
                @Override
                public void onSuccess() {
                    loggedInProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    loggedInProgressBar.setVisibility(View.GONE);
                }
            });
        }



        getFullChampionship();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Navigation View must close when any of this item is clicked.
                //To do this we use the closeDrawers() method.
                drawerLayout.closeDrawers();

                //Using switch case to identify the ID of the menu item
                // and then performing relevant action.
                switch (menuItem.getItemId()){
                    case R.id.calendar:
                        setFragment(new CalendarFragment(), R.string.calendar);
                        return true;
                    case R.id.rule_book:
                        Utils.nullCheck(championshipFull.getRules().getRules(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(championshipFull.getRules().getRules()));
                                startActivity(browserIntent2);
                            }

                            @Override
                            public void onNull() {

                            }
                        });

                        return true;
                    case R.id.team_of_judges:
                        setFragment(new TeamOfJudgesFragment(), R.string.team_of_judges);
                        return true;
                    case R.id.drivers:
                        setFragment(new DriversFragment(), R.string.drivers);
                        return true;
                    case R.id.rankings:
                        setFragment(new RankingsFragment(), R.string.rankings);
                        return true;
                    case R.id.news:
                        setFragment(new NewsFragment(), R.string.news);
                        return true;
                    case R.id.official_sponsors:
                        setFragment(new OfficialSponsorsFragment(), R.string.official_sponsors);
                        return true;
                    case R.id.buy_tickets_online:
                        //setFragment(new BuyTicketsFragment(), R.string.buy_tickets_online);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(championshipFull.getTicketsUrl()));
                        startActivity(browserIntent);
                        return true;
//                    case R.id.notifications:
//                        setFragment(new JudgingScoresJudgeScores(), R.string.notifications);
//                        return true;
                    case R.id.authority_login:
                        Intent intent = new Intent(ChampionshipNavigationViewActivity.this, LoginActivity.class);
                        startActivity(intent);
                    default:
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
    }



    public void setFragment(Fragment fragment, int fragmentName) {
        navigationDrawerTitleTextView.setText(getString(fragmentName));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, fragment)
                .commit();
    }

    public void setInitialFragment(Fragment fragment, int fragmentName) {
        navigationDrawerTitleTextView.setText(getString(fragmentName));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, fragment)
                .commit();
    }

    public ChampionshipShort getSelectedChampionship() {
        return selectedChampionship;
    }

    public void setSelectedChampionship(ChampionshipShort selectedChampionship) {
        ChampionshipNavigationViewActivity.selectedChampionship = selectedChampionship;
    }

    public Championship getChampionshipFull() {
        return championshipFull;
    }

    public void setChampionshipFull(Championship championshipFull) {
        ChampionshipNavigationViewActivity.championshipFull = championshipFull;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    private void getFullChampionship() {

        dialog = ProgressDialog.show(this, "Loading", "Getting Championship info...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        ChampionshipService championshipService;
        championshipService = retrofit.create(ChampionshipService.class);

        Call<Championship> championshipsCall = championshipService.getChampionshipsFull(getSelectedChampionship().getId());

        championshipsCall.enqueue(new retrofit.Callback<Championship>() {
            @Override
            public void onResponse(final Response<Championship> response, Retrofit retrofit) {
                setChampionshipFull(response.body());
                setInitialFragment(new CalendarFragment(), R.string.calendar);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e("championship", "FMM PILU");

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
