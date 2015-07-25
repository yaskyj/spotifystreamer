/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackPlayerFragment extends DialogFragment {

    public static final String TRACK_INFO = "selectedTrack";
    private ParcelableTrackObject trackToPlay;

    @Bind(R.id.player_artist_name)
    TextView artistName;
    @Bind(R.id.player_album_name)
    TextView albumName;
    @Bind(R.id.player_album_thumbnail)
    ImageView albumImage;
    @Bind(R.id.player_track_name)
    TextView trackName;
    @Bind(R.id.play_button)
    Button playButton;
    @Bind(R.id.next_button)
    Button nextButton;
    @Bind(R.id.previous_button)
    Button previousButton;
    @Bind(R.id.seekbar)
    SeekBar seekBar;

    private int trackProgress = 0;

    public TrackPlayerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player, container, false);

        if (savedInstanceState == null) {
            trackToPlay = getArguments().getParcelable(TRACK_INFO);
        } else {
            trackToPlay = savedInstanceState.getParcelable(TRACK_INFO);
        }

        ButterKnife.bind(this, rootView);
        artistName.setText(trackToPlay.mArtistName);
        trackName.setText(trackToPlay.mTrackName);
        albumName.setText(trackToPlay.mAlbum);
        Picasso.with(getActivity()).load(trackToPlay.mThumbnail).into(albumImage);

        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_Theme_AppCompat_Dialog);

        return dialog;
    }

    public interface Callback {
        public void onTrackSelected(ParcelableTrackObject selectedTrack);

        public void onNext();

        public void onPrevious();
    }
}
