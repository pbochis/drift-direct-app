package com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.adapters.RunJudgeSelectCommentsAdapter;
import com.iancuio.driftdirect.round.navigationView.top32_16.adapters.BattleRunBubbleAdapter;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleSubmitDriverJudgeAwards;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleSubmitJudgeBattle;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleViewJudgeBattle;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.service.RoundService;
import com.iancuio.driftdirect.utils.DialogUtils;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.SizeUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.rangeSeekBar.RangeBar;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class JudgeBattleActivity extends AppCompatActivity {

	static PopupWindow fadePopup;
	static View popupView;
	static PopupWindow popupWindow;
	@Bind(R.id.textView_battleJudgeLayout_topNumber)
	TextView topNumberTextView;
	@Bind(R.id.imageView_battleJudgeLayout_firstDriverBadgePicture)
	CircleImageView firstDriverPictureImageView;
	@Bind(R.id.progressBar_battleJudgeLayout_firstDriverBadgePictureProgressBar)
	ProgressBar firstDriverPictureProgressBar;
	@Bind(R.id.imageView_battleJudgeLayout_firstDriverFlag)
	ImageView firstDriverFlag;
	@Bind(R.id.textView_battleJudgeLayout_firstDriverAdvantage)
	TextView firstDriverAdvantageTextView;
	@Bind(R.id.textView_battleJudgeLayout_firstDriverQualificationNumber)
	TextView firstDriverQualificationNumberTextView;
	@Bind(R.id.textView_battleJudgeLayout_firstDriverName)
	TextView firstDriverNameTextView;
	@Bind(R.id.textView_battleJudgeLayout_firstDriverCarModel)
	TextView firstDriverCarModelTextView;
	@Bind(R.id.textView_battleJudgeLayout_firstDriverCarHp)
	TextView firstDriverCarHpTextView;
	@Bind(R.id.imageView_battleJudgeLayout_firstDriverCarImage)
	ImageView firstDriverCarImageTextView;
	@Bind(R.id.progressBar_battleJudgeLayout_firstDriverCarImageProgressBar)
	ProgressBar firstDriverCarImageProgressBar;
	@Bind(R.id.button_battleJudgeLayout_firstDriverPositiveComments)
	Button firstDriverPositiveComments;
	@Bind(R.id.button_battleJudgeLayout_firstDriverNegativeComments)
	Button firstDriverNegativeComments;
	@Bind(R.id.textView_battleJudgeLayout_firstDriverStatus)
	TextView firstDriverStatusTextView;
	@Bind(R.id.imageView_battleJudgeLayout_secondDriverBadgePicture)
	CircleImageView secondDriverPictureImageView;
	@Bind(R.id.progressBar_battleJudgeLayout_secondDriverBadgePictureProgressBar)
	ProgressBar secondDriverPictureProgressBar;
	@Bind(R.id.imageView_battleJudgeLayout_secondDriverFlag)
	ImageView secondDriverFlag;
	@Bind(R.id.textView_battleJudgeLayout_secondDriverAdvantage)
	TextView secondDriverAdvantageTextView;
	@Bind(R.id.textView_battleJudgeLayout_secondDriverQualificationNumber)
	TextView secondDriverQualificationNumberTextView;
	@Bind(R.id.textView_battleJudgeLayout_secondDriverName)
	TextView secondDriverNameTextView;
	@Bind(R.id.textView_battleJudgeLayout_secondDriverCarModel)
	TextView secondDriverCarModelTextView;
	@Bind(R.id.textView_battleJudgeLayout_secondDriverCarHp)
	TextView secondDriverCarHpTextView;
	@Bind(R.id.imageView_battleJudgeLayout_secondDriverCarImage)
	ImageView secondDriverCarImageTextView;
	@Bind(R.id.progressBar_battleJudgeLayout_secondDriverCarImageProgressBar)
	ProgressBar secondDriverCarImageProgressBar;
	@Bind(R.id.button_battleJudgeLayout_secondDriverPositiveComments)
	Button secondDriverPositiveComments;
	@Bind(R.id.button_battleJudgeLayout_secondDriverNegativeComments)
	Button secondDriverNegativeComments;
	@Bind(R.id.textView_battleJudgeLayout_secondDriverStatus)
	TextView secondDriverStatusTextView;
	@Bind(R.id.rangeBar_battleJudgeLayout_points)
	RangeBar driverPointsRangeBar;
	@Bind(R.id.button_battleJudgeLayout_submit)
	Button submitButton;
	@Bind(R.id.recyclerView_battleJudgeLayout_runNumber)
	RecyclerView runNumberRecyclerView;
	ProgressDialog dialog;
	List<Comment> firstDriverSelectedPositiveCommentsFull;
	List<Comment> firstDriverSelectedNegativeCommentsFull;
	List<Comment> firstDriverSelectedPositiveComments;
	List<Comment> firstDriverSelectedNegativeComments;
	SparseBooleanArray firstDriverRetainedPositiveCheckedComments;
	SparseBooleanArray firstDriverRetainedNegativeCheckedComments;
	List<Comment> secondDriverSelectedPositiveCommentsFull;
	List<Comment> secondDriverSelectedNegativeCommentsFull;
	List<Comment> secondDriverSelectedPositiveComments;
	List<Comment> secondDriverSelectedNegativeComments;
	SparseBooleanArray secondDriverRetainedPositiveCheckedComments;
	SparseBooleanArray secondDriverRetainedNegativeCheckedComments;
	PlayoffBattleViewJudgeBattle playoffBattleViewJudgeBattle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_judge_battle);
		ButterKnife.bind(this);

		getBattleDetails();

		Long topNumber = getIntent().getLongExtra("topNumber", 0);
		if (topNumber != 0) {
			topNumberTextView.setText(String.valueOf(topNumber));
		} else {
			topNumberTextView.setText("FINALS");
		}

		firstDriverSelectedPositiveComments = new ArrayList<>();
		firstDriverSelectedNegativeComments = new ArrayList<>();

		firstDriverSelectedPositiveCommentsFull = new ArrayList<>();
		firstDriverSelectedNegativeCommentsFull = new ArrayList<>();

		firstDriverRetainedPositiveCheckedComments = new SparseBooleanArray();
		firstDriverRetainedNegativeCheckedComments = new SparseBooleanArray();

		secondDriverSelectedPositiveComments = new ArrayList<>();
		secondDriverSelectedNegativeComments = new ArrayList<>();

		secondDriverSelectedPositiveCommentsFull = new ArrayList<>();
		secondDriverSelectedNegativeCommentsFull = new ArrayList<>();

		secondDriverRetainedPositiveCheckedComments = new SparseBooleanArray();
		secondDriverRetainedNegativeCheckedComments = new SparseBooleanArray();
	}

	private void getBattleDetails() {
		dialog = ProgressDialog.show(this, "Loading", "Getting battle details...");

		Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

		RoundService roundService;
		roundService = retrofit.create(RoundService.class);

		Long battleId = getIntent().getLongExtra("battleId", 0);
		String token = getIntent().getStringExtra("token");

		Call<PlayoffBattleViewJudgeBattle> playoffBattleViewJudgeBattleCall = roundService.getBattleDetailsForJudge(token, battleId);

		playoffBattleViewJudgeBattleCall.enqueue(new retrofit.Callback<PlayoffBattleViewJudgeBattle>() {
			@Override
			public void onResponse(final Response<PlayoffBattleViewJudgeBattle> response, Retrofit retrofit) {
				if (response.code() == 200) {
					if (response.body() != null) {
						dialog.dismiss();
						playoffBattleViewJudgeBattle = response.body();

						ImageUtils.loadNormalImage(200, 200, JudgeBattleActivity.this,
								RestUrls.FILE + playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getProfilePicture(), firstDriverPictureImageView,
								new Callback() {
									@Override
									public void onSuccess() {
										firstDriverPictureProgressBar.setVisibility(View.GONE);
									}

									@Override
									public void onError() {
										firstDriverPictureProgressBar.setVisibility(View.GONE);
									}
								});

						ImageUtils.loadNormalImage(100, 100, JudgeBattleActivity.this,
								RestUrls.FILE + playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getCountry(), firstDriverFlag, new Callback() {
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

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getProfilePicture(), new NullCheck() {
							@Override
							public void onNotNull() {
								firstDriverNameTextView.setText(
										playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getFirstName() + " " + playoffBattleViewJudgeBattle.getDriver1()
												.getDriver()
												.getDriver()
												.getLastName());
							}

							@Override
							public void onNull() {
								firstDriverNameTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver1().getDriver().getRanking(), new NullCheck() {
							@Override
							public void onNotNull() {
								firstDriverQualificationNumberTextView.setText(String.valueOf(playoffBattleViewJudgeBattle.getDriver1().getDriver().getRanking()));
							}

							@Override
							public void onNull() {
								firstDriverQualificationNumberTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getDriverDetails().getMake(), new NullCheck() {
							@Override
							public void onNotNull() {
								firstDriverCarModelTextView.setText(playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getDriverDetails().getMake()
										+ " "
										+ playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getDriverDetails().getModel());
							}

							@Override
							public void onNull() {
								firstDriverNameTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
							@Override
							public void onNotNull() {
								firstDriverCarHpTextView.setText(
										String.valueOf(playoffBattleViewJudgeBattle.getDriver1().getDriver().getDriver().getDriverDetails().getHorsePower() + "HP"));
							}

							@Override
							public void onNull() {
								firstDriverCarHpTextView.setText("-HP");
							}
						});

						ImageUtils.loadNormalImage(200, 200, JudgeBattleActivity.this,
								RestUrls.FILE + playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getProfilePicture(), secondDriverPictureImageView,
								new Callback() {
									@Override
									public void onSuccess() {
										firstDriverPictureProgressBar.setVisibility(View.GONE);
									}

									@Override
									public void onError() {
										firstDriverPictureProgressBar.setVisibility(View.GONE);
									}
								});

						ImageUtils.loadNormalImage(100, 100, JudgeBattleActivity.this,
								RestUrls.FILE + playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getCountry(), secondDriverFlag, new Callback() {
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

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getFirstName(), new NullCheck() {
							@Override
							public void onNotNull() {
								secondDriverNameTextView.setText(
										playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getFirstName() + " " + playoffBattleViewJudgeBattle.getDriver2()
												.getDriver()
												.getDriver()
												.getLastName());
							}

							@Override
							public void onNull() {
								secondDriverNameTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver2().getDriver().getRanking(), new NullCheck() {
							@Override
							public void onNotNull() {
								secondDriverQualificationNumberTextView.setText(String.valueOf(playoffBattleViewJudgeBattle.getDriver2().getDriver().getRanking()));
							}

							@Override
							public void onNull() {
								secondDriverQualificationNumberTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getDriverDetails().getMake(), new NullCheck() {
							@Override
							public void onNotNull() {
								secondDriverCarModelTextView.setText(playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getDriverDetails().getMake()
										+ " "
										+ playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getDriverDetails().getModel());
							}

							@Override
							public void onNull() {
								secondDriverCarModelTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
							@Override
							public void onNotNull() {
								secondDriverCarHpTextView.setText(
										String.valueOf(playoffBattleViewJudgeBattle.getDriver2().getDriver().getDriver().getDriverDetails().getHorsePower() + "HP"));
							}

							@Override
							public void onNull() {
								secondDriverCarHpTextView.setText("-HP");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver1().isLead(), new NullCheck() {
							@Override
							public void onNotNull() {
								if (playoffBattleViewJudgeBattle.getDriver1().isLead()) {
									firstDriverStatusTextView.setText("LEAD");
									secondDriverStatusTextView.setText("CHASE");
								} else {
									firstDriverStatusTextView.setText("CHASE");
									secondDriverStatusTextView.setText("LEAD");
								}
							}

							@Override
							public void onNull() {
								firstDriverStatusTextView.setText("-");
								secondDriverStatusTextView.setText("-");
							}
						});

						Utils.nullCheck(playoffBattleViewJudgeBattle.getDriver1().isAdvantage(), new NullCheck() {
							@Override
							public void onNotNull() {
								if (playoffBattleViewJudgeBattle.getDriver1().isAdvantage()) {
									firstDriverAdvantageTextView.setVisibility(View.VISIBLE);
									secondDriverAdvantageTextView.setVisibility(View.GONE);
								} else {
									firstDriverAdvantageTextView.setVisibility(View.GONE);
									secondDriverAdvantageTextView.setVisibility(View.VISIBLE);
								}
							}

							@Override
							public void onNull() {
								firstDriverAdvantageTextView.setVisibility(View.GONE);
								secondDriverAdvantageTextView.setVisibility(View.GONE);
							}
						});

						driverPointsRangeBar.setThumbIndices(5);

						for (Comment comment : playoffBattleViewJudgeBattle.getAvailableComments()) {
							if (comment.getPositive()) {
								firstDriverSelectedPositiveComments.add(comment);
								secondDriverSelectedPositiveComments.add(comment);
							} else {
								firstDriverSelectedNegativeComments.add(comment);
								secondDriverSelectedNegativeComments.add(comment);
							}
						}

						LinearLayoutManager layoutManager = new LinearLayoutManager(JudgeBattleActivity.this, LinearLayoutManager.HORIZONTAL, false);
						runNumberRecyclerView.setLayoutManager(layoutManager);

						int totalRunNumber;

						if (((playoffBattleViewJudgeBattle.getRunNumber()) % 2) == 0) {
							totalRunNumber = playoffBattleViewJudgeBattle.getRunNumber();
						} else {
							totalRunNumber = playoffBattleViewJudgeBattle.getRunNumber() + 1;
						}
						runNumberRecyclerView.setAdapter(new BattleRunBubbleAdapter(totalRunNumber, playoffBattleViewJudgeBattle.getRunNumber(), false, null));

						DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

						int dpWidth = displayMetrics.widthPixels;
						int padding = (int) (10 * displayMetrics.density);
						int childWidth = (int) (70 * displayMetrics.density);
						int line = (int) (30 * displayMetrics.density);
						int paddingToBeAdded = dpWidth - childWidth * totalRunNumber - padding * 2 + line;

						runNumberRecyclerView.setPadding(paddingToBeAdded / 2, 0, 0, 0);
						runNumberRecyclerView.invalidate();
						runNumberRecyclerView.requestLayout();
					}
				} else if (response.code() == 412) {
					dialog.dismiss();
					DialogUtils.showAlertDialog(JudgeBattleActivity.this, "Attention!", "Please wait for the other judges to give their scores for the current run!",
							"Please wait for the other judges to give their scores for the first run!");
				} else {
					dialog.dismiss();
					Toast.makeText(JudgeBattleActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Throwable t) {
				t.printStackTrace();
			}
		});
	}

	@OnClick(R.id.button_battleJudgeLayout_firstDriverPositiveComments)
	public void firstDriverPositiveCommentsClick() {
		dimBackground();
		showPopupFirstDriver(findViewById(android.R.id.content), true);
	}

	@OnClick(R.id.button_battleJudgeLayout_firstDriverNegativeComments)
	public void firstDriverNegativeCommentsClick() {
		dimBackground();
		showPopupFirstDriver(findViewById(android.R.id.content), false);
	}

	@OnClick(R.id.button_battleJudgeLayout_secondDriverPositiveComments)
	public void secondDriverPositiveCommentsClick() {
		dimBackground();
		showPopupSecondsDriver(findViewById(android.R.id.content), true);
	}

	@OnClick(R.id.button_battleJudgeLayout_secondDriverNegativeComments)
	public void secondDriverNegativeCommentsClick() {
		dimBackground();
		showPopupSecondsDriver(findViewById(android.R.id.content), false);
	}

	@OnClick(R.id.button_battleJudgeLayout_submit)
	public void submitClick() {

		new AlertDialog.Builder(JudgeBattleActivity.this).setTitle("Warning!")
				.setMessage("Are you sure you want to submit the scores? You cannot change them after submission!")
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, int which) {
						// continue with delete
						PlayoffBattleSubmitJudgeBattle playoffBattleSubmitJudgeBattle = new PlayoffBattleSubmitJudgeBattle();
						playoffBattleSubmitJudgeBattle.setRoundId(playoffBattleViewJudgeBattle.getBattleRoundId());
						playoffBattleSubmitJudgeBattle.setRunId(playoffBattleViewJudgeBattle.getRunId());

						PlayoffBattleSubmitDriverJudgeAwards driver1 = new PlayoffBattleSubmitDriverJudgeAwards();
						PlayoffBattleSubmitDriverJudgeAwards driver2 = new PlayoffBattleSubmitDriverJudgeAwards();

						List<Comment> firstDriverAllCommentsList = new ArrayList<>();
						firstDriverAllCommentsList.addAll(firstDriverSelectedPositiveCommentsFull);
						firstDriverAllCommentsList.addAll(firstDriverSelectedNegativeCommentsFull);

						List<Comment> secondDriverAllCommentsList = new ArrayList<>();
						secondDriverAllCommentsList.addAll(secondDriverSelectedPositiveCommentsFull);
						secondDriverAllCommentsList.addAll(secondDriverSelectedNegativeCommentsFull);

						driver1.setComments(firstDriverAllCommentsList);
						driver1.setQualifiedDriverId(playoffBattleViewJudgeBattle.getDriver1().getDriver().getId());

						driver2.setComments(secondDriverAllCommentsList);
						driver2.setQualifiedDriverId(playoffBattleViewJudgeBattle.getDriver2().getDriver().getId());

						if (driverPointsRangeBar.getLeftIndex() <= 5) {
							driver1.setPoints(10 - driverPointsRangeBar.getLeftIndex());
							driver2.setPoints(driverPointsRangeBar.getLeftIndex());
						} else {
							if (driverPointsRangeBar.getLeftIndex() >= 5) {
								driver2.setPoints(10 - driverPointsRangeBar.getLeftIndex());
								driver1.setPoints(driverPointsRangeBar.getLeftIndex());
							}
						}

						playoffBattleSubmitJudgeBattle.setDriver1(driver1);
						playoffBattleSubmitJudgeBattle.setDriver2(driver2);

						Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

						RoundService roundService;
						roundService = retrofit.create(RoundService.class);

						SharedPreferences sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
						String token = sharedPreferences.getString("token", "token");
						Call<Void> roundCall =
								roundService.postJudgeBattleAwards(token, playoffBattleViewJudgeBattle.getBattleId(), playoffBattleSubmitJudgeBattle);

						roundCall.enqueue(new retrofit.Callback<Void>() {
							@Override
							public void onResponse(Response<Void> response, Retrofit retrofit) {
								dialog.dismiss();

								switch (response.code()) {
									case 200:
										Toast.makeText(JudgeBattleActivity.this, "Success!", Toast.LENGTH_SHORT).show();
										JudgeBattleActivity.this.onBackPressed();
										break;
									case 401:
										Toast.makeText(JudgeBattleActivity.this, "You must be authorized for this operation!", Toast.LENGTH_SHORT).show();
										break;
									case 403:
										Toast.makeText(JudgeBattleActivity.this, "You do not have the required permissions for this operation!", Toast.LENGTH_SHORT)
												.show();
										break;
									case 500:
										Toast.makeText(JudgeBattleActivity.this, "Something went wrong! Please try again!", Toast.LENGTH_SHORT).show();
										break;
									case 404:
										Toast.makeText(JudgeBattleActivity.this, "Requested resource not found!", Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onFailure(Throwable t) {
								Log.e("jhg", t.toString());
								dialog.dismiss();
							}
						});
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	}

	public void showPopupFirstDriver(final View anchorView, boolean positiveComments) {

		popupView = getLayoutInflater().inflate(R.layout.popup_judge_comments, null);

		int percentWidth = (int) ((getResources().getDisplayMetrics().widthPixels) * 0.80);
		int percentHeight = (int) (((getResources().getDisplayMetrics().heightPixels) - SizeUtils.getStatusBarHeight(this)) * 0.80);

		popupWindow = new PopupWindow(popupView, percentWidth, percentHeight);

		final Button completeButton = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsLayout_completeButton);
		final ListView commentsListView = (ListView) popupView.findViewById(R.id.listView_popupJudgeCommentsLayout_commentList);
		TextView header = (TextView) popupView.findViewById(R.id.textView_popupJudgeCommentsLayout_header);
		final EditText newCommentEditText = (EditText) popupView.findViewById(R.id.editText_popupJudgeCommentsLayout_newComment);
		Button addNewComment = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsLayout_addNewComment);

		commentsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		commentsListView.setItemsCanFocus(false);

		if (positiveComments) {
			header.setText("Select positive comments:");

			if (firstDriverSelectedPositiveCommentsFull.size() == 0) {
				completeButton.setText("DISMISS");
			} else {
				completeButton.setText("COMPLETE SELECTION");
			}

			commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(this, firstDriverSelectedPositiveComments, true));
			final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
			firstDriverRecheckPositiveItems(commentsListView);

			commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView commentItem = (TextView) view.findViewById(R.id.textView_commentSelectionListViewRow_comment);

					firstDriverRetainedPositiveCheckedComments = commentsListView.getCheckedItemPositions().clone();
					//for (int i = 0; i < checkedItems.size(); i++) {
					if (commentsListView.getCheckedItemPositions().get(position)) {
						commentItem.setTextColor(getResources().getColor(R.color.top16Top32Winner));
						if (!firstDriverSelectedPositiveCommentsFull.contains(castedAdapter.getItem(position))) {
							firstDriverSelectedPositiveCommentsFull.add(castedAdapter.getItem(position));
						}
					} else {
						commentItem.setTextColor(getResources().getColor(R.color.white));
						firstDriverSelectedPositiveCommentsFull.remove(castedAdapter.getItem(position));
					}

					if (firstDriverSelectedPositiveCommentsFull.size() == 0) {
						completeButton.setText("DISMISS");
					} else {
						completeButton.setText("COMPLETE SELECTION");
					}
				}
			});

			addNewComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (newCommentEditText.getText().toString().equals("")) {
						Toast.makeText(JudgeBattleActivity.this, "A new comment must have text!", Toast.LENGTH_SHORT).show();
					} else {
						Comment newComment = new Comment();
						newComment.setComment(newCommentEditText.getText().toString());
						newComment.setPositive(true);
						firstDriverSelectedPositiveCommentsFull.add(newComment);
						firstDriverSelectedPositiveComments.add(newComment);
						newCommentEditText.setText("");
						firstDriverRetainedPositiveCheckedComments.put(firstDriverSelectedPositiveComments.size() - 1, true);
						commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(JudgeBattleActivity.this, firstDriverSelectedPositiveComments, true));
						firstDriverRecheckPositiveItems(commentsListView);
						final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
						castedAdapter.notifyDataSetChanged();
						Toast.makeText(JudgeBattleActivity.this, "Comment added and selected!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} else {
			header.setText("Select negative comments:");

			if (firstDriverSelectedNegativeCommentsFull.size() == 0) {
				completeButton.setText("DISMISS");
			} else {
				completeButton.setText("COMPLETE SELECTION");
			}

			commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(JudgeBattleActivity.this, firstDriverSelectedNegativeComments, false));
			final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
			firstDriverRecheckNegativeItems(commentsListView);

			commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView commentItem = (TextView) view.findViewById(R.id.textView_commentSelectionListViewRow_comment);

					firstDriverRetainedNegativeCheckedComments = commentsListView.getCheckedItemPositions().clone();
					if (commentsListView.getCheckedItemPositions().get(position)) {
						commentItem.setTextColor(getResources().getColor(R.color.red));
						if (!firstDriverSelectedNegativeCommentsFull.contains(castedAdapter.getItem(position))) {
							firstDriverSelectedNegativeCommentsFull.add(castedAdapter.getItem(position));
						}
					} else {
						commentItem.setTextColor(getResources().getColor(R.color.white));
						firstDriverSelectedNegativeCommentsFull.remove(castedAdapter.getItem(position));
					}

					if (firstDriverSelectedNegativeCommentsFull.size() == 0) {
						completeButton.setText("DISMISS");
					} else {
						completeButton.setText("COMPLETE SELECTION");
					}
				}
			});

			addNewComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (newCommentEditText.getText().toString().equals("")) {
						Toast.makeText(JudgeBattleActivity.this, "A new comment must have text!", Toast.LENGTH_SHORT).show();
					} else {
						Comment newComment = new Comment();
						newComment.setComment(newCommentEditText.getText().toString());
						newComment.setPositive(false);
						firstDriverSelectedNegativeComments.add(newComment);
						firstDriverSelectedNegativeCommentsFull.add(newComment);
						newCommentEditText.setText("");
						firstDriverRetainedNegativeCheckedComments.put(firstDriverSelectedNegativeComments.size() - 1, true);
						commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(JudgeBattleActivity.this, firstDriverSelectedNegativeComments, false));
						firstDriverRecheckNegativeItems(commentsListView);
						final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
						castedAdapter.notifyDataSetChanged();
						Toast.makeText(JudgeBattleActivity.this, "Added! Now select it from the list.", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

		completeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				fadePopup.dismiss();
			}
		});

		popupWindow.setBackgroundDrawable(null);

		int location[] = new int[2];

		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under anchorView
		popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.update();
	}

	public void showPopupSecondsDriver(final View anchorView, boolean positiveComments) {

		popupView = getLayoutInflater().inflate(R.layout.popup_judge_comments, null);

		int percentWidth = (int) ((getResources().getDisplayMetrics().widthPixels) * 0.80);
		int percentHeight = (int) (((getResources().getDisplayMetrics().heightPixels) - SizeUtils.getStatusBarHeight(this)) * 0.80);

		popupWindow = new PopupWindow(popupView, percentWidth, percentHeight);

		final Button completeButton = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsLayout_completeButton);
		final ListView commentsListView = (ListView) popupView.findViewById(R.id.listView_popupJudgeCommentsLayout_commentList);
		TextView header = (TextView) popupView.findViewById(R.id.textView_popupJudgeCommentsLayout_header);
		final EditText newCommentEditText = (EditText) popupView.findViewById(R.id.editText_popupJudgeCommentsLayout_newComment);
		Button addNewComment = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsLayout_addNewComment);

		commentsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		commentsListView.setItemsCanFocus(false);

		if (positiveComments) {
			header.setText("Select positive comments:");

			if (secondDriverSelectedPositiveCommentsFull.size() == 0) {
				completeButton.setText("DISMISS");
			} else {
				completeButton.setText("COMPLETE SELECTION");
			}

			commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(this, secondDriverSelectedPositiveComments, true));
			final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
			secondDriverRecheckPositiveItems(commentsListView);

			commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView commentItem = (TextView) view.findViewById(R.id.textView_commentSelectionListViewRow_comment);

					secondDriverRetainedPositiveCheckedComments = commentsListView.getCheckedItemPositions().clone();
					//for (int i = 0; i < checkedItems.size(); i++) {
					if (commentsListView.getCheckedItemPositions().get(position)) {
						commentItem.setTextColor(getResources().getColor(R.color.top16Top32Winner));
						if (!secondDriverSelectedPositiveCommentsFull.contains(castedAdapter.getItem(position))) {
							secondDriverSelectedPositiveCommentsFull.add(castedAdapter.getItem(position));
						}
					} else {
						commentItem.setTextColor(getResources().getColor(R.color.white));
						secondDriverSelectedPositiveCommentsFull.remove(castedAdapter.getItem(position));
					}

					if (secondDriverSelectedPositiveCommentsFull.size() == 0) {
						completeButton.setText("DISMISS");
					} else {
						completeButton.setText("COMPLETE SELECTION");
					}
				}
			});

			addNewComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (newCommentEditText.getText().toString().equals("")) {
						Toast.makeText(JudgeBattleActivity.this, "A new comment must have text!", Toast.LENGTH_SHORT).show();
					} else {
						Comment newComment = new Comment();
						newComment.setComment(newCommentEditText.getText().toString());
						newComment.setPositive(true);
						secondDriverSelectedPositiveCommentsFull.add(newComment);
						secondDriverSelectedPositiveComments.add(newComment);
						newCommentEditText.setText("");
						secondDriverRetainedPositiveCheckedComments.put(secondDriverSelectedPositiveComments.size() - 1, true);
						commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(JudgeBattleActivity.this, secondDriverSelectedPositiveComments, true));
						secondDriverRecheckPositiveItems(commentsListView);
						final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
						castedAdapter.notifyDataSetChanged();
						Toast.makeText(JudgeBattleActivity.this, "Comment added and selected!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} else {
			header.setText("Select negative comments:");

			if (secondDriverSelectedNegativeComments.size() == 0) {
				completeButton.setText("DISMISS");
			} else {
				completeButton.setText("COMPLETE SELECTION");
			}

			commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(JudgeBattleActivity.this, secondDriverSelectedNegativeComments, false));
			final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
			secondDriverRecheckNegativeItems(commentsListView);

			commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView commentItem = (TextView) view.findViewById(R.id.textView_commentSelectionListViewRow_comment);

					secondDriverRetainedNegativeCheckedComments = commentsListView.getCheckedItemPositions().clone();
					if (commentsListView.getCheckedItemPositions().get(position)) {
						commentItem.setTextColor(getResources().getColor(R.color.red));
						if (!secondDriverSelectedNegativeCommentsFull.contains(castedAdapter.getItem(position))) {
							secondDriverSelectedNegativeCommentsFull.add(castedAdapter.getItem(position));
						}
					} else {
						commentItem.setTextColor(getResources().getColor(R.color.white));
						secondDriverSelectedNegativeCommentsFull.remove(castedAdapter.getItem(position));
					}

					if (secondDriverSelectedNegativeCommentsFull.size() == 0) {
						completeButton.setText("DISMISS");
					} else {
						completeButton.setText("COMPLETE SELECTION");
					}
				}
			});

			addNewComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (newCommentEditText.getText().toString().equals("")) {
						Toast.makeText(JudgeBattleActivity.this, "A new comment must have text!", Toast.LENGTH_SHORT).show();
					} else {
						Comment newComment = new Comment();
						newComment.setComment(newCommentEditText.getText().toString());
						newComment.setPositive(false);
						secondDriverSelectedPositiveComments.add(newComment);
						secondDriverSelectedNegativeCommentsFull.add(newComment);
						newCommentEditText.setText("");
						secondDriverRetainedNegativeCheckedComments.put(secondDriverSelectedPositiveComments.size() - 1, true);
						commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(JudgeBattleActivity.this, secondDriverSelectedPositiveComments, false));
						secondDriverRecheckNegativeItems(commentsListView);
						final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
						castedAdapter.notifyDataSetChanged();
						Toast.makeText(JudgeBattleActivity.this, "Added! Now select it from the list.", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

		completeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				fadePopup.dismiss();
			}
		});

		popupWindow.setBackgroundDrawable(null);

		int location[] = new int[2];

		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under anchorView
		popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.update();
	}

	private PopupWindow dimBackground() {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.judge_comments_fade_popup, (ViewGroup) findViewById(R.id.fadePopup));
		fadePopup = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
		fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);
		return fadePopup;
	}

	private void firstDriverRecheckPositiveItems(ListView commentsListView) {
		if (firstDriverRetainedPositiveCheckedComments != null) {
			for (int i = 0; i < firstDriverRetainedPositiveCheckedComments.size(); i++) {
				if (firstDriverRetainedPositiveCheckedComments.valueAt(i)) {
					commentsListView.setItemChecked(firstDriverRetainedPositiveCheckedComments.keyAt(i), true);
				} else {
					commentsListView.setItemChecked(firstDriverRetainedPositiveCheckedComments.keyAt(i), false);
				}
			}
		}
	}

	private void firstDriverRecheckNegativeItems(ListView commentsListView) {
		if (firstDriverRetainedNegativeCheckedComments != null) {
			for (int i = 0; i < firstDriverRetainedNegativeCheckedComments.size(); i++) {
				if (firstDriverRetainedNegativeCheckedComments.valueAt(i)) {
					commentsListView.setItemChecked(firstDriverRetainedNegativeCheckedComments.keyAt(i), true);
				} else {
					commentsListView.setItemChecked(firstDriverRetainedNegativeCheckedComments.keyAt(i), false);
				}
			}
		}
	}

	private void secondDriverRecheckPositiveItems(ListView commentsListView) {
		if (secondDriverRetainedPositiveCheckedComments != null) {
			for (int i = 0; i < secondDriverRetainedPositiveCheckedComments.size(); i++) {
				if (secondDriverRetainedPositiveCheckedComments.valueAt(i)) {
					commentsListView.setItemChecked(secondDriverRetainedPositiveCheckedComments.keyAt(i), true);
				} else {
					commentsListView.setItemChecked(secondDriverRetainedPositiveCheckedComments.keyAt(i), false);
				}
			}
		}
	}

	private void secondDriverRecheckNegativeItems(ListView commentsListView) {
		if (secondDriverRetainedNegativeCheckedComments != null) {
			for (int i = 0; i < secondDriverRetainedNegativeCheckedComments.size(); i++) {
				if (secondDriverRetainedNegativeCheckedComments.valueAt(i)) {
					commentsListView.setItemChecked(secondDriverRetainedNegativeCheckedComments.keyAt(i), true);
				} else {
					commentsListView.setItemChecked(secondDriverRetainedNegativeCheckedComments.keyAt(i), false);
				}
			}
		}
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
