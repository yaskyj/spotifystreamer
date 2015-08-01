/*
 * Copyright (C) 2015 Justin Rogers
 */

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

import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.models.Artist;


/**
 * Implements Customer Adapter for the Artists List produced from search
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {

    private final String LOG_TAG = ArtistAdapter.class.getSimpleName();

    public static class ViewHolder {
        @Bind(R.id.list_artist_image)
        ImageView artistImageView;
        @Bind(R.id.list_artist_name)
        TextView artistNameView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public ArtistAdapter(Context context, int resource, List<Artist> artistList) {
        super(context, resource, artistList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_artists, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Artist artist = getItem(position);
        viewHolder.artistNameView.setText(artist.name);
        if (artist.images.size() > 0) {
            int littleThumbnailPos = artist.images.size() - 1;
            Picasso.with(getContext()).load(artist.images.get(littleThumbnailPos).url).into(viewHolder.artistImageView);
        } else {
            Picasso.with(getContext()).load(R.drawable.default_image_substitute).into(viewHolder.artistImageView);
        }

        return convertView;
    }

    public List<Artist> getValues(List<Artist> artistList) {
        return artistList;
    }

}
