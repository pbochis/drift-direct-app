package com.iancuio.driftdirect.round.navigationView.top32_16.activities.publicBattle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.adapters.BattlePublicCommentsAdapter;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.adapters.BattlePublicJudgeAwardedPointsAdapter;
import com.iancuio.driftdirect.round.navigationView.top32_16.adapters.BattleRunBubbleAdapter;
import com.iancuio.driftdirect.customObjects.BattleJudgeAndComments;
import com.iancuio.driftdirect.customObjects.PublicViewBattleJudgeAwards;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.BattleRoundRunJudgeScores;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleViewPublicBattle;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.DialogUtils;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.SizeUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.clickListeners.RecyclerItemClickListener;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PublicBattleActivity extends AppCompatActivity {

    @Bind(R.id.textView_battlePublicLayout_topNumber)
    TextView topNumberTextView;
    @Bind(R.id.imageView_battlePublicLayout_firstDriverBadgePicture)
    CircleImageView firstDriverPictureImageView;
    @Bind(R.id.progressBar_battlePublicLayout_firstDriverBadgePictureProgressBar)
    ProgressBar firstDriverPictureProgressBar;
    @Bind(R.id.imageView_battlePublicLayout_firstDriverFlag)
    ImageView firstDriverFlag;
    @Bind(R.id.textView_battlePublicLayout_firstDriverAdvantage)
    TextView firstDriverAdvantageTextView;
    @Bind(R.id.textView_battlePublicLayout_firstDriverQualificationNumber)
    TextView firstDriverQualificationNumberTextView;
    @Bind(R.id.textView_battlePublicLayout_firstDriverName)
    TextView firstDriverNameTextView;
    @Bind(R.id.textView_battlePublicLayout_firstDriverCarModel)
    TextView firstDriverCarModelTextView;
    @Bind(R.id.textView_battlePublicLayout_firstDriverCarHp)
    TextView firstDriverCarHpTextView;
    @Bind(R.id.imageView_battlePublicLayout_firstDriverCarImage)
    ImageView firstDriverCarImageTextView;
    @Bind(R.id.progressBar_battlePublicLayout_firstDriverCarImageProgressBar)
    ProgressBar firstDriverCarImageProgressBar;
    @Bind(R.id.textView_battlePublicLayout_firstDriverStatus)
    TextView firstDriverStatusTextView;

    @Bind(R.id.imageView_battlePublicLayout_secondDriverBadgePicture)
    CircleImageView secondDriverPictureImageView;
    @Bind(R.id.progressBar_battlePublicLayout_secondDriverBadgePictureProgressBar)
    ProgressBar secondDriverPictureProgressBar;
    @Bind(R.id.imageView_battlePublicLayout_secondDriverFlag)
    ImageView secondDriverFlag;
    @Bind(R.id.textView_battlePublicLayout_secondDriverAdvantage)
    TextView secondDriverAdvantageTextView;
    @Bind(R.id.textView_battlePublicLayout_secondDriverQualificationNumber)
    TextView secondDriverQualificationNumberTextView;
    @Bind(R.id.textView_battlePublicLayout_secondDriverName)
    TextView secondDriverNameTextView;
    @Bind(R.id.textView_battlePublicLayout_secondDriverCarModel)
    TextView secondDriverCarModelTextView;
    @Bind(R.id.textView_battlePublicLayout_secondDriverCarHp)
    TextView secondDriverCarHpTextView;
    @Bind(R.id.imageView_battlePublicLayout_secondDriverCarImage)
    ImageView secondDriverCarImageTextView;
    @Bind(R.id.progressBar_battlePublicLayout_secondDriverCarImageProgressBar)
    ProgressBar secondDriverCarImageProgressBar;
    @Bind(R.id.textView_battlePublicLayout_secondDriverStatus)
    TextView secondDriverStatusTextView;

    @Bind(R.id.listView_battlePublicLayout_driversAwardedPoints)
    ListView driverAwardedPointsListView;
    @Bind(R.id.listView_battlePublicLayout_firstDriverComments)
    ListView firstDriverComments;
    @Bind(R.id.listView_battlePublicLayout_secondDriverComments)
    ListView secondDriverComments;
    @Bind(R.id.recyclerView_battlePublicLayout_runNumber)
    RecyclerView runNumber;

    ProgressDialog dialog;

    Long topNumber;

    PlayoffBattleViewPublicBattle playoffBattleViewPublicBattle;
    List<PublicViewBattleJudgeAwards> publicViewBattleJudgeAwardsList;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_battle);
        ButterKnife.bind(this);

        topNumber = getIntent().getLongExtra("topNumber", 0);

        getBattleDetails();
    }

    private void getBattleDetails() {
        dialog = ProgressDialog.show(this, "Loading", "Getting battle details...");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        RoundService roundService;
        roundService = retrofit.create(RoundService.class);

        Long battleId = getIntent().getLongExtra("battleId", 0);
        Call<PlayoffBattleViewPublicBattle> playoffBattleViewPublicBattleCall = roundService.getBattleDetailsForPublic(battleId);

        playoffBattleViewPublicBattleCall.enqueue(new retrofit.Callback<PlayoffBattleViewPublicBattle>() {
            @Override
            public void onResponse(final Response<PlayoffBattleViewPublicBattle> response, Retrofit retrofit) {

                if (response.code() == 200) {
                    if (response.body() != null) {
                        dialog.dismiss();
                        playoffBattleViewPublicBattle = response.body();

                        LinearLayoutManager layoutManager = new LinearLayoutManager(PublicBattleActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        runNumber.setLayoutManager(layoutManager);

                        runNumber.setAdapter(new BattleRunBubbleAdapter(playoffBattleViewPublicBattle.getRounds().size() * 2, playoffBattleViewPublicBattle.getRounds().size() + 1, false, null));

                        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

                        int dpWidth = displayMetrics.widthPixels;
                        int padding = (int) (10 * displayMetrics.density);
                        int childWidth = (int) (70 * displayMetrics.density);
                        int line = (int) (30 * displayMetrics.density);
                        int paddindToBeAdded = dpWidth - childWidth * playoffBattleViewPublicBattle.getRounds().size() * 2 - padding * 2 + line;

                        runNumber.setPadding(paddindToBeAdded / 2, 0, 0, 0);
                        runNumber.invalidate();
                        runNumber.requestLayout();

                        populateView(playoffBattleViewPublicBattle.getRounds().size() * 2 - 1);

                        runNumber.addOnItemTouchListener(new RecyclerItemClickListener(PublicBattleActivity.this,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View childView, int position) {
                                        populateView(position);

                                        runNumber.setAdapter(new BattleRunBubbleAdapter(playoffBattleViewPublicBattle.getRounds().size() * 2, playoffBattleViewPublicBattle.getRounds().size() + 1, true, position));
                                    }

                                    @Override
                                    public void onItemLongPress(View childView, int position) {
                                    }
                                }));

                    }
                } else if (response.code() == 412) {
                    dialog.dismiss();
                    DialogUtils.showAlertDialog(PublicBattleActivity.this, "Attention!", "Please wait for the other judges to give their scores for the current run!", "Please wait for the other judges to give their scores for the first run!");
                } else {
                    dialog.dismiss();
                    Toast.makeText(PublicBattleActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void populateView(int position) {

        if (topNumber != 0) {
            topNumberTextView.setText("TOP " + String.valueOf(topNumber));
        } else {
            topNumberTextView.setText("FINALS");
        }

        ImageUtils.loadNormalImage(200, 200, PublicBattleActivity.this, RestUrls.FILE + playoffBattleViewPublicBattle.getDriver1().getDriver().getProfilePicture(), firstDriverPictureImageView, new Callback() {
            @Override
            public void onSuccess() {
                firstDriverPictureProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        ImageUtils.loadNormalImage(100, 100, PublicBattleActivity.this, RestUrls.FILE + playoffBattleViewPublicBattle.getDriver1().getDriver().getCountry(), firstDriverFlag, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {

            }
        });

//                Utils.loadNormalImage(100, 100, JudgeBattleActivity.this, RestUrls.FILE + playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getCountry(), firstDriverFlag, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver1().getDriver().getFirstName(), new NullCheck() {
            @Override
            public void onNotNull() {
                firstDriverNameTextView.setText(playoffBattleViewPublicBattle.getDriver1().getDriver().getFirstName() + " " + playoffBattleViewPublicBattle.getDriver1().getDriver().getLastName());

            }

            @Override
            public void onNull() {
                firstDriverNameTextView.setText("-");
            }
        });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver1().getRanking(), new NullCheck() {
            @Override
            public void onNotNull() {
                firstDriverQualificationNumberTextView.setText(String.valueOf(playoffBattleViewPublicBattle.getDriver1().getRanking()));

            }

            @Override
            public void onNull() {
                firstDriverQualificationNumberTextView.setText("-");
            }
        });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver1().getDriver().getDriverDetails().getMake(), new NullCheck() {
            @Override
            public void onNotNull() {
                firstDriverCarModelTextView.setText(playoffBattleViewPublicBattle.getDriver1().getDriver().getDriverDetails().getMake() + " " + playoffBattleViewPublicBattle.getDriver1().getDriver().getDriverDetails().getModel());

            }

            @Override
            public void onNull() {
                firstDriverCarModelTextView.setText("-");
            }
        });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver1().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
            @Override
            public void onNotNull() {
                firstDriverCarHpTextView.setText(String.valueOf(playoffBattleViewPublicBattle.getDriver1().getDriver().getDriverDetails().getHorsePower()));

            }

            @Override
            public void onNull() {
                firstDriverCarHpTextView.setText("-");
            }
        });

        ImageUtils.loadNormalImage(200, 200, PublicBattleActivity.this, RestUrls.FILE + playoffBattleViewPublicBattle.getDriver2().getDriver().getProfilePicture(), secondDriverPictureImageView, new Callback() {
            @Override
            public void onSuccess() {
                firstDriverPictureProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        ImageUtils.loadNormalImage(100, 100, PublicBattleActivity.this, RestUrls.FILE + playoffBattleViewPublicBattle.getDriver2().getDriver().getCountry(), secondDriverFlag, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {

            }
        });

//                Utils.loadNormalImage(100, 100, JudgeBattleActivity.this, RestUrls.FILE + playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getCountry(), firstDriverFlag, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver2().getDriver().getFirstName(), new NullCheck() {
            @Override
            public void onNotNull() {
                secondDriverNameTextView.setText(playoffBattleViewPublicBattle.getDriver2().getDriver().getFirstName() + " " + playoffBattleViewPublicBattle.getDriver2().getDriver().getLastName());

            }

            @Override
            public void onNull() {
                secondDriverNameTextView.setText("-");
            }
        });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver2().getRanking(), new NullCheck() {
            @Override
            public void onNotNull() {
                secondDriverQualificationNumberTextView.setText(String.valueOf(playoffBattleViewPublicBattle.getDriver2().getRanking()));

            }

            @Override
            public void onNull() {
                secondDriverQualificationNumberTextView.setText("-");
            }
        });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver2().getDriver().getDriverDetails().getMake(), new NullCheck() {
            @Override
            public void onNotNull() {
                secondDriverCarModelTextView.setText(playoffBattleViewPublicBattle.getDriver2().getDriver().getDriverDetails().getMake() + " " + playoffBattleViewPublicBattle.getDriver2().getDriver().getDriverDetails().getModel());

            }

            @Override
            public void onNull() {
                secondDriverCarModelTextView.setText("-");
            }
        });

        Utils.nullCheck(playoffBattleViewPublicBattle.getDriver2().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
            @Override
            public void onNotNull() {
                secondDriverCarHpTextView.setText(String.valueOf(playoffBattleViewPublicBattle.getDriver2().getDriver().getDriverDetails().getHorsePower()));
            }

            @Override
            public void onNull() {
                secondDriverCarHpTextView.setText("-");
            }
        });

        if (((position + 1) % 2) == 0) {
            firstDriverStatusTextView.setText("CHASE");
            secondDriverStatusTextView.setText("LEAD");
        } else {
            firstDriverStatusTextView.setText("LEAD");
            secondDriverStatusTextView.setText("CHASE");
        }

        int buttonValue = position + 1;
        int roundIndex = buttonValue % 2 == 0 ? (buttonValue / 2) - 1 : ((buttonValue + 1) / 2 - 1);
        List<BattleRoundRunJudgeScores> selectedRun = buttonValue % 2 == 1 ? playoffBattleViewPublicBattle.getRounds().get(roundIndex).getFirstRunScores() : playoffBattleViewPublicBattle.getRounds().get(roundIndex).getSecondRunScores();

        //if (position == roundIndex) {

        int firstDriverPoints = 0;
        int secondDriverPoints = 0;

        if (selectedRun.size() == 3) {
            for (int i = 0; i < selectedRun.size(); i++) {
                firstDriverPoints += selectedRun.get(i).getDriver1Score();
                secondDriverPoints += selectedRun.get(i).getDriver2Score();
            }
        }


        if (selectedRun.size() == 3) {
            if (firstDriverPoints > secondDriverPoints) {
                firstDriverAdvantageTextView.setVisibility(View.VISIBLE);
                secondDriverAdvantageTextView.setVisibility(View.GONE);
            } else {
                firstDriverAdvantageTextView.setVisibility(View.GONE);
                secondDriverAdvantageTextView.setVisibility(View.VISIBLE);
            }
        } else {
            firstDriverAdvantageTextView.setVisibility(View.GONE);
            secondDriverAdvantageTextView.setVisibility(View.GONE);
        }

        if (selectedRun.size() == 3) {
            if (position + 1 == playoffBattleViewPublicBattle.getRounds().size() * 2) {
                if (firstDriverPoints > secondDriverPoints) {
                    firstDriverAdvantageTextView.setText("WINNER");
                } else if (secondDriverPoints > firstDriverPoints) {
                    secondDriverAdvantageTextView.setText("WINNER");
                } else {
                    firstDriverAdvantageTextView.setVisibility(View.GONE);
                    secondDriverAdvantageTextView.setVisibility(View.GONE);
                }
            } else {
                firstDriverAdvantageTextView.setText("ADVANTAGE");
                secondDriverAdvantageTextView.setText("ADVANTAGE");
            }
        }


        publicViewBattleJudgeAwardsList = new ArrayList<>();
        for (int i = 0; i < selectedRun.size(); i++) {


            PublicViewBattleJudgeAwards publicViewbattleJudgeAwards = new PublicViewBattleJudgeAwards();
            publicViewbattleJudgeAwards.setJudgeName(selectedRun.get(i).getJudge().getFirstName() + " " + selectedRun.get(i).getJudge().getLastName());
            publicViewbattleJudgeAwards.setFirstDriverPoints(selectedRun.get(i).getDriver1Score());
            publicViewbattleJudgeAwards.setSecondDriverPoints(selectedRun.get(i).getDriver2Score());

            publicViewBattleJudgeAwardsList.add(publicViewbattleJudgeAwards);
        }

        driverAwardedPointsListView.setAdapter(new BattlePublicJudgeAwardedPointsAdapter(PublicBattleActivity.this, publicViewBattleJudgeAwardsList));
        SizeUtils.setListViewHeightBasedOnItems(driverAwardedPointsListView);

        List<BattleJudgeAndComments> firstDriverBattleJudgeAndCommentsList = new ArrayList<>();
        List<BattleJudgeAndComments> secondDriverBattleJudgeAndCommentsList = new ArrayList<>();

        for (int i = 0; i < selectedRun.size(); i++) {

            BattleJudgeAndComments firstDriverBattleJudgeAndComments = new BattleJudgeAndComments();
            BattleJudgeAndComments secondDriverBattleJudgeAndComments = new BattleJudgeAndComments();

            firstDriverBattleJudgeAndComments.setJudgeName(selectedRun.get(i).getJudge().getFirstName() + " " + selectedRun.get(i).getJudge().getLastName());
            // TO DO
            firstDriverBattleJudgeAndComments.setJudgeType(selectedRun.get(i).getJudge().getFirstName());

            secondDriverBattleJudgeAndComments.setJudgeName(selectedRun.get(i).getJudge().getFirstName() + " " + selectedRun.get(i).getJudge().getLastName());
            // TO DO
            secondDriverBattleJudgeAndComments.setJudgeType(selectedRun.get(i).getJudge().getFirstName());

            List<Comment> firstDriverPositiveComments = new ArrayList<>();
            List<Comment> firstDriverNegativeComments = new ArrayList<>();
            List<Comment> secondDriverPositiveComments = new ArrayList<>();
            List<Comment> secondDriverNegativeComments = new ArrayList<>();

            for (Comment comment : selectedRun.get(i).getDriver1Comments()) {
                if (comment.getPositive()) {
                    firstDriverPositiveComments.add(comment);
                } else {
                    firstDriverNegativeComments.add(comment);
                }
            }

            for (Comment comment : selectedRun.get(i).getDriver2Comments()) {
                if (comment.getPositive()) {
                    secondDriverPositiveComments.add(comment);
                } else {
                    secondDriverNegativeComments.add(comment);
                }
            }

            firstDriverBattleJudgeAndComments.setPositiveComments(firstDriverPositiveComments);
            firstDriverBattleJudgeAndComments.setNegativeComments(firstDriverNegativeComments);

            secondDriverBattleJudgeAndComments.setPositiveComments(secondDriverPositiveComments);
            secondDriverBattleJudgeAndComments.setNegativeComments(secondDriverNegativeComments);

            firstDriverBattleJudgeAndCommentsList.add(firstDriverBattleJudgeAndComments);
            secondDriverBattleJudgeAndCommentsList.add(secondDriverBattleJudgeAndComments);

        }

        firstDriverComments.setAdapter(new BattlePublicCommentsAdapter(PublicBattleActivity.this, findViewById(android.R.id.content), firstDriverBattleJudgeAndCommentsList, true));
        SizeUtils.setListViewHeightBasedOnItems(firstDriverComments);

        secondDriverComments.setAdapter(new BattlePublicCommentsAdapter(PublicBattleActivity.this, findViewById(android.R.id.content), secondDriverBattleJudgeAndCommentsList, false));
        SizeUtils.setListViewHeightBasedOnItems(secondDriverComments);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}



