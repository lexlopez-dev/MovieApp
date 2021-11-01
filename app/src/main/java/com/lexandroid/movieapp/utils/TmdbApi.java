package com.lexandroid.movieapp.utils;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.PersonModel;
import com.lexandroid.movieapp.models.tv.TvModel;
import com.lexandroid.movieapp.response.AllSearchResponse;
import com.lexandroid.movieapp.response.SliderSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    //////////////////// SEARCH ALL /////////////////////////////////////////////
    @GET(Credentials.BASE_URL + "search/multi")
    Call<SliderSearchResponse> searchForMovies(
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


    ///////////////////////////// MOVIES ///////////////////////////////////////////

    // Getting a movie with a specific ID
    @GET(Credentials.BASE_URL + "movie/{movie_id}?")
    Call<MovieModel> getSpecificMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //Getting popular movies
    @GET(Credentials.BASE_URL + "movie/popular")
    Call<SliderSearchResponse> getPopularMovies(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    //Getting now playing movies
    @GET(Credentials.BASE_URL + "movie/now_playing")
    Call<SliderSearchResponse> getNowPlayingMovies(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    //Getting top rated movies
    @GET(Credentials.BASE_URL + "movie/top_rated")
    Call<SliderSearchResponse> getTopRatedMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    //Getting upcoming movies
    //Can add a region string to specify by country
    @GET(Credentials.BASE_URL + "movie/upcoming")
    Call<AllSearchResponse> getUpcomingMovies(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET(Credentials.BASE_URL + "trending/movie/day")
    Call<SliderSearchResponse> getTrendingMoviesDay(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET(Credentials.BASE_URL + "trending/movie/week")
    Call<SliderSearchResponse> getTrendingMoviesWeek(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );


    //////////////////////// TV /////////////////////////////////////////////////////

    @GET(Credentials.BASE_URL + "tv/{tv_id}?")
    Call<TvModel> getSpecificTv(
            @Path("tv_id") int tv_id,
            @Query("api_key") String apiKey
    );

    @GET(Credentials.BASE_URL + "trending/tv/day")
    Call<SliderSearchResponse> getTrendingTvDay(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET(Credentials.BASE_URL + "trending/tv/week")
    Call<SliderSearchResponse> getTrendingTvWeek(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET(Credentials.BASE_URL + "tv/popular")
    Call<SliderSearchResponse> getPopularTv(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET(Credentials.BASE_URL + "tv/on_the_air")
    Call<SliderSearchResponse> getTvOnAir(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET(Credentials.BASE_URL + "tv/top_rated")
    Call<SliderSearchResponse> getTvTopRated(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("region") String region
    );



    ////////////////////// PERSON ////////////////////////////////////////////////

    @GET(Credentials.BASE_URL + "person/{person_id}?")
    Call<PersonModel> getSpecificPerson(
            @Path("person_id") int person_id,
            @Query("api_key") String apiKey
    );


    /////////////////// DISCOVER /////////////////////////////////////////////

    //https://api.themoviedb.org/3/discover/movie?api_key=13403154d9f3f6fc07972f58977a650b&with_genres=27&region=US
    // This URL calls movies by genre and current popularity in the US, 27 = Action

}
