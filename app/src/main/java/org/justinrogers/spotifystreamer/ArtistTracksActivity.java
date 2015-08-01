/*
 * Copyright (C) 2015 Justin Rogers
 */

package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/** Basic Activity for the Top Ten Artist tracks and activates the Activity Fragment*/
public class ArtistTracksActivity extends AppCompatActivity implements TrackPlayerFragment.Callback {

    private final String LOG_TAG = ArtistTracksActivity.class.getSimpleName();
    private String mArtistName;
    private String mSpotifyId;
    private ArtistTracksActivityFragment artistTracksActivityFragment;
    private TrackPlayerFragment trackPlayerFragment;
    private ArrayList<ParcelableTrackObject> tracksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_tracks);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                mArtistName = intent.getStringExtra("artistName");
                mSpotifyId = intent.getStringExtra("artistId");
                getSupportActionBar().setSubtitle(mArtistName);
            }

            trackPlayerFragment = new TrackPlayerFragment();
            artistTracksActivityFragment = new ArtistTracksActivityFragment();

            Bundle arguments = new Bundle();
            arguments.putString("artistName", mArtistName);
            arguments.putString("artistId", mSpotifyId);

            artistTracksActivityFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.artist_track_container, artistTracksActivityFragment, "ArtistTracksActivityFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artist_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrackSelected(ArrayList<ParcelableTrackObject> topTenTracks, int trackId) {
        Intent intent = new Intent(this, TrackPlayer.class);
        intent.putExtra("topTenTracks", topTenTracks);
        intent.putExtra("trackId", trackId);
        startActivity(intent);
    }

    @Override
    public void onNext() {
        trackPlayerFragment.onNext();
    }

    @Override
    public void onPrevious() {
        trackPlayerFragment.onPrevious();
    }

    public void play(View w) {
        trackPlayerFragment.play(w);
    }
}
