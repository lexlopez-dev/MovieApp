package com.lexandroid.movieapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.request.MovieApiClient;

public class MovieRepository {
    //This class is acting as a movie repository

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    //These two are for getting next pages in a search
    private int mId;

    public static MovieRepository getInstance(){
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<MovieModel> getMovies() {
        return movieApiClient.getMovies();
    }

    //2 - calling the method in repository
    public void searchMovieApi(int id) {
        mId = id;
        Log.d("Debug", "Made it inside searchMovieApi inside MovieRepository");
        movieApiClient.searchMovieApi(id);
    }

//    public void searchNextPage() {
//        searchMovieApi(id);
//    }



}



