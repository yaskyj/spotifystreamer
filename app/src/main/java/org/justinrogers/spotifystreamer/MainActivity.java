/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/*
 * Basic MainActivity which also creates the Fragment for the Artist search
 */
public class MainActivity extends AppCompatActivity implements ArtistsSearchFragment.Callback, TrackPlayerFragment.Callback {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mTwoPane;
    private TrackPlayerFragment trackPlayerFragment;
    private ArtistTracksActivityFragment artistTracksActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.artist_track_container) != null) {

            mTwoPane = true;

        } else {
            mTwoPane = false;
        }

        ArtistsSearchFragment artistsSearchFragment = (ArtistsSearchFragment) getSupportFragmentManager().findFragmentById(R.id.artist_fragment);

    }

    @Override
    public void onItemSelected(String spotifyId, String name) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString("artistId", spotifyId);
            arguments.putString("artistName", name);

            artistTracksActivityFragment = new ArtistTracksActivityFragment();
            artistTracksActivityFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.artist_track_container, artistTracksActivityFragment, "ArtistTracksActivityFragment")
                    .commit();
        } else {
            Intent intent = new Intent(this, ArtistTracksActivity.class);
            intent.putExtra("artistId", spotifyId);
            intent.putExtra("artistName", name);
            startActivity(intent);
        }
    }

    @Override
    public void onTrackSelected(ArrayList<ParcelableTrackObject> topTenTracks, int trackId) {
        trackPlayerFragment = new TrackPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(TrackPlayerFragment.TRACKS_INFO, topTenTracks);
        bundle.putInt(TrackPlayerFragment.TRACK_ID, trackId);

        trackPlayerFragment.setArguments(bundle);

        if (mTwoPane) {
            trackPlayerFragment.show(getFragmentManager(), "dialog");
        } else {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, trackPlayerFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onNext() {
        trackPlayerFragment.onNext();
    }

    @Override
    public void onPrevious() {
        trackPlayerFragment.onPrevious();
    }

    public void play(View v) {
        trackPlayerFragment.play(v);
    }
}
