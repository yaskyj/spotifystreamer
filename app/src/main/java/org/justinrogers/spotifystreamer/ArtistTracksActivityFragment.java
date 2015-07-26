/*
 * Copyright (C) 2015 Justin Rogers
 */

package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;


/**
 * Fragment that implements the Spotify api for Top Ten tracks from a given artist.
 */
public class ArtistTracksActivityFragment extends Fragment {

    private static final String LOG_TAG = ArtistTracksActivityFragment.class.getSimpleName();
    private String mArtistId;
    private TrackAdapter mTrackAdapter;
    private ArrayList<ParcelableTrackObject> tracksList;
    ListView trackList;
    private int mSelectedTrackId;

    public ArtistTracksActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("TrackList", tracksList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mArtistId = arguments.getString("artistId");
        }

        View rootView = inflater.inflate(R.layout.fragment_artist_tracks, container, false);

        if (savedInstanceState != null) {
            tracksList = savedInstanceState.getParcelable("TracksList");
            mSelectedTrackId = savedInstanceState.getInt("selectedTrackId");
        } else {
            tracksList = new ArrayList<ParcelableTrackObject>();
        }

        mTrackAdapter = new TrackAdapter(getActivity(), R.layout.list_item_tracks, tracksList);

        trackList = (ListView) rootView.findViewById((R.id.artist_track_list));
        trackList.setAdapter(mTrackAdapter);

        FetchArtistTracks tracksTask = new FetchArtistTracks();
        tracksTask.execute(mArtistId);

        trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedTrackId = position;
                ParcelableTrackObject selectedTrack = mTrackAdapter.getItem(mSelectedTrackId);
                ((TrackPlayerFragment.Callback) getActivity())
                        .onTrackSelected(selectedTrack);
            }
        });
        return rootView;
    }

    public ParcelableTrackObject loadNext() {
        ParcelableTrackObject selectedTrack = null;
        if (mSelectedTrackId < mTrackAdapter.getCount() - 1) {
            mSelectedTrackId = mSelectedTrackId + 1;
            selectedTrack = mTrackAdapter.getItem(mSelectedTrackId);
        }
        return selectedTrack;
    }

    public ParcelableTrackObject loadPrevious() {
        ParcelableTrackObject selectedTrack = null;
        if (mSelectedTrackId != 0) {
            mSelectedTrackId = mSelectedTrackId - 1;
            selectedTrack = mTrackAdapter.getItem(mSelectedTrackId);
        }
        return selectedTrack;
    }

    public class FetchArtistTracks extends AsyncTask<String, Void, Tracks> {
        private final String LOG_TAG = FetchArtistTracks.class.getSimpleName();

        /**
         * Creates Spotify service and uses the passed Artist id and gets the country from
         * the phone configuration
         */
        @Override
        protected Tracks doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Map<String, Object> options = new HashMap<String, Object>();
            String country = getActivity().getResources().getConfiguration().locale.getCountry();
            options.put("country", country);
            try {
                Tracks tracks = spotify.getArtistTopTrack(params[0], options);
                return tracks;
            } catch (RetrofitError error) {
                Log.e(LOG_TAG, error.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Tracks result) {
            if (result == null) {
                Toast.makeText(getActivity(), "There may be a problem with your connection.", Toast.LENGTH_SHORT).show();
            } else {
                if (result.tracks.isEmpty()) {
                    Toast.makeText(getActivity(), "This artist does not have any tracks available in your country.", Toast.LENGTH_SHORT).show();
                } else {
                    /**
                     * Creates the ParcelableTrackObject using the returned information from the API call
                     */
                    mTrackAdapter.clear();
                    for (Track track : result.tracks) {
                        String imageUrl = track.album.images.get(0).url;
                        ParcelableTrackObject parcelableTrackObject = new ParcelableTrackObject(track.name, track.album.name, imageUrl, track.preview_url, track.artists.get(0).name);
                        mTrackAdapter.add(parcelableTrackObject);
                    }
                }
            }
        }
    }
}
