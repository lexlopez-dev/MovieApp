package com.lexandroid.movieapp.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;

import java.util.List;

//This class is for getting multiple results (Movies lists) - popular movies
public class AllSearchResponse {
    @SerializedName("total_results")
    @Expose
    private int total_count;


    @SerializedName("results")
    @Expose()
    private List<SearchModel> results;

    public int getTotal_count() {
        return total_count;
    }

    public List<SearchModel> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + results +
                '}';
    }
}
