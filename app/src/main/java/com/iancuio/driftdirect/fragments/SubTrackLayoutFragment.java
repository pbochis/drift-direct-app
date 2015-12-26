package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.ChampionshipNavigationViewActivity;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.track.Track;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubTrackLayoutFragment extends Fragment {

    @Bind(R.id.textView_subTrackLayout_trackDescription)
    TextView trackDescriptionTextView;
    @Bind(R.id.imageView_subTrackLayout_trackCartoonImage)
    ImageView trackCartoonImageImageView;
    @Bind(R.id.progressBar_subTrackLayout_progressBar)
    ProgressBar trackProgressBar;

    Round roundFull;




    public SubTrackLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_track_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roundFull = ((RoundNavigationViewActivity)getActivity()).getRoundFull();

        trackDescriptionTextView.setText(roundFull.getTrack().getDescription());
        Picasso.with(getActivity()).load(RestUrls.FILE + roundFull.getTrack().getLayout()).noPlaceholder().into(trackCartoonImageImageView, new Callback() {
            @Override
            public void onSuccess() {
                trackProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });


    }
}
