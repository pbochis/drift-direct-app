package com.iancuio.driftdirect.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class JudgeBattleActivity extends AppCompatActivity {

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
    TextView firstDriverQualificationNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_battle);
        ButterKnife.bind(this);
    }

}
