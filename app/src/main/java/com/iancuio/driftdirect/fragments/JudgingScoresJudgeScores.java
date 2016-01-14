package com.iancuio.driftdirect.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.adapters.listViewAdapters.JudgePointsAllocationAdapter;
import com.iancuio.driftdirect.adapters.listViewAdapters.RunJudgeSelectCommentsAdapter;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgeType;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.JudgeQualifierAwards;
import com.iancuio.driftdirect.customObjects.round.qualifier.QualifierJudge;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.AwardedPoints;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.JudgeAwardedPoints;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.service.QualifierService;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.HTTP;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgingScoresJudgeScores extends Fragment {

    @Bind(R.id.editText_judgingScoresJudgeScoresLayout_entrySpeed)
    EditText entrySpeedEditText;
    @Bind(R.id.textView_judgingScoresJudgeScoresLayout_judgeName)
    TextView judgeNameTextView;
    @Bind(R.id.listView_judgingScoresJudgeScoresLayout_pointsAllocationList)
    ListView pointsAllocationListListView;
    @Bind(R.id.textView_judgingScoresJudgeScoresLayout_judgeType)
    TextView judgeTypeTextView;
    @Bind(R.id.linearLayout_judgingScoresJudgeScoresLayout_entrySpeedContainer)
    LinearLayout entrySpeedContainerLinearLayout;
    @Bind(R.id.button_judgingScoresJudgeScoresLayout_submit)
    Button submitButton;
    @Bind(R.id.button_judgingScoresJudgeScoresLayout_positiveComments)
    Button positiveCommentsButton;
    @Bind(R.id.button_judgingScoresJudgeScoresLayout_negativeComments)
    Button negativeCommentsButton;

    Round roundFull;
    Championship championshipFull;
    static PopupWindow fadePopup;
    static View popupView;
    static PopupWindow popupWindow;

    QualifierJudge qualifierJudge;

    List<Comment> selectedPositiveCommentsFull;
    List<Comment> selectedNegativeCommentsFull;

    List<Comment> selectedPositiveComments;
    List<Comment> selectedNegativeComments;

    SparseBooleanArray retainedPositiveCheckedcomments;
    SparseBooleanArray retainedNegativeCheckedcomments;

    List<JudgePointsAllocation> judgePointsAllocationList;

    Float entrySpeed;

    ProgressDialog dialog;

    public JudgingScoresJudgeScores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_judging_scores_judge_scores, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((RoundNavigationViewActivity) getActivity()).setCurrentFragment(this);
        roundFull = ((RoundNavigationViewActivity) getActivity()).getRoundFull();

        championshipFull = ((RoundNavigationViewActivity) getActivity()).getChampionshipFull();

        selectedPositiveComments = new ArrayList<>();
        selectedNegativeComments = new ArrayList<>();

        selectedPositiveCommentsFull = new ArrayList<>();
        selectedNegativeCommentsFull = new ArrayList<>();

        retainedPositiveCheckedcomments = new SparseBooleanArray();
        retainedNegativeCheckedcomments = new SparseBooleanArray();

        getLoggedInUserDetails();

        overrideBackPressed();
    }

    private void getAllocationPoints(List<JudgePointsAllocation> judgePointsAllocationList) {
        pointsAllocationListListView.setAdapter(new JudgePointsAllocationAdapter(getActivity(), judgePointsAllocationList));
        Utils.setListViewHeightBasedOnItems(pointsAllocationListListView);
    }

    private void getLoggedInUserDetails() {

        dialog = ProgressDialog.show(getActivity(), "Loading", "Getting info...");

        Bundle bundle = getArguments();
        Long qualifierId = bundle.getLong("qualifierId");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "token");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

        QualifierService qualifierService;
        qualifierService = retrofit.create(QualifierService.class);

        Call<QualifierJudge> qualifierJudgeCall = qualifierService.getQualifierJudge(token, qualifierId);

        qualifierJudgeCall.enqueue(new retrofit.Callback<QualifierJudge>() {
            @Override
            public void onResponse(final Response<QualifierJudge> response, Retrofit retrofit) {

                if (response.body() == null) {
                    dialog.dismiss();
                    Utils.showAlertDialog(getActivity(), "Attention!", "Finished judging for this driver!", "Finished judging for this driver!");
                } else if (response.body().getRunId().equals(0L)) {
                    dialog.dismiss();
                    Utils.showAlertDialog(getActivity(), "Run already judged!", "Wait for the other judges to finish judging!", "Run already judged! Wait for the other judges to finish judging.");
                } else {
                    judgeNameTextView.setText(response.body().getJudge().getJudge().getFirstName() + " " + response.body().getJudge().getJudge().getLastName());
                    judgeTypeTextView.setText(response.body().getJudge().getTitle());

                    if (response.body().getJudge().getJudgeType().equals(JudgeType.STYLE)) {
                        entrySpeedContainerLinearLayout.setVisibility(View.VISIBLE);
                    }

                    judgePointsAllocationList = response.body().getJudge().getPointsAllocations();
                    getAllocationPoints(response.body().getJudge().getPointsAllocations());

                    qualifierJudge = response.body();
                    dialog.dismiss();

                    for (Comment comment : qualifierJudge.getAvailableComments()) {
                        if (comment.getPositive()) {
                            selectedPositiveComments.add(comment);
                        } else {
                            selectedNegativeComments.add(comment);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Log.e("championship", t.toString());

            }
        });
    }

    @OnClick(R.id.button_judgingScoresJudgeScoresLayout_positiveComments)
    public void setPositiveCommentsButtonClick() {
        dimBackground();
        showPopup(getView(), true);
    }

    @OnClick(R.id.button_judgingScoresJudgeScoresLayout_negativeComments)
    public void setNegativeCommentsButtonClick() {
        dimBackground();
        showPopup(getView(), false);
    }

    @OnClick(R.id.button_judgingScoresJudgeScoresLayout_submit)
    public void setSubmitButtonClick() {

        new AlertDialog.Builder(getActivity())
                .setTitle("Warning!")
                .setMessage("Are you sure you want to submit the scores? You cannot change them after submission!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        dialog = ProgressDialog.show(getActivity(), "Loading", "Uploading judging info...");
                        JudgeQualifierAwards judgeQualifierAwards = new JudgeQualifierAwards();

                        List<Comment> allCommentsList = new ArrayList<>();
                        allCommentsList.addAll(selectedPositiveCommentsFull);
                        allCommentsList.addAll(selectedNegativeCommentsFull);

                        List<JudgeAwardedPoints> judgeAwardedPointsList = new ArrayList<>();

                        for (int i = 0; i<judgePointsAllocationList.size(); i++) {
                            JudgeAwardedPoints awardedPoints = new JudgeAwardedPoints();
                            awardedPoints.setAwardedPoints(((JudgePointsAllocationAdapter)pointsAllocationListListView.getAdapter()).getPoints(i));
                            awardedPoints.setPointsAllocation(((JudgePointsAllocationAdapter)pointsAllocationListListView.getAdapter()).getItemId(i));
                            judgeAwardedPointsList.add(awardedPoints);
                        }

                        if (!entrySpeedEditText.getText().toString().trim().equals("")) {
                            entrySpeed = Float.valueOf(entrySpeedEditText.getText().toString());
                            judgeQualifierAwards.setEntrySpeed(entrySpeed);
                        }

                        judgeQualifierAwards.setComments(allCommentsList);
                        judgeQualifierAwards.setAwardedPoints(judgeAwardedPointsList);

                        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

                        QualifierService qualifierService;
                        qualifierService = retrofit.create(QualifierService.class);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "token");
                        Call<Void> judgeQualifierAwardsCall = qualifierService.postJudgeAwardedPoints(token, judgeQualifierAwards, qualifierJudge.getId(), qualifierJudge.getRunId());

                        final DialogInterface finalDialog = dialog;
                        judgeQualifierAwardsCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Response<Void> response, Retrofit retrofit) {
                                finalDialog.dismiss();

                                switch (response.code()) {
                                    case 200:
                                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                                        getActivity().onBackPressed();
                                        break;
                                    case 401:
                                        Toast.makeText(getActivity(), "You must be authorized for this operation!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 403:
                                        Toast.makeText(getActivity(), "You do not have the required permissions for this operation!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getActivity(), "Something went wrong! Please try again!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getActivity(), "Requested resource not found!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e("jhg", t.toString());
                                finalDialog.dismiss();
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

    public void showPopup(final View anchorView, boolean positiveComments) {

        popupView = getActivity().getLayoutInflater().inflate(R.layout.popup_judge_comments, null);

        int percentWidth = (int) ((getResources().getDisplayMetrics().widthPixels) * 0.80);
        int percentHeight = (int) (((getResources().getDisplayMetrics().heightPixels) - Utils.getStatusBarHeight(getActivity())) * 0.80);

        popupWindow = new PopupWindow(popupView,
                percentWidth, percentHeight);


//        final PopupWindow popupWindow = new PopupWindow(popupView,
//                percentWidth, percentHeight);

        final Button completeButton = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsLayout_completeButton);
        final ListView commentsListView = (ListView) popupView.findViewById(R.id.listView_popupJudgeCommentsLayout_commentList);
        TextView header = (TextView) popupView.findViewById(R.id.textView_popupJudgeCommentsLayout_header);
        final EditText newCommentEditText = (EditText) popupView.findViewById(R.id.editText_popupJudgeCommentsLayout_newComment);
        Button addNewComment = (Button) popupView.findViewById(R.id.button_popupJudgeCommentsLayout_addNewComment);


        commentsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        commentsListView.setItemsCanFocus(false);

        if (positiveComments) {
            header.setText("Select positive comments:");

            if (selectedPositiveCommentsFull.size() == 0) {
                completeButton.setText("DISMISS");
            } else {
                completeButton.setText("COMPLETE SELECTION");
            }

            commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(getActivity(), selectedPositiveComments, true));
            final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
            recheckPositiveItems(commentsListView);

            commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView commentItem = (TextView) view.findViewById(R.id.textView_commentSelectionListViewRow_comment);

                    retainedPositiveCheckedcomments = commentsListView.getCheckedItemPositions().clone();
                    //for (int i = 0; i < checkedItems.size(); i++) {
                        if (commentsListView.getCheckedItemPositions().get(position)) {
                            commentItem.setTextColor(getResources().getColor(R.color.top16Top32Winner));
                            if (!selectedPositiveCommentsFull.contains(castedAdapter.getItem(position))) {
                                selectedPositiveCommentsFull.add(castedAdapter.getItem(position));
                            }
                        } else {
                            commentItem.setTextColor(getResources().getColor(R.color.white));
                            selectedPositiveCommentsFull.remove(castedAdapter.getItem(position));
                        }

                    if (selectedPositiveCommentsFull.size() == 0) {
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
                        Toast.makeText(getActivity(), "A new comment must have text!", Toast.LENGTH_SHORT).show();
                    } else {
                        Comment newComment = new Comment();
                        newComment.setComment(newCommentEditText.getText().toString());
                        newComment.setPositive(true);
                        selectedPositiveCommentsFull.add(newComment);
                        selectedPositiveComments.add(newComment);
                        newCommentEditText.setText("");
                        retainedPositiveCheckedcomments.put(selectedPositiveComments.size()-1, true);
                        commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(getActivity(), selectedPositiveComments, true));
                        recheckPositiveItems(commentsListView);
                        final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
                        castedAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Comment added and selected!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            header.setText("Select negative comments:");

            if (selectedNegativeCommentsFull.size() == 0) {
                completeButton.setText("DISMISS");
            } else {
                completeButton.setText("COMPLETE SELECTION");
            }

            commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(getActivity(), selectedNegativeComments, false));
            final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
            recheckNegativeItems(commentsListView);

            commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView commentItem = (TextView) view.findViewById(R.id.textView_commentSelectionListViewRow_comment);

                    retainedNegativeCheckedcomments = commentsListView.getCheckedItemPositions().clone();
                        if (commentsListView.getCheckedItemPositions().get(position)) {
                            commentItem.setTextColor(getResources().getColor(R.color.red));
                            if (!selectedNegativeCommentsFull.contains(castedAdapter.getItem(position))) {
                                selectedNegativeCommentsFull.add(castedAdapter.getItem(position));
                            }
                        } else {
                            commentItem.setTextColor(getResources().getColor(R.color.white));
                            selectedNegativeCommentsFull.remove(castedAdapter.getItem(position));
                        }

                    if (selectedNegativeCommentsFull.size() == 0) {
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
                        Toast.makeText(getActivity(), "A new comment must have text!", Toast.LENGTH_SHORT).show();
                    } else {
                        Comment newComment = new Comment();
                        newComment.setComment(newCommentEditText.getText().toString());
                        newComment.setPositive(false);
                        selectedNegativeComments.add(newComment);
                        selectedNegativeCommentsFull.add(newComment);
                        newCommentEditText.setText("");
                        retainedNegativeCheckedcomments.put(selectedNegativeComments.size()-1, true);
                        commentsListView.setAdapter(new RunJudgeSelectCommentsAdapter(getActivity(), selectedNegativeComments, false));
                        recheckNegativeItems(commentsListView);
                        final RunJudgeSelectCommentsAdapter castedAdapter = ((RunJudgeSelectCommentsAdapter) commentsListView.getAdapter());
                        castedAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Added! Now select it from the list.", Toast.LENGTH_SHORT).show();
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

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.judge_comments_fade_popup,
                (ViewGroup) getActivity().findViewById(R.id.fadePopup));
        fadePopup = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);
        return fadePopup;
    }

    private void overrideBackPressed() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                boolean result = false;
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow = null;
                            result = true;
                        } else {
                            result = false;
                        }
                    }
                }
                return result;
            }
        } );
    }

    private void recheckPositiveItems(ListView commentsListView) {
        if (retainedPositiveCheckedcomments != null) {
            for (int i = 0; i < retainedPositiveCheckedcomments.size(); i++) {
                if (retainedPositiveCheckedcomments.valueAt(i)) {
                    commentsListView.setItemChecked(retainedPositiveCheckedcomments.keyAt(i), true);
                } else {
                    commentsListView.setItemChecked(retainedPositiveCheckedcomments.keyAt(i), false);
                }
            }
        }
    }

    private void recheckNegativeItems(ListView commentsListView) {
        if (retainedNegativeCheckedcomments != null) {
            for (int i = 0; i < retainedNegativeCheckedcomments.size(); i++) {
                if (retainedNegativeCheckedcomments.valueAt(i)) {
                    commentsListView.setItemChecked(retainedNegativeCheckedcomments.keyAt(i), true);
                } else {
                    commentsListView.setItemChecked(retainedNegativeCheckedcomments.keyAt(i), false);
                }
            }
        }
    }
}






