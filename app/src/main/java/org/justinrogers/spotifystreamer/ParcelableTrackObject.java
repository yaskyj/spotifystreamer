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

    public String mName;
    public String mAlbum;
    public String mThumbnail;
    public String mTrackUrl;

    /*
    * Takes a Name, Album, and Thumbnail input on object creation
    */
    public ParcelableTrackObject(String name, String album, String thumbnail, String trackUrl) {
        this.mName = name;
        this.mAlbum = album;
        this.mThumbnail = thumbnail;
        this.mTrackUrl = trackUrl;
    }

    private ParcelableTrackObject(Parcel in) {
        mName = in.readString();
        mAlbum = in.readString();
        mThumbnail = in.readString();
        mTrackUrl = in.readString();
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
        dest.writeString(this.mName);
        dest.writeString(this.mAlbum);
        dest.writeString(this.mThumbnail);
        dest.writeString(this.mTrackUrl);
    }

    public int describeContents() {
        return 0;
    }
}
