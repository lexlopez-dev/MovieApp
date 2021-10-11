package com.lexandroid.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;

public class SearchModel implements Parcelable {

    private int id;
    private String media_type;
    private String poster_path;

    public SearchModel(int id, String media_type, String poster_path) {
        this.id = id;
        this.media_type = media_type;
        this.poster_path = poster_path;
    }

    protected SearchModel(Parcel in) {
        id = in.readInt();
        media_type = in.readString();
        poster_path = in.readString();
    }

    public static final Creator<SearchModel> CREATOR = new Creator<SearchModel>() {
        @Override
        public SearchModel createFromParcel(Parcel in) {
            return new SearchModel(in);
        }

        @Override
        public SearchModel[] newArray(int size) {
            return new SearchModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(media_type);
        parcel.writeString(poster_path);
    }


}
