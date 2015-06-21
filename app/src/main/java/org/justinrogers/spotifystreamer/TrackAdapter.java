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
public class TrackAdapter extends ArrayAdapter<Track> {

    private final String LOG_TAG = TrackAdapter.class.getSimpleName();

    public TrackAdapter(Context context, int resource, List<Track> trackList) {
        super(context, resource, trackList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_tracks, parent, false);
        }
        Track track = getItem(position);
        TextView songNameView = (TextView) convertView.findViewById(R.id.list_song_name);
        songNameView.setText(track.name);
        TextView albumNameView = (TextView) convertView.findViewById(R.id.list_album_name);
        albumNameView.setText(track.album.name);
        ImageView albumImageView = (ImageView) convertView.findViewById(R.id.list_track_image);
        if (track.album.images.size() > 0) {
            int littleThumbnailPos = track.album.images.size() - 1;
            Picasso.with(getContext()).load(track.album.images.get(littleThumbnailPos).url).into(albumImageView);
        } else {
            Picasso.with(getContext()).load(R.drawable.abc_list_pressed_holo_dark).into(albumImageView);
        }
        return convertView;
    }
}
