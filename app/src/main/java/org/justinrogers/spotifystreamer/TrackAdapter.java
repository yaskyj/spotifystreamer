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

/*
 * Custom adapter for the Top 10 tracks. Uses the ParcelableTrackObject.
 */
public class TrackAdapter extends ArrayAdapter<ParcelableTrackObject> {

    private final String LOG_TAG = TrackAdapter.class.getSimpleName();

    public TrackAdapter(Context context, int resource, List<ParcelableTrackObject> trackList) {
        super(context, resource, trackList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_tracks, parent, false);
        }
        /*
        * Sets the various items to their respective views in the list layout.
        */
        ParcelableTrackObject track = getItem(position);
        TextView songNameView = (TextView) convertView.findViewById(R.id.list_song_name);
        songNameView.setText(track.mName);
        TextView albumNameView = (TextView) convertView.findViewById(R.id.list_album_name);
        albumNameView.setText(track.mAlbum);
        ImageView albumImageView = (ImageView) convertView.findViewById(R.id.list_track_image);
        Picasso.with(getContext()).load(track.mThumbnail).into(albumImageView);
        return convertView;
    }
}
