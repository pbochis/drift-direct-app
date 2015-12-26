package com.iancuio.driftdirect.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetOfRulesFragment extends Fragment {

    @Bind(R.id.imageView_setOfRulesLayout_ruleImage)
    ImageView ruleImageImageView;
    @Bind(R.id.textView_setOfRulesLayout_ruleDescription)
    TextView ruleDescriptionTextView;
    @Bind(R.id.imageView_setOfRulesLayout_ruleImageBlack)
    ImageView ruleImageBlackImageView;
    @Bind(R.id.imageView_setOfRulesLayout_ruleImagePlay)
    ImageView ruleImagePlayImageView;
    @Bind(R.id.relativeLayout_setOfRulesLayout_ruleVideoContainer)
    RelativeLayout ruleVideoContainer;
    @Bind(R.id.progressBar_setOfRulesLayout_progressBar)
    ProgressBar imageProgressBar;


    Championship championshipFull;



    public SetOfRulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_set_of_rules, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        championshipFull = ((ChampionshipNavigationViewActivity)getActivity()).getChampionshipFull();

        Picasso.with(getActivity()).load(RestUrls.FILE + championshipFull.getBackgroundImage()).into(ruleImageImageView, new Callback() {
            @Override
            public void onSuccess() {
                imageProgressBar.setVisibility(View.GONE);
                ruleImagePlayImageView.setVisibility(View.VISIBLE);
                ruleImageBlackImageView.setVisibility(View.VISIBLE);
            }


            @Override
            public void onError() {

            }
        });

        ruleDescriptionTextView.setText(championshipFull.getRules().getRules());
    }

    @OnClick(R.id.imageView_setOfRulesLayout_ruleImagePlay)
    public void setRuleImagePlayImageViewClick () {
//        Intent internetIntent = new Intent(Intent.ACTION_WEB_SEARCH,
//                Uri.parse(championshipFull.getRules().getVideoUrl()));
////        internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
////        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getActivity().startActivity(internetIntent);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(championshipFull.getRules().getVideoUrl()));
        startActivity(browserIntent);
    }
}
