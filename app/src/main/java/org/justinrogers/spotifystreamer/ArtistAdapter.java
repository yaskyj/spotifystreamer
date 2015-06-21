package org.justinrogers.spotifystreamer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by arkham on 6/18/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {

    private final String LOG_TAG = ArtistAdapter.class.getSimpleName();

    public ArtistAdapter(Context context, int resource, List<Artist> artistList) {
        super(context, resource, artistList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_artists, parent,false);
        }
        Artist artist = getItem(position);
        TextView artistNameView = (TextView) convertView.findViewById(R.id.list_artist_name);
        artistNameView.setText(artist.name);
        ImageView artistImageView = (ImageView) convertView.findViewById(R.id.list_artist_image);
        if (artist.images.size() > 0) {
            int littleThumbnailPos = artist.images.size() - 1;
            Picasso.with(getContext()).load(artist.images.get(littleThumbnailPos).url).into(artistImageView);
        } else {
            Picasso.with(getContext()).load(R.drawable.default_image_substitute).into(artistImageView);
        }

        return convertView;
    }

}
