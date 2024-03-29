package com.iancuio.driftdirect.championship;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.championship.navigationView.authorityLogin.LoginActivity;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.championship.navigationView.calendar.fragments.CalendarFragment;
import com.iancuio.driftdirect.championship.navigationView.news.fragments.NewsFragment;
import com.iancuio.driftdirect.championship.navigationView.officialSponsors.fragments.OfficialSponsorsFragment;
import com.iancuio.driftdirect.championship.navigationView.rankings.fragments.RankingsFragment;
import com.iancuio.driftdirect.championship.navigationView.teamOfJudges.TeamOfJudgesFragment;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.utils.FragmentUtils;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
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
    String token;

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
        token = sharedPreferences.getString("username", "token");

        if (!token.equals("token")) {
            toolbarLoggedInName.setText(token);
            ImageUtils.loadProfileImage(100, 100, this, "TO ADD LINK", loggedInProfilePicture, new Callback() {
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
                        FragmentUtils.setFragment(new CalendarFragment(), R.string.calendar, R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, ChampionshipNavigationViewActivity.this);
                        return true;
                    case R.id.rule_book:
                        Utils.nullCheck(championshipFull.getRules().getRules(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                if (Patterns.WEB_URL.matcher(championshipFull.getRules().getRules()).matches()) {
                                    Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(championshipFull.getRules().getRules()));
                                    startActivity(browserIntent2);
                                } else {
                                    Toast.makeText(ChampionshipNavigationViewActivity.this, "Bad URL format.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onNull() {

                            }
                        });
                        return true;
                    case R.id.team_of_judges:
                        FragmentUtils.setFragment(new TeamOfJudgesFragment(), R.string.team_of_judges, R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, ChampionshipNavigationViewActivity.this);
                        return true;
                    case R.id.rankings:
                        FragmentUtils.setFragment(new RankingsFragment(), R.string.rankings, R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, ChampionshipNavigationViewActivity.this);
                        return true;
                    case R.id.news:
                        FragmentUtils.setFragment(new NewsFragment(), R.string.news, R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, ChampionshipNavigationViewActivity.this);
                        return true;
                    case R.id.official_sponsors:
                        FragmentUtils.setFragment(new OfficialSponsorsFragment(), R.string.official_sponsors, R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, ChampionshipNavigationViewActivity.this);
                        return true;
                    case R.id.buy_tickets_online:
                        //setFragment(new BuyTicketsFragment(), R.string.buy_tickets_online);
                        if (Patterns.WEB_URL.matcher(championshipFull.getTicketsUrl()).matches()) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(championshipFull.getTicketsUrl()));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(ChampionshipNavigationViewActivity.this, "Bad URL format.", Toast.LENGTH_SHORT).show();
                        }
                        return true;
//                    case R.id.notifications:
//                        setFragment(new JudgingScoresJudgeScores(), R.string.notifications);
//                        return true;
                    case R.id.authority_login:
                        Intent intent = new Intent(ChampionshipNavigationViewActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
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
                FragmentUtils.setInitialFragment(new CalendarFragment(), R.string.calendar, R.id.frameLayout_championshipNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, ChampionshipNavigationViewActivity.this);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e("championship", t.toString());
            }
        });
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

    @Override
    public void onBackPressed() {

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("username", "token");
        if (!token.equals("token")) {
            toolbarLoggedInName.setText(token);
            loggedInProfilePicture.setVisibility(View.VISIBLE);
            ImageUtils.loadProfileImage(100, 100, this, "TO ADD LINK", loggedInProfilePicture, new Callback() {
                @Override
                public void onSuccess() {
                    loggedInProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    loggedInProgressBar.setVisibility(View.GONE);
                }
            });
        } else {
            loggedInProgressBar.setVisibility(View.GONE);
            toolbarLoggedInName.setText("");
            loggedInProfilePicture.setVisibility(View.GONE);
        }
    }
}
