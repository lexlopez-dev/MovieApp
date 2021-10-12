package com.lexandroid.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieModel implements Parcelable {
    /*Model Class for our movies
    This grabs results from a movie and saves it to this class
    * */

    private int movie_id;
    private String original_title; //title
    private String poster_path; //full poster
    private String release_date;
    private String backdrop_path; //small image
    private int[] genre_ids; //array of integers
    private String overview;
    private float vote_average;
    private int vote_count;
    private int runtime;
    private String tagline;

    private String media_type;
    private String homepage;

    //Constructor
    public MovieModel(int movie_id, String original_title, String poster_path, String release_date, String backdrop_path, int[] genre_ids, String overview, float vote_average, int vote_count, int runtime, String media_type, String tagline, String homepage) {
        this.movie_id = movie_id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.runtime = runtime;

        this.media_type = media_type;
        this.tagline = tagline;
        this.homepage = homepage;
    }


    //Adding Parcelable implementation
    protected MovieModel(Parcel in) {
        movie_id = in.readInt();
        original_title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        backdrop_path = in.readString();
        genre_ids = in.createIntArray();
        overview = in.readString();
        vote_average = in.readFloat();
        vote_count = in.readInt();
        runtime = in.readInt();

        media_type = in.readString();
        tagline = in.readString();
        homepage = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    //////////////////////////////////////////////////////////
    //Getters
    public int getMovie_id() {
        return movie_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getRelease_year() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Date year = formatter.parse(getRelease_date());
        String formattedYear = formatter.format(year);
        return formattedYear;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getOverview() {
        return overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getRuntime() {

        int hours = runtime / 60; //since both are ints, you get an int
        int minutes = runtime % 60;
        String time = String.valueOf(hours) + " h " + String.valueOf(minutes) + " min";
        //System.out.printf("%d:%02d", hours, minutes);
        return time;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getTagline() {
        return tagline;
    }

    public String getHomepage() {
        return homepage;
    }

    //////////////////////////////////////////////////////////////
    //Implementing Parcelable methods below
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movie_id);
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(backdrop_path);
        parcel.writeIntArray(genre_ids);
        parcel.writeString(overview);
        parcel.writeFloat(vote_average);
        parcel.writeInt(vote_count);
        parcel.writeInt(runtime);
        parcel.writeString(media_type);
        parcel.writeString(tagline);
        parcel.writeString(homepage);
    }
}
