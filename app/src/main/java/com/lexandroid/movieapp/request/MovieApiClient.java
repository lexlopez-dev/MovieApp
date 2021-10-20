package com.lexandroid.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lexandroid.movieapp.AppExecutors;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.response.MovieResponse;
import com.lexandroid.movieapp.response.MovieSearchResponse;
import com.lexandroid.movieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //LiveData for movies search (not currently being used)
    private MutableLiveData<MovieModel> mMovies;

    //LiveData for getPopularMovies
    private MutableLiveData<List<MovieModel>> mMoviesPopular;

    private static MovieApiClient instance;


    //Making Global RUNNABLE request
    private RetrieveMovieRunnable retrieveMovieRunnable;

    //Making Global RUNNABLE for popular
    private RetrievePopularMovieRunnable retrievePopularMovieRunnable;



    public static MovieApiClient getInstance() {
        if(instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
    }

    public LiveData<MovieModel> getMovies() {
        return mMovies;
    }

    public LiveData<List<MovieModel>> getPopularMovies() {
        return mMoviesPopular;
    }


    // 1 - This method that we are going to call through the classes
    public void searchMovieApi(int id) {
        Log.d("Debug", "Made it inside searchMovieApi inside MovieApiClient");
        if(retrieveMovieRunnable != null) {
            retrieveMovieRunnable = null;
        }

       retrieveMovieRunnable = new RetrieveMovieRunnable(id);
        Log.d("Debug", "Made it past RetrieveMovieRunnable inside MovieApiClient");
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for popular movies
    public void searchPopularMoviesApi(int page) {
        Log.d("Debug", "inside searchPopularMoviesApi inside MovieApiClient");
        if(retrievePopularMovieRunnable != null) {
            retrievePopularMovieRunnable = null;
        }

        retrievePopularMovieRunnable = new RetrievePopularMovieRunnable(page);
        Log.d("Debug", "Made it past RetrieveMovieRunnable inside MovieApiClient");
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrievePopularMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //Retrieving data from rest api
    //We have 2 types of Queries
    private class RetrieveMovieRunnable implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveMovieRunnable(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getSpecificMovie(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    MovieModel movieFound = ((MovieResponse)response.body()).getMovie();
                        // Sending data to live data
                        // PostValue: used for background thread
                        //setValue: not for background threat
                        mMovies.postValue(movieFound);

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        //Search method/query
        private Call<MovieSearchResponse> getMovies(String query, int page) {
                return Service.getTmdbApi().searchForMovies(
                        Credentials.API_KEY,
                        query,
                        page
                );
        }

        private Call<MovieSearchResponse> getPopularMovies(int page) {
            return Service.getTmdbApi().getPopularMovies(
                    Credentials.API_KEY,
                    page
            );
        }

        private Call<MovieModel> getSpecificMovie(int movieId) {
            return Service.getTmdbApi().getSpecificMovie(
                    movieId,
                    Credentials.API_KEY
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrievePopularMovieRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrievePopularMovieRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getPopularMovies(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(page == 1) {
                        mMoviesPopular.postValue(list);
                    }else {
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesPopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }

        }

        //Search method/query
        private Call<MovieSearchResponse> getMovies(String query, int page) {
            return Service.getTmdbApi().searchForMovies(
                    Credentials.API_KEY,
                    query,
                    page
            );
        }

        private Call<MovieSearchResponse> getPopularMovies(int page) {
            return Service.getTmdbApi().getPopularMovies(
                    Credentials.API_KEY,
                    page
            );
        }

        private Call<MovieModel> getSpecificMovie(int movieId) {
            return Service.getTmdbApi().getSpecificMovie(
                    movieId,
                    Credentials.API_KEY
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }




}














