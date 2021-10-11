package com.lexandroid.movieapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //This class is used for VIEWMODEL

    private MovieRepository movieRepository;

    //Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<MovieModel> getMovies() {
        return movieRepository.getMovies();
    }


    // 3 - Calling method in viewmodel
    public void searchMovieApi(int id) {
        Log.d("Debug", "Made it inside searchMovieApi inside of MovieListViewModel");
        movieRepository.searchMovieApi(id);
    }

//    public void searchNextPage() {
//        movieRepository.searchNextPage();
//    }

}
