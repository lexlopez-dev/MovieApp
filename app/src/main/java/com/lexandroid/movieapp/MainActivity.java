package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.request.Service;
import com.lexandroid.movieapp.response.MovieSearchResponse;
import com.lexandroid.movieapp.utils.Credentials;
import com.lexandroid.movieapp.utils.MovieApi;
import com.lexandroid.movieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn;

    //ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


    }

    private void getRetrofitResponse() {
        Log.v("Debug", "Able to start getRetrofitResponse");

        MovieApi movieApi = Service.getMovieApi();

        Log.v("Debug", "Able to create movie api");

        Call<MovieSearchResponse> responseCall = movieApi
        .searchMovie(
                Credentials.API_KEY,
                "Jack Reacher",
                1);

        Log.v("Debug", "Able to get response call from movieApi.searchMovie");

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200) {
                    Log.v("Tag", "Response here: "+ response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie: movies) {
                        Log.v("Tag", "Movie Name: " + movie.getOriginal_title());
                    }
                }
                else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    private void getRetrofitResponseAccordingToID() {
        Log.v("Debug", "Able to start getRetrofitResponseAccordingToID");

        MovieApi movieApi = Service.getMovieApi();

        Call<MovieModel> responseCall = movieApi
                .getMovie(
                        550,
                        Credentials.API_KEY
                );

        Log.v("Debug", "Able to get response call from movieApi.getMovie");

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.v("Tag", "The Response: " + movie.getOriginal_title());
                } else {
                    try {
                        Log.v("Tag", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    // Observing any changes in the data
    private void observeAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change

            }
        });
    }

}

// https://api.themoviedb.org/3/movie/343611?api_key={api_key}