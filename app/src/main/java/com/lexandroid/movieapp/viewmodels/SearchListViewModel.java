package com.lexandroid.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.repositories.MovieRepository;
import com.lexandroid.movieapp.repositories.SearchRepository;

import java.util.List;

public class SearchListViewModel extends ViewModel {

    //This class is used for VIEWMODEL

    private SearchRepository searchRepository;

    //Constructor
    public SearchListViewModel() {
        searchRepository = SearchRepository.getInstance();
    }

    public LiveData<List<SearchModel>> getResults() {
        return searchRepository.getResults();
    }


    // 3 - Calling method in viewmodel
    public void searchAllApi(String query, int page) {
        searchRepository.searchAllApi(query, page);
    }

    public void searchNextPage() {
        searchRepository.searchNextPage();
    }

}
