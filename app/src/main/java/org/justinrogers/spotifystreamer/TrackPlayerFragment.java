/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackPlayerFragment extends DialogFragment implements View.OnClickListener {

    private static final String LOG_TAG = TrackPlayerFragment.class.getSimpleName();
    public static final String TRACKS_INFO = "topTenTracks";
    public static final String TRACK_ID = "trackId";
    private ParcelableTrackObject trackToPlay;
    public MediaPlayer mediaPlayer;
    int duration;
    int amountToUpdate;
    Timer mTimer = new Timer();
    private ArrayList<ParcelableTrackObject> tracksList;
    int trackId;

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

        ButterKnife.bind(this, rootView);

        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);

        if (savedInstanceState == null) {
            tracksList = getArguments().getParcelableArrayList(TRACKS_INFO);
            trackId = getArguments().getInt(TRACK_ID);
        } else {
            tracksList = savedInstanceState.getParcelableArrayList(TRACKS_INFO);
            trackId = savedInstanceState.getInt(TRACK_ID);
        }

        playerTasks();

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
        public void onTrackSelected(ArrayList<ParcelableTrackObject> topTenTracks, int trackId);

        public void onNext();

        public void onPrevious();
    }

    public void onNext(ParcelableTrackObject selectedTrack) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (trackId != tracksList.size()-1) {
            trackId = trackId + 1;
        }

        playerTasks();
    }

    public void onPrevious(ParcelableTrackObject selectedTrack) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (trackId != 0) {
            trackId = trackId - 1;
        }

        playerTasks();
    }

    public void play(View view) {
        playButton = (Button) view;
        if (mediaPlayer.isPlaying()) {
            playButton.setCompoundDrawablesRelativeWithIntrinsicBounds(android.R.drawable.ic_media_pause, 0, 0, 0);
            mediaPlayer.pause();
        } else {
            playButton.setCompoundDrawablesRelativeWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
            mediaPlayer.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_button:
                play(view);
                break;
            case R.id.previous_button:
                ((TrackPlayerFragment.Callback) getActivity()).onPrevious();
                break;
            case R.id.next_button:
                ((TrackPlayerFragment.Callback) getActivity()).onNext();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playerTasks() {
        trackToPlay = tracksList.get(trackId);

        artistName.setText(trackToPlay.mArtistName);
        trackName.setText(trackToPlay.mTrackName);
        albumName.setText(trackToPlay.mAlbum);
        Picasso.with(getActivity()).load(trackToPlay.mThumbnail).into(albumImage);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(trackToPlay.mTrackUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
