package com.lexandroid.movieapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.repositories.SliderRepository;

import java.util.List;

public class SliderListViewModel extends ViewModel {

    //This class is used for VIEWMODEL

    private SliderRepository sliderRepository;

    //Constructor
    public SliderListViewModel() {
        sliderRepository = SliderRepository.getInstance();
    }

    public LiveData<MovieModel> getMovies() {
        return sliderRepository.getMovies();
    }

    //For popular movies
    public LiveData<List<SearchModel>> getPopularMovies() {
        return sliderRepository.getPopularMovies();
    }

    //For now playing movies
    public LiveData<List<SearchModel>> getNowPlayingMovies() {
        return sliderRepository.getNowPlayingMovies();
    }

    //For popular Tv
    public LiveData<List<SearchModel>> getPopularTv() {
        return sliderRepository.getPopularTv();
    }

    public LiveData<List<SearchModel>> getMoviesTrendingDay() {
        return sliderRepository.getMoviesTrendingDay();
    }

    public LiveData<List<SearchModel>> getTvTrendingDay() {
        return sliderRepository.getTvTrendingDay();
    }

    public LiveData<List<SearchModel>> getMoviesTrendingWeek() {
        return sliderRepository.getMoviesTrendingWeek();
    }

    public LiveData<List<SearchModel>> getTvTrendingWeek() {
        return sliderRepository.getTvTrendingWeek();
    }

    public LiveData<List<SearchModel>> getTvOnAir() {
        return sliderRepository.getTvOnAir();
    }

    public LiveData<List<SearchModel>> getMoviesTopRated() {
        return sliderRepository.getMoviesTopRated();
    }

    public LiveData<List<SearchModel>> getTvTopRated() {
        return sliderRepository.getTvTopRated();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // 3 - Calling method in viewmodel
    public void searchMovieApi(int id) {
        sliderRepository.searchMovieApi(id);
    }

    //Search API for popular movies
    public void searchPopularMoviesApi(int page) {
        sliderRepository.searchPopularMoviesApi(page);
    }

    //Search API for now playing movies
    public void searchNowPlayingMoviesApi(int page) {
        sliderRepository.searchNowPlayingMoviesApi(page);
    }

    //Search API for popular Tv
    public void searchPopularTv(int page) {
        sliderRepository.searchPopularTv(page);
    }

    public void searchMoviesTrendingDay(int page) {
        sliderRepository.searchMoviesTrendingDay(page);
    }

    public void searchTvTrendingDay(int page) {
        sliderRepository.searchTvTrendingDay(page);
    }

    public void searchMoviesTrendingWeek(int page) {
        sliderRepository.searchMoviesTrendingWeek(page);
    }

    public void searchTvTrendingWeek(int page) {
        sliderRepository.searchTvTrendingWeek(page);
    }

    public void searchTvOnAir(int page) {
        sliderRepository.searchTvOnAir(page);
    }

    public void searchMoviesTopRated(int page) {
        sliderRepository.searchMoviesTopRated(page);
    }

    public void searchTvTopRated(int page) {
        sliderRepository.searchTvTopRated(page);
    }


}
