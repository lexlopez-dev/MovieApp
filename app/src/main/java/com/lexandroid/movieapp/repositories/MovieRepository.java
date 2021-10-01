package com.lexandroid.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    //This class is acting as a movie repository

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    //These two are for getting next pages in a search
    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    //2 - calling the method in repository
    public void searchMovieApi(String query, int page) {
        mQuery = query;
        mPageNumber = page;

        movieApiClient.searchMoviesApi(query, page);
    }

    public void searchNextPage() {
        searchMovieApi(mQuery, mPageNumber + 1);
    }



}



