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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //LiveData
    private MutableLiveData<MovieModel> mMovies;

    private static MovieApiClient instance;


    //Making Global RUNNABLE qequest
    private RetrieveMovieRunnable retrieveMovieRunnable;



    public static MovieApiClient getInstance() {
        if(instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<MovieModel> getMovies() {
        return mMovies;
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














