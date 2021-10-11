package com.lexandroid.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lexandroid.movieapp.AppExecutors;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.response.AllSearchResponse;
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

public class SearchApiClient {

    //LiveData
    private MutableLiveData<List<SearchModel>> mResults;

    private static SearchApiClient instance;


    //Making Global RUNNABLE qequest
    private RetrieveSearchesRunnable retrieveSearchesRunnable;



    public static SearchApiClient getInstance() {
        if(instance == null) {
            instance = new SearchApiClient();
        }
        return instance;
    }

    private SearchApiClient() {
        mResults = new MutableLiveData<>();
    }

    public LiveData<List<SearchModel>> getResults() {
        return mResults;
    }


    // 1 - This method that we are going to call through the classes
    public void searchAllApi(String query, int page) {

        if(retrieveSearchesRunnable != null) {
            retrieveSearchesRunnable = null;
        }

        retrieveSearchesRunnable = new RetrieveSearchesRunnable(query, page);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveSearchesRunnable);

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
    private class RetrieveSearchesRunnable implements Runnable {

        private String query;
        private int page;
        boolean cancelRequest;

        public RetrieveSearchesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getResults(query, page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((AllSearchResponse)response.body()).getResults());
                    if (page == 1) {
                        // Sending data to live data
                        // PostValue: used for background thread
                        //setValue: not for background threat
                        mResults.postValue(list);
                    }else {
                        List<SearchModel> currentResults = mResults.getValue();
                        currentResults.addAll(list);
                        mResults.postValue(currentResults);
                    }
                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mResults.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mResults.postValue(null);
            }

        }

        //Search method/query
        private Call<AllSearchResponse> getResults(String query, int page) {
                return Service.getTmdbApi().searchForAll(
                        Credentials.API_KEY,
                        query,
                        page
                );
        }


        // LEAVE BELOW FOR GETTING SPECIFIC MOVIES
        private Call<MovieResponse> getSpecificMovie(int movieId) {
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














