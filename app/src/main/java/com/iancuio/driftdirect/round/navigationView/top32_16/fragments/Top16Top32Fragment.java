package com.iancuio.driftdirect.round.navigationView.top32_16.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.JudgeBattleActivity;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.publicBattle.PublicBattleActivity;
import com.iancuio.driftdirect.round.RoundNavigationViewActivity;
import com.iancuio.driftdirect.round.navigationView.qualifications.fragments.QualificationsResultsListFragment;
import com.iancuio.driftdirect.sharedAdapters.viewPagerAdapters.ScreenSlidePagerAdapter;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.playoffs.PlayoffTreeGraphicDisplay;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Top16Top32Fragment extends Fragment {

    @Bind(R.id.tabLayout_top16Top32Layout_slidingTabs)
    TabLayout top16Top32TabLayout;
    @Bind(R.id.viewPager_top16Top32Layout_drivers)
    ViewPager top16Top32ViewPager;
    @Bind(R.id.relativeLayout_top16Top32Layout_battleLauncher)
    RelativeLayout battleLauncher;
    @Bind(R.id.imageView_top16Top32Layout_firstDriverFlag)
    ImageView firstDriverFlag;
    @Bind(R.id.textView_top16Top32Layout_firstDriverName)
    TextView firstDriverName;
    @Bind(R.id.imageView_top16Yop32Layout_firstBadgePicture)
    CircleImageView firstDriverPicture;
    @Bind(R.id.progressBar_top16Yop32Layout_firstBadgePictureProgressBar)
    ProgressBar firstDriverPictureProgressBar;
    @Bind(R.id.textView_top16Yop32Layout_firstBadgeText)
    TextView firstDriverStatus;
    @Bind(R.id.textView_top16Yop32Layout_firstBadgeOrder)
    TextView firstDriverQualificationOrder;
    @Bind(R.id.textView_top16Top32Layout_firstDriverCarModel)
    TextView firstDriverCarModel;
    @Bind(R.id.textView_top16Top32Layout_firstDriverCarHP)
    TextView firstDriverCarHP;

    @Bind(R.id.imageView_top16Top32Layout_secondDriverFlag)
    ImageView secondDriverFlag;
    @Bind(R.id.textView_top16Top32Layout_secondDriverName)
    TextView secondDriverName;
    @Bind(R.id.imageView_top16Yop32Layout_secondBadgePicture)
    CircleImageView secondDriverPicture;
    @Bind(R.id.progressBar_top16Yop32Layout_secondBadgePictureProgressBar)
    ProgressBar secondDriverPictureProgressBar;
    @Bind(R.id.textView_top16Yop32Layout_secondBadgeText)
    TextView secondDriverStatus;
    @Bind(R.id.textView_top16Yop32Layout_secondBadgeOrder)
    TextView secondDriverQualificationOrder;
    @Bind(R.id.textView_top16Top32Layout_secondDriverCarModel)
    TextView secondDriverCarModel;
    @Bind(R.id.textView_top16Top32Layout_secondDriverCarHP)
    TextView secondDriverCarHP;


    private ScreenSlidePagerAdapter top16Top32PagerAdapter;
    PlayoffTreeGraphicDisplay playoffTreeGraphicDisplay;


    Round roundFull;

    ProgressDialog dialog;

    Bundle bundle;
    int topNumber;


    public Top16Top32Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        View v = inflater.inflate(R.layout.fragment_top16_top32, container, false);
        ButterKnife.bind(this, v);
        if (top16Top32PagerAdapter != null) {
            getPlayoffs();
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity) getActivity()).getRoundFull();

        if (top16Top32PagerAdapter == null) {
            getPlayoffs();
        }

    }

    private List<Fragment> getFragmentsForViewPager(final PlayoffTreeGraphicDisplay playoffTreeGraphicDisplay) {
        List<Fragment> fList = new ArrayList<Fragment>();

        final SubTop16Top32Fragment top24 = new SubTop16Top32Fragment();
        bundle = new Bundle();
        Utils.nullCheck(playoffTreeGraphicDisplay, new NullCheck() {
            @Override
            public void onNotNull() {

                Utils.nullCheck(playoffTreeGraphicDisplay.getStages().get(0), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(0));
                        bundle.putLong("topNumber", 24);
                        top24.setArguments(bundle);
                    }

                    @Override
                    public void onNull() {

                    }
                });

            }

            @Override
            public void onNull() {

            }
        });


        final SubTop16Top32Fragment top16 = new SubTop16Top32Fragment();
        bundle = new Bundle();

        Utils.nullCheck(playoffTreeGraphicDisplay, new NullCheck() {
            @Override
            public void onNotNull() {

                Utils.nullCheck(playoffTreeGraphicDisplay.getStages().get(1), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(1));
                        bundle.putLong("topNumber", 16);
                        top16.setArguments(bundle);
                    }

                    @Override
                    public void onNull() {

                    }
                });

            }

            @Override
            public void onNull() {

            }
        });

        final SubTop16Top32Fragment top8 = new SubTop16Top32Fragment();
        bundle = new Bundle();

        Utils.nullCheck(playoffTreeGraphicDisplay, new NullCheck() {
            @Override
            public void onNotNull() {
                Utils.nullCheck(playoffTreeGraphicDisplay.getStages().get(2), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(2));
                        bundle.putLong("topNumber", 8);
                        top8.setArguments(bundle);
                    }

                    @Override
                    public void onNull() {

                    }
                });
            }

            @Override
            public void onNull() {

            }
        });

        final SubTop16Top32Fragment top4 = new SubTop16Top32Fragment();
        bundle = new Bundle();
        Utils.nullCheck(playoffTreeGraphicDisplay, new NullCheck() {
            @Override
            public void onNotNull() {
                Utils.nullCheck(playoffTreeGraphicDisplay.getStages().get(3), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(3));
                        bundle.putLong("topNumber", 4);
                        top4.setArguments(bundle);
                    }

                    @Override
                    public void onNull() {

                    }
                });
            }

            @Override
            public void onNull() {

            }
        });

        final SubTop16Top32Fragment finals = new SubTop16Top32Fragment();
        bundle = new Bundle();
        Utils.nullCheck(playoffTreeGraphicDisplay, new NullCheck() {
            @Override
            public void onNotNull() {
                Utils.nullCheck(playoffTreeGraphicDisplay.getStages().get(4), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        bundle.putSerializable("playoffStage", playoffTreeGraphicDisplay.getStages().get(4));
                        bundle.putLong("topNumber", 0);
                        finals.setArguments(bundle);
                    }

                    @Override
                    public void onNull() {

                    }
                });
            }

            @Override
            public void onNull() {

            }
        });

        final QualificationsResultsListFragment results = new QualificationsResultsListFragment();
        bundle = new Bundle();
        Utils.nullCheck(playoffTreeGraphicDisplay, new NullCheck() {
            @Override
            public void onNotNull() {
                Utils.nullCheck(playoffTreeGraphicDisplay.getRoundResults(), new NullCheck() {
                    @Override
                    public void onNotNull() {
                        bundle.putSerializable("results", (Serializable) playoffTreeGraphicDisplay.getRoundResults());
                        results.setArguments(bundle);
                    }

                    @Override
                    public void onNull() {

                    }
                });
            }

            @Override
            public void onNull() {

            }
        });

        fList.add(top24);
        fList.add(top16);
        fList.add(top8);
        fList.add(top4);
        fList.add(finals);
        fList.add(results);
        return fList;
    }

    private void initializeDriverDetailsViewPager(PlayoffTreeGraphicDisplay playoffTreeGraphicDisplay) {
        List<Fragment> fragments = getFragmentsForViewPager(playoffTreeGraphicDisplay);
        String tabTitles[] = new String[]{"Top 24", "Top 16", "Top 8", "Top 4", "Finals", "Results"};

        // Instantiate a ViewPager and a PagerAdapter.

        top16Top32PagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabTitles);
        top16Top32ViewPager.setAdapter(top16Top32PagerAdapter);
        top16Top32ViewPager.setCurrentItem(0);

        //driverDetailsTabLayout.setSelectedTabIndicatorColor(Color.parseColor('#' + Integer.toHexString(getResources().getColor(R.color.colorChampionships))));
        top16Top32TabLayout.setupWithViewPager(top16Top32ViewPager);
    }

    @OnClick(R.id.relativeLayout_top16Top32Layout_battleLauncher)
    public void battleLauncherClick() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        Set<String> roles = sharedPreferences.getStringSet("roles", new HashSet<String>());
        if (roles.size() == 0) {
            if (playoffTreeGraphicDisplay != null) {
                if (playoffTreeGraphicDisplay.getCurrentBattle() != null) {
                    Intent intent = new Intent(getActivity(), PublicBattleActivity.class);
                    intent.putExtra("battleId", playoffTreeGraphicDisplay.getCurrentBattle().getId());
                    topNumber = 0;
                    switch (playoffTreeGraphicDisplay.getCurrentBattle().getOrder()) {
                        case 0:
                            topNumber = 24;
                            break;
                        case 1:
                            topNumber = 16;
                            break;
                        case 2:
                            topNumber = 8;
                            break;
                        case 3:
                            topNumber = 4;
                            break;
                        case 4:
                            topNumber = 0;
                            break;
                    }
                    intent.putExtra("topNumber", topNumber);
                    startActivity(intent);
                } else {
                    if (roles.contains("ROLE_JUDGE")) {
                        Intent intent = new Intent(getActivity(), JudgeBattleActivity.class);
                        intent.putExtra("battleId", playoffTreeGraphicDisplay.getCurrentBattle().getId());
                        intent.putExtra("token", sharedPreferences.getString("token", "token"));
                        intent.putExtra("topNumber", topNumber);
                        startActivity(intent);
                    }
                }
            }
        }
    }

    public void getPlayoffs() {
        dialog = ProgressDialog.show(getActivity(), "Loading", "Getting battles...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        RoundService roundService;
        roundService = retrofit.create(RoundService.class);

        long roundId = roundFull.getId();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "token");
        Call<PlayoffTreeGraphicDisplay> playoffTreeGraphicDisplayCall = roundService.getPlayoffs(token, roundId);

        playoffTreeGraphicDisplayCall.enqueue(new retrofit.Callback<PlayoffTreeGraphicDisplay>() {
            @Override
            public void onResponse(final Response<PlayoffTreeGraphicDisplay> response, Retrofit retrofit) {
                playoffTreeGraphicDisplay = response.body();
                initializeDriverDetailsViewPager(playoffTreeGraphicDisplay);

                if (playoffTreeGraphicDisplay != null) {
                    if (playoffTreeGraphicDisplay.getCurrentBattle() != null) {
                        ImageUtils.loadNormalImage(200, 200, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getProfilePicture(), firstDriverPicture, new Callback() {
                            @Override
                            public void onSuccess() {
                                firstDriverPictureProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                firstDriverPictureProgressBar.setVisibility(View.GONE);

                            }
                        });

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getFirstName(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                firstDriverName.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getFirstName() + " " + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getLastName());

                            }

                            @Override
                            public void onNull() {
                                firstDriverName.setText("-");
                            }
                        });

                        ImageUtils.loadNormalImage(100, 100, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getCountry(), firstDriverFlag, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {

                            }
                        });

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getRanking(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                firstDriverQualificationOrder.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getRanking()));

                            }

                            @Override
                            public void onNull() {
                                firstDriverQualificationOrder.setText("-");
                            }
                        });
                        //viewHolder.firstDriverStatus

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getMake(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                firstDriverCarModel.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getMake() + " " + playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getModel());

                            }

                            @Override
                            public void onNull() {
                                firstDriverCarModel.setText("-");

                            }
                        });

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                firstDriverCarHP.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver1().getDriver().getDriverDetails().getHorsePower() + "HP"));

                            }

                            @Override
                            public void onNull() {
                                firstDriverCarHP.setText("- HP");
                            }
                        });

                        ImageUtils.loadNormalImage(200, 200, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getProfilePicture(), secondDriverPicture, new Callback() {
                            @Override
                            public void onSuccess() {
                                secondDriverPictureProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                secondDriverPictureProgressBar.setVisibility(View.GONE);

                            }
                        });

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getFirstName(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                secondDriverName.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getFirstName() + " " + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getLastName());

                            }

                            @Override
                            public void onNull() {
                                secondDriverName.setText("-");

                            }
                        });

                        ImageUtils.loadNormalImage(100, 100, getActivity(), RestUrls.FILE + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getCountry(), secondDriverFlag, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                            }
                        });

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getRanking(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                secondDriverQualificationOrder.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getRanking()));

                            }

                            @Override
                            public void onNull() {
                                secondDriverQualificationOrder.setText("-");
                            }
                        });
                        //viewHolder.firstDriverStatus

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getMake(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                secondDriverCarModel.setText(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getMake() + " " + playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getModel());

                            }

                            @Override
                            public void onNull() {
                                secondDriverCarModel.setText("-");
                            }
                        });

                        Utils.nullCheck(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
                            @Override
                            public void onNotNull() {
                                secondDriverCarHP.setText(String.valueOf(playoffTreeGraphicDisplay.getCurrentBattle().getDriver2().getDriver().getDriverDetails().getHorsePower() + "HP"));

                            }

                            @Override
                            public void onNull() {
                                secondDriverCarHP.setText("- HP");
                            }
                        });
                        dialog.dismiss();
                    }
                }
                dialog.dismiss();
                Toast.makeText(getActivity(), "No drift battles yet!", Toast.LENGTH_SHORT).show();
                firstDriverPictureProgressBar.setVisibility(View.GONE);
                secondDriverPictureProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("top16top32", t.toString());
                dialog.dismiss();
                Toast.makeText(getActivity(), "No drift battles yet!", Toast.LENGTH_SHORT).show();
                firstDriverPictureProgressBar.setVisibility(View.GONE);
                secondDriverPictureProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.button_top16Top32Layout_refreshButton)
    public void refreshButtonClick() {
        getPlayoffs();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (top16Top32PagerAdapter != null) {
            top16Top32PagerAdapter = null;
            getPlayoffs();
        }
    }
}
