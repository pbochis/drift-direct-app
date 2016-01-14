package com.iancuio.driftdirect.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.activities.RoundNavigationViewActivity;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

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

        Utils.loadNormalImage(600, 600, getActivity(), RestUrls.FILE + roundFull.getTrack().getLayout(), trackCartoonImageImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                trackProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
                trackProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
