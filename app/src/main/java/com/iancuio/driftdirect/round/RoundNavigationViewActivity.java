package com.iancuio.driftdirect.round;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.round.navigationView.eventSchedule.fragments.EventScheduleFragment;
import com.iancuio.driftdirect.round.navigationView.qualifications.fragments.QualificationsListFragment;
import com.iancuio.driftdirect.round.navigationView.top32_16.fragments.Top16Top32Fragment;
import com.iancuio.driftdirect.round.navigationView.trackLayout.TrackLayoutFragment;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.DateTimeUtils;
import com.iancuio.driftdirect.utils.FragmentUtils;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RoundNavigationViewActivity extends AppCompatActivity {

    public static Round roundFull;
    public static Long roundImageId;
    private static Long roundId;
    private static Championship championshipFull;
    public Fragment currentFragment;
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
    @Bind(R.id.textView_roundNavigationViewLayout_navigationDrawerLoggedInName)
    TextView toolbarloggedInNameTextView;
    @Bind(R.id.imageView_roundNavigationViewLayout_driverPicture)
    CircleImageView loggedInProfilePicture;
    @Bind(R.id.progressBar_roundNavigationViewLayout_progressBar)
    ProgressBar loggedInProgressBar;

    @Bind(R.id.linearLayout_feedBar_nextRoundTimer)
    LinearLayout nextRoundTimerContainer;
    @Bind(R.id.textView_feedBar_nextRoundLocation)
    TextView nextRoundLocationTextView;
    @Bind(R.id.textView_feedBar_nextRoundDays)
    TextView nextRoundDaysTextView;
    @Bind(R.id.textView_feedBar_nextRoundHours)
    TextView nextRoundHoursTextView;

    @Bind(R.id.linearLayout_feedBar_currentRound)
    LinearLayout currentRoundContainer;
    @Bind(R.id.textView_feedBar_currentRoundLocation)
    TextView currentRoundLocationTextView;
    @Bind(R.id.textView_feedBar_currentRoundSchedule)
    TextView currentRoundScheduleTextView;
    @Bind(R.id.imageView_feedBar_currentRoundWeatherImage)
    ImageView currentRoundWeatherImageImageView;
    @Bind(R.id.textView_feedBar_currentRoundWeatherDay)
    TextView currentRoundWeatherDayTextView;
    @Bind(R.id.imageView_feedBar_currentRoundNextWeatherImage)
    ImageView currentRoundNextWeatherImageImageView;
    @Bind(R.id.textView_feedBar_currentRoundNextWeatherDay)
    TextView currentRoundNextWeatherDayTextView;

    ProgressDialog dialog;
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

        SharedPreferences sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("username", "token");

        if (!token.equals("token")) {
            toolbarloggedInNameTextView.setText(token);
            ImageUtils.loadProfileImage(100, 100, this, "eewjhg", loggedInProfilePicture, new Callback() {
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
        }

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

        setRoundImageId(getIntent().getExtras().getLong("roundImageId"));
        setChampionshipFull((Championship) getIntent().getExtras().getSerializable("championshipFull"));

        getFullRound();
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
                switch (menuItem.getItemId()) {
                    case R.id.track_layout:
                        FragmentUtils.setFragment(new TrackLayoutFragment(), R.string.track_layout, R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, RoundNavigationViewActivity.this);
                        return true;
                    case R.id.qualifications:
                        FragmentUtils.setFragment(new QualificationsListFragment(), R.string.qualifications, R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, RoundNavigationViewActivity.this);
                        return true;
                    case R.id.top_32_16:
                        FragmentUtils.setFragment(new Top16Top32Fragment(), R.string.top_32_16, R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, RoundNavigationViewActivity.this);
                        return true;
                    case R.id.event_schedule:
                        FragmentUtils.setFragment(new EventScheduleFragment(), R.string.event_schedule, R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, RoundNavigationViewActivity.this);
                        return true;
                    case R.id.live_stream:
                        if (Patterns.WEB_URL.matcher(roundFull.getLiveStream()).matches()) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(roundFull.getLiveStream()));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(RoundNavigationViewActivity.this, "Bad URL format.", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    case R.id.buy_tickets_online_rounds:
                        Utils.nullCheck(roundFull.getTicketsUrl(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                if (Patterns.WEB_URL.matcher(roundFull.getTicketsUrl()).matches()) {
                                    Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(roundFull.getTicketsUrl()));
                                    startActivity(browserIntent2);
                                } else {
                                    Toast.makeText(RoundNavigationViewActivity.this, "Bad URL format.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onNull() {
                                Toast.makeText(RoundNavigationViewActivity.this, "No tickets link provided!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    default:
                        return true;
                }
            }
        });
        //Initialising DrawerLayout.
        //Initialising ActionBarDrawerToggle and overriding its methods
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, navigationDrawerTitleToolbar, R.string.open_drawer, R.string.close_drawer) {
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
                setTicketsUrlVisibility();
                FragmentUtils.setInitialFragment(new TrackLayoutFragment(), R.string.track_layout, R.id.frameLayout_roundNavigationViewLayout_fragmentContainer, navigationDrawerTitleTextView, RoundNavigationViewActivity.this);

                Utils.nullCheck(roundFull.getSchedule(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        if (!roundFull.getSchedule().get(0).getEndDate().isBeforeNow()) {
                            setFeedBar();
                        } else {
                            nextRoundTimerContainer.setVisibility(View.GONE);
                            currentRoundContainer.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNull() {
                        nextRoundTimerContainer.setVisibility(View.GONE);
                        currentRoundContainer.setVisibility(View.GONE);
                    }
                });
                dialog.dismiss();

            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
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
        if (roundFull.getLiveStream() == null)
            navigationView.getMenu().getItem(4).setVisible(false);
    }

    public void setTicketsUrlVisibility() {
        if (roundFull.getTicketsUrl() == null)
            navigationView.getMenu().getItem(5).setVisible(false);
    }

    @Override
    public void onBackPressed() {

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setFeedBar() {
        if (DateTimeUtils.eventIsNow(roundFull.getSchedule().get(0).getStartDate(), roundFull.getSchedule().get(roundFull.getSchedule().size() - 1).getEndDate())) {
            nextRoundTimerContainer.setVisibility(View.GONE);
            currentRoundContainer.setVisibility(View.VISIBLE);

            currentRoundLocationTextView.setText(roundFull.getName());

            int i;
            for (i = 0; i<roundFull.getSchedule().size(); i++) {
                if (DateTimeUtils.eventIsNow(roundFull.getSchedule().get(i).getStartDate(), roundFull.getSchedule().get(i).getEndDate())) {
                    initiateCountDown(i);
                    break;

                }
            }
        } else if (Days.daysBetween(DateTime.now(), roundFull.getSchedule().get(roundFull.getSchedule().size()-1).getStartDate()).getDays() < 7) {
            nextRoundTimerContainer.setVisibility(View.VISIBLE);
            currentRoundContainer.setVisibility(View.GONE);
            nextRoundLocationTextView.setText(roundFull.getName());

            final long roundMilliseconds = roundFull.getStartDate().getMillis();
            final long nowMilliseconds = DateTime.now().getMillis();

            if (roundMilliseconds - nowMilliseconds < 3600000) {
                nextRoundDaysTextView.setText(String.valueOf(TimeUnit.MILLISECONDS.toDays(0)));
                nextRoundHoursTextView.setText(String.valueOf(TimeUnit.MILLISECONDS.toHours(0)));
            }

            new CountDownTimer(roundMilliseconds - nowMilliseconds, 3600000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (!(millisUntilFinished < 3600000)) {
                        nextRoundDaysTextView.setText(String.valueOf(TimeUnit.MILLISECONDS.toDays(roundMilliseconds - nowMilliseconds)));
                        nextRoundHoursTextView.setText(String.valueOf(TimeUnit.MILLISECONDS.toHours(roundMilliseconds - nowMilliseconds) % 24));
                    } else {
                        nextRoundDaysTextView.setText(String.valueOf(TimeUnit.MILLISECONDS.toDays(0)));
                        nextRoundHoursTextView.setText(String.valueOf(TimeUnit.MILLISECONDS.toHours(0)));
                    }

                }

                @Override
                public void onFinish() {
                    setFeedBar();
                }
            }.start();
        }
    }

    private void initiateCountDown(final int i) {
        if (i<roundFull.getSchedule().size()) {
            long eventMilliseconds = roundFull.getSchedule().get(i).getEndDate().getMillis();
            long nowMilliseconds = DateTime.now().getMillis();
            currentRoundScheduleTextView.setText(roundFull.getSchedule().get(i).getName());
            new CountDownTimer(eventMilliseconds - nowMilliseconds, eventMilliseconds - nowMilliseconds) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    if (i+1 == roundFull.getSchedule().size()) {
                        nextRoundTimerContainer.setVisibility(View.GONE);
                        currentRoundContainer.setVisibility(View.GONE);
                    } else {
                        initiateCountDown(i + 1);
                    }
                }
            }.start();
        }
    }
}
