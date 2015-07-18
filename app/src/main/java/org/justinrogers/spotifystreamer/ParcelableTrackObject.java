/*
 * Copyright (C) 2015 Justin Rogers
 */
package org.justinrogers.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Creates Parcelable object for the Top Ten track information to be passed to the SavedInstanceState
 */
public class ParcelableTrackObject implements Parcelable {

    public String mTrackName;
    public String mAlbum;
    public String mThumbnail;
    public String mTrackUrl;
    public String mArtistName;

    /*
    * Takes a Name, Album, and Thumbnail input on object creation
    */
    public ParcelableTrackObject(String trackName, String album, String thumbnail, String trackUrl, String artistName) {
        this.mTrackName = trackName;
        this.mAlbum = album;
        this.mThumbnail = thumbnail;
        this.mTrackUrl = trackUrl;
        this.mArtistName = artistName;
    }

    private ParcelableTrackObject(Parcel in) {
        mTrackName = in.readString();
        mAlbum = in.readString();
        mThumbnail = in.readString();
        mTrackUrl = in.readString();
        mArtistName = in.readString();
    }

    public static final Creator<ParcelableTrackObject> CREATOR = new Creator<ParcelableTrackObject>() {
        @Override
        public ParcelableTrackObject createFromParcel(Parcel in) {
            return new ParcelableTrackObject(in);
        }

        @Override
        public ParcelableTrackObject[] newArray(int size) {
            return new ParcelableTrackObject[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTrackName);
        dest.writeString(this.mAlbum);
        dest.writeString(this.mThumbnail);
        dest.writeString(this.mTrackUrl);
        dest.writeString(this.mArtistName);
    }

    public int describeContents() {
        return 0;
    }
}
