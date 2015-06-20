package org.justinrogers.spotifystreamer;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by arkham on 6/20/15.
 */
public class TrackAdapter extends ArrayAdapter<Track> {

    private final String LOG_TAG = TrackAdapter.class.getSimpleName();

    public TrackAdapter(Context context, int resource, List<Track> trackList) {
        super(context, resource, trackList);
    }
}
