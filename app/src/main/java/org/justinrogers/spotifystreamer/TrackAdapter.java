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
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by arkham on 6/20/15.
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
        ParcelableTrackObject track = getItem(position);
        TextView songNameView = (TextView) convertView.findViewById(R.id.list_song_name);
        songNameView.setText(track.mName);
        TextView albumNameView = (TextView) convertView.findViewById(R.id.list_album_name);
        albumNameView.setText(track.mAlbum);
        ImageView albumImageView = (ImageView) convertView.findViewById(R.id.list_track_image);
        Picasso.with(getContext()).load(track.mThumbnail).into(albumImageView);
//        Picasso.with(getContext()).load(track.album.images.get(littleThumbnailPos).url).into(albumImageView);
//        if (track.album.images.size() > 0) {
//            int littleThumbnailPos = track.album.images.size() - 1;
//            Picasso.with(getContext()).load(track.album.images.get(littleThumbnailPos).url).into(albumImageView);
//        } else {
//            Picasso.with(getContext()).load(R.drawable.default_image_substitute).into(albumImageView);
//        }
        return convertView;
    }
}
