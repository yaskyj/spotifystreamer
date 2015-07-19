/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackPlayerFragment extends Fragment {

    private String mTrackName;
    private String mAlbum;
    private String mThumbnail;
    private String mTrackUrl;
    private String mArtistName;

    public TrackPlayerFragment() {
    }

    public static class ViewHolder {
        @Bind(R.id.player_artist_name)
        TextView artistName;
        @Bind(R.id.player_album_name)
        TextView albumName;
        @Bind(R.id.player_album_thumbnail)
        ImageView albumImage;
        @Bind(R.id.player_track_name)
        TextView trackName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTrackName = arguments.getString("trackName");
            mAlbum = arguments.getString("albumName");
            mThumbnail = arguments.getString("imageUrl");
            mTrackUrl = arguments.getString("trackUrl");
            mArtistName = arguments.getString("artistName");
        }
        View rootView = inflater.inflate(R.layout.fragment_track_player, container, false);

        ViewHolder viewHolder = new ViewHolder(rootView);
        viewHolder.artistName.setText(mArtistName);
        viewHolder.trackName.setText(mTrackName);
        viewHolder.albumName.setText(mAlbum);
        Picasso.with(getActivity()).load(mThumbnail).into(viewHolder.albumImage);

        return rootView;
    }

}
