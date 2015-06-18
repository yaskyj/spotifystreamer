package org.justinrogers.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by arkham on 6/18/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {

    public ArtistAdapter(Context context, int resource, List<Artist> artistList) {
        super(context, resource, artistList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_artists, null);
        }
        Artist artist = getItem(position);
        TextView artistNameView = (TextView) convertView.findViewById(R.id.list_artist_name);
        artistNameView.setText(artist.name);
//        if (artist.images.size() > 0) {
//            int littleThumbnailPos = artist.images.size() - 1;
//            Picasso.with(parent.getContext()).load(artist.images.get(littleThumbnailPos).url).into(viewHolder.thumbnail);
//        }

        return convertView;
    }

}
