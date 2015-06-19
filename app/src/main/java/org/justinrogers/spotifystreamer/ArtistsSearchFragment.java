package org.justinrogers.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by arkham on 6/18/15.
 */
public class ArtistsSearchFragment extends Fragment {

    ListView artistView;

    private ArrayList<Artist> artistsList = new ArrayList<Artist>();

    private ArtistAdapter mArtistAdapter;

    private final String LOG_TAG = ArtistsSearchFragment.class.getSimpleName();

    public ArtistsSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArtistAdapter = new ArtistAdapter(getActivity(), R.layout.list_item_artists, artistsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        artistView = (ListView) rootView.findViewById(R.id.artist_search_list);
        EditText artistText = (EditText) rootView.findViewById(R.id.artist_search);
        artistText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    EditText artistName = (EditText) getActivity().findViewById(R.id.artist_search);
                    FetchArtistsTask artistsTask = new FetchArtistsTask();
                    artistsTask.execute(artistName.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        artistView.setAdapter(mArtistAdapter);
        return rootView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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

    public class FetchArtistsTask extends AsyncTask<String, Void, List<Artist>> {

        private final String LOG_TAG = FetchArtistsTask.class.getSimpleName();

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
                Log.e("LOG_TAG", error.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Artist> result) {
            if (result.isEmpty()) {
                Toast.makeText(getActivity(), "Artist not found.", Toast.LENGTH_SHORT).show();
            } else {
                mArtistAdapter.clear();
                mArtistAdapter.addAll(result);
            }
        }



//        Toast.makeText(getApplicationContext(), artistName.getText().toString(), Toast.LENGTH_SHORT).show();
//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
