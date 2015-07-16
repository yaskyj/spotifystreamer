/*
 * Copyright (C) 2015 Justin Rogers
 */

package org.justinrogers.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.RetrofitError;
import android.widget.Toast;

/**
 * Fragment for populating the Artists List on the Spotify api search for artist name
 */
public class ArtistsSearchFragment extends Fragment {

    ListView artistView;

    private ArrayList<Artist> artistsList = new ArrayList<Artist>();

    private ArtistAdapter mArtistAdapter;

    private final String LOG_TAG = ArtistsSearchFragment.class.getSimpleName();

    @Bind(R.id.artist_search)
    SearchView searchView;

    public ArtistsSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (savedInstanceState != null) {
            artistsList = savedInstanceState.getParcelable("Artists");
        }
        mArtistAdapter = new ArtistAdapter(getActivity(), R.layout.list_item_artists, artistsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, rootView);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        FetchArtistsTask fetchArtistsTask = new FetchArtistsTask();
                        fetchArtistsTask.execute(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

        artistView = (ListView) rootView.findViewById(R.id.artist_search_list);
        artistView.setAdapter(mArtistAdapter);
        artistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String spotifyId = mArtistAdapter.getItem(position).id;
                String artistName = mArtistAdapter.getItem(position).name;
                ((Callback)getActivity())
                        .onItemSelected(spotifyId, artistName);
            }
        });
        return rootView;
    }

    public class FetchArtistsTask extends AsyncTask<String, Void, List<Artist>> {

        private final String LOG_TAG = FetchArtistsTask.class.getSimpleName();
        /** Creates Spotify service and calls the search api for text entered into EditText box*/
        @Override
        protected List<Artist> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();

            try {
                ArtistsPager artistsPager = spotify.searchArtists(params[0]);
                Log.d(LOG_TAG, artistsPager.getClass().toString());
                return artistsPager.artists.items;
            } catch (RetrofitError error) {
                Log.e(LOG_TAG, error.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Artist> result) {
            if (result == null) {
                Toast.makeText(getActivity(), "There may be a problem with your connection.", Toast.LENGTH_SHORT).show();
            } else {
                if (result.isEmpty()) {
                    Toast.makeText(getActivity(), "Artist not found. Please refine search term.", Toast.LENGTH_SHORT).show();
                } else {
                    mArtistAdapter.clear();
                    mArtistAdapter.addAll(result);
                }
            }
        }
    }

    public interface Callback {
        public void onItemSelected(String id, String name);
    }

}
