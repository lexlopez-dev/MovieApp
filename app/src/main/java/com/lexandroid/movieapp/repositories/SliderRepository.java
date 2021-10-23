package com.lexandroid.movieapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.request.SliderApiClient;

import java.util.List;

public class SliderRepository {
    //This class is acting as a movie repository

    private static SliderRepository instance;

    private SliderApiClient sliderApiClient;

    //These two are for getting next pages in a search
    private int mId;

    public static SliderRepository getInstance(){
        if(instance == null) {
            instance = new SliderRepository();
        }
        return instance;
    }

    private SliderRepository() {
        sliderApiClient = SliderApiClient.getInstance();
    }

    public LiveData<MovieModel> getMovies() {
        return sliderApiClient.getMovies();
    }

    //For popular movies
    public LiveData<List<SearchModel>> getPopularMovies() {
        return sliderApiClient.getPopularMovies();
    }

    //For now playing movies
    public LiveData<List<SearchModel>> getNowPlayingMovies() {
        return sliderApiClient.getNowPlayingMovies();
    }

    //For popular Tv
    public LiveData<List<SearchModel>> getPopularTv() {
        return sliderApiClient.getPopularTv();
    }

    //For movies trending day
    public LiveData<List<SearchModel>> getMoviesTrendingDay() {
        return sliderApiClient.getMoviesTrendingDay();
    }

    //For tv trending day
    public LiveData<List<SearchModel>> getTvTrendingDay() {
        return sliderApiClient.getTvTrendingDay();
    }

    //For movies trending week
    public LiveData<List<SearchModel>> getMoviesTrendingWeek() {
        return sliderApiClient.getMoviesTrendingWeek();
    }

    //For tv trending week
    public LiveData<List<SearchModel>> getTvTrendingWeek() {
        return sliderApiClient.getTvTrendingWeek();
    }

    //For tv on air
    public LiveData<List<SearchModel>> getTvOnAir() {
        return sliderApiClient.getTvOnAir();
    }

    //For movies top rated
    public LiveData<List<SearchModel>> getMoviesTopRated() {
        return sliderApiClient.getMoviesTopRated();
    }

    //For tv top rated
    public LiveData<List<SearchModel>> getTvTopRated() {
        return sliderApiClient.getTvTopRated();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    //2 - calling the method in repository
    public void searchMovieApi(int id) {
        mId = id;
        sliderApiClient.searchMovieApi(id);
    }


    //For popular movies
    public void searchPopularMoviesApi(int page) {
        sliderApiClient.searchPopularMoviesApi(page);
    }

    //For now playing movies
    public void searchNowPlayingMoviesApi(int page) {
        sliderApiClient.searchNowPlayingMoviesApi(page);
    }

    //For now playing movies
    public void searchPopularTv(int page) {
        sliderApiClient.searchPopularTvApi(page);
    }

    //For movies trending day
    public void searchMoviesTrendingDay(int page) {
        sliderApiClient.searchMoviesTrendingDayApi(page);
    }

    //For tv trending day
    public void searchTvTrendingDay(int page) {
        sliderApiClient.searchTvTrendingDayApi(page);
    }

    //For movies trending week
    public void searchMoviesTrendingWeek(int page) {
        sliderApiClient.searchMoviesTrendingWeekApi(page);
    }

    //For tv trending week
    public void searchTvTrendingWeek(int page) {
        sliderApiClient.searchTvTrendingWeekApi(page);
    }

    //For tv on air
    public void searchTvOnAir(int page) {
        sliderApiClient.searchTvOnAirApi(page);
    }

    //For movies top rated
    public void searchMoviesTopRated(int page) {
        sliderApiClient.searchMoviesTopRatedApi(page);
    }

    //For tv top rated
    public void searchTvTopRated(int page) {
        sliderApiClient.searchTvTopRatedApi(page);
    }



}



