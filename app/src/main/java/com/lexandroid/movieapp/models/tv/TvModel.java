package com.lexandroid.movieapp.models.tv;

import android.os.Parcel;
import android.os.Parcelable;

public class TvModel implements Parcelable {
    private String backdrop_path, first_air_date, last_air_date, name, overview, poster_path;
    private int id, number_of_episodes, number_of_seasons, popularity, vote_count;
    private float vote_average;

    private Season[] seasons;

    public TvModel(String backdrop_path, String first_air_date, String last_air_date, String name, String overview, String poster_path, int id, int number_of_episodes, int number_of_seasons, int popularity, int vote_count, float vote_average, Season[] seasons) {
        this.backdrop_path = backdrop_path;
        this.first_air_date = first_air_date;
        this.last_air_date = last_air_date;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.id = id;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.seasons = seasons;
    }

    protected TvModel(Parcel in) {
        backdrop_path = in.readString();
        first_air_date = in.readString();
        last_air_date = in.readString();
        name = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        id = in.readInt();
        number_of_episodes = in.readInt();
        number_of_seasons = in.readInt();
        popularity = in.readInt();
        vote_count = in.readInt();
        vote_average = in.readFloat();
    }

    public static final Creator<TvModel> CREATOR = new Creator<TvModel>() {
        @Override
        public TvModel createFromParcel(Parcel in) {
            return new TvModel(in);
        }

        @Override
        public TvModel[] newArray(int size) {
            return new TvModel[size];
        }
    };

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public float getVote_average() {
        return vote_average;
    }

    public Season[] getSeasons() {
        return seasons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(backdrop_path);
        parcel.writeString(first_air_date);
        parcel.writeString(last_air_date);
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeString(poster_path);
        parcel.writeInt(id);
        parcel.writeInt(number_of_episodes);
        parcel.writeInt(number_of_seasons);
        parcel.writeInt(popularity);
        parcel.writeInt(vote_count);
        parcel.writeFloat(vote_average);
    }
}
