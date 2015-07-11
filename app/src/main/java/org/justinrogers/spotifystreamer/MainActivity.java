/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

/*
 * Basic MainActivity which also creates the Fragment for the Artist search
 */
public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.artist_fragment) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.artist_fragment, new ArtistsSearchFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

//        ArtistsSearchFragment artistsSearchFragment = (ArtistsSearchFragment) getSupportFragmentManager().findFragmentById(R.id.artist_fragment);

    }

}
