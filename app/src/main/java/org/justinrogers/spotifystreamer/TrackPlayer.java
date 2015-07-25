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

//    private final String LOG_TAG = TrackPlayer.class.getSimpleName();
//    private String mArtistName;
//    private String mTrackname;
//    private String mTrackUrl;
//    private String mImageUrl;
//    private String mAlbumName;
//    private TrackPlayerFragment trackPlayerFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_track_player);
//
//        if (savedInstanceState == null) {
//            Intent intent = getIntent();
//            if (intent != null) {
//                mArtistName = intent.getStringExtra("artistName");
//                mTrackname = intent.getStringExtra("trackName");
//                mTrackUrl = intent.getStringExtra("trackUrl");
//                mImageUrl = intent.getStringExtra("imageUrl");
//                mAlbumName = intent.getStringExtra("albumName");
//            }
//
//            trackPlayerFragment = new TrackPlayerFragment();
//
//            Bundle arguments = new Bundle();
//            arguments.putString("artistName", mArtistName);
//            arguments.putString("trackName", mTrackname);
//            arguments.putString("trackUrl", mTrackname);
//            arguments.putString("imageUrl", mImageUrl);
//            arguments.putString("albumName", mAlbumName);
//
//            trackPlayerFragment.setArguments(arguments);
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.track_player_container, trackPlayerFragment, "TrackPlayerFragment")
//                    .commit();
//
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_track_player, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
