package com.lexandroid.movieapp.utils;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.response.AllSearchResponse;
import com.lexandroid.movieapp.response.MovieResponse;
import com.lexandroid.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    //Search for movies
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET(Credentials.BASE_URL + "search/multi")
    Call<MovieSearchResponse> searchForMovies(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET(Credentials.BASE_URL + "search/multi")
    Call<AllSearchResponse> searchForAll(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") boolean adult //setting false to not get adult stuff
    );

    // Getting a movie with a specific ID
    // https://api.themoviedb.org/3/movie/343611?api_key={api_key}
    // Remember that movie_id = 550 is for Jack Reacher movie
    @GET(Credentials.BASE_URL + "movie/{movie_id}?")
    Call<MovieModel> getSpecificMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}
