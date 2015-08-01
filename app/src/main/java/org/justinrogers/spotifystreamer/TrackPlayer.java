/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TrackPlayer extends ActionBarActivity {

    private final String LOG_TAG = TrackPlayer.class.getSimpleName();
    private TrackPlayerFragment trackPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_player);

        if (savedInstanceState == null) {
            Intent intent = getIntent();

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(TrackPlayerFragment.TRACKS_INFO, intent.getParcelableArrayListExtra("topTenTracks"));
            bundle.putInt(TrackPlayerFragment.TRACK_ID, intent.getIntExtra("trackId", 0));

            trackPlayerFragment = new TrackPlayerFragment();

            trackPlayerFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.track_player_container, trackPlayerFragment, "TrackPlayerFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track_player, menu);
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
                trackPlayerFragment.stop();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        trackPlayerFragment.stop();
    }
}
