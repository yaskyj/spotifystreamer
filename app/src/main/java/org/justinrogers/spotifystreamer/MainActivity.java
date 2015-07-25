/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
    public void onTrackSelected(ParcelableTrackObject selectedTrack) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        trackPlayerFragment = new TrackPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TrackPlayerFragment.TRACK_INFO, selectedTrack);

        trackPlayerFragment.setArguments(bundle);

        trackPlayerFragment.show(getFragmentManager(), "dialog");
//        getSupportFragmentManager().beginTransaction()
//                .add(android.R.id.content, trackPlayerFragment)
//                .addToBackStack(null).commit();
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }
}
