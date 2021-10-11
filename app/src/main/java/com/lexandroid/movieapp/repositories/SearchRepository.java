package com.lexandroid.movieapp.repositories;

import androidx.lifecycle.LiveData;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.request.MovieApiClient;
import com.lexandroid.movieapp.request.SearchApiClient;

import java.util.List;

public class SearchRepository {
    //This class is acting as a movie repository

    private static SearchRepository instance;

    private SearchApiClient searchApiClient;

    //These two are for getting next pages in a search
    private String mQuery;
    private int mPageNumber;

    public static SearchRepository getInstance(){
        if(instance == null) {
            instance = new SearchRepository();
        }
        return instance;
    }

    private SearchRepository() {
        searchApiClient = SearchApiClient.getInstance();
    }

    public LiveData<List<SearchModel>> getResults() {
        return searchApiClient.getResults();
    }

    //2 - calling the method in repository
    public void searchAllApi(String query, int page) {
        mQuery = query;
        mPageNumber = page;

        searchApiClient.searchAllApi(query, page);
    }

    public void searchNextPage() {
        searchAllApi(mQuery, mPageNumber + 1);
    }



}



