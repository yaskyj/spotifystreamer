package org.justinrogers.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arkham on 6/21/15.
 */
public class ParcelableTrackObject implements Parcelable {

    public String mName;
    public String mAlbum;
    public String mThumbnail;

    public ParcelableTrackObject(String name, String album, String thumbnail) {
        this.mName = name;
        this.mAlbum = album;
        this.mThumbnail = thumbnail;
    }

    private ParcelableTrackObject(Parcel in) {
        mName = in.readString();
        mAlbum = in.readString();
        mThumbnail = in.readString();
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
    }

    public int describeContents() {
        return 0;
    }
}
