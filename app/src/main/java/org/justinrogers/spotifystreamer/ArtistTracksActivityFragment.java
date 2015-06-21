package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistTracksActivityFragment extends Fragment {

    private static final String LOG_TAG = ArtistTracksActivity.class.getSimpleName();
    private String mArtistId;
    private TrackAdapter mTrackAdapter;

    public ArtistTracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_tracks, container, false);

        Intent intent = getActivity().getIntent();
        mArtistId = intent.getStringExtra(Intent.EXTRA_TEXT);
        ListView listView = (ListView) rootView.findViewById((R.id.artist_track_list));
        listView.setAdapter(mTrackAdapter);
        FetchArtistTracks tracksTask = new FetchArtistTracks();
        tracksTask.execute(mArtistId);

        return rootView;
    }

    public class FetchArtistTracks extends AsyncTask<String, Void, List<Track>> {
        private final String LOG_TAG = FetchArtistTracks.class.getSimpleName();

        @Override
        protected List<Track> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("country", "US");
            spotify.getArtistTopTrack(params[0], options, new Callback<Tracks>() {
                @Override
                public void success(Tracks tracks, Response response) {
                    Log.d(LOG_TAG, tracks.getClass().toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(LOG_TAG, error.toString());
                }
            });
            return null;
        }
    }
}
