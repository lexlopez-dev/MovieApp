package com.lexandroid.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lexandroid.movieapp.adapters.MovieRecyclerView;
import com.lexandroid.movieapp.adapters.OnMovieListener;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.viewmodels.MovieListViewModel;

import java.util.List;

public class MovieSearchActivity extends AppCompatActivity implements OnMovieListener {

    //Before we run our app, we need to add the Network Security config

    //Button btn;

    // ** RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;


    //ViewModel
    private MovieListViewModel movieListViewModel;
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        SetupSearchView();




        recyclerView = findViewById(R.id.movieRecyclerView1);


        //btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //Calling the observers
        ConfigureRecyclerView();
        observeAnyChange();
        SetupSearchView();

        //Testing the searchMovieApiMethod
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Displaying only the results of page 1
//                searchMovieApi("Fast", 1);
//            }
//        });




    }

    //Getting data from searchview & query the api to get the results (Movies)
    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                movieListViewModel.searchMovieApi(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

//    // 4 - Calling Method in Main Activity
//    private void searchMovieApi(String query, int page) {
//        movieListViewModel.searchMovieApi(query, page);
//    }

    // ** 5 - Initializing recyclerView & adding data
    private void ConfigureRecyclerView() {
        movieRecyclerViewAdapter = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerViewAdapter);

        HorizontalLayout
                = new LinearLayoutManager(
                MovieSearchActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        //recyclerView.setLayoutManager(HorizontalLayout);  //use this instead of below to create longer views like netflix
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        // RecyclerView Pagination
        //Loading next pages of results
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)) {
                    //Here we need to display next search results
                    movieListViewModel.searchNextPage();
                }
            }
        });


    }


//    private void getRetrofitResponse() {
//        Log.v("Debug", "Able to start getRetrofitResponse");
//
//        MovieApi movieApi = Service.getMovieApi();
//
//        Log.v("Debug", "Able to create movie api");
//
//        Call<MovieSearchResponse> responseCall = movieApi
//        .searchMovie(
//                Credentials.API_KEY,
//                "Jack Reacher",
//                1);
//
//        Log.v("Debug", "Able to get response call from movieApi.searchMovie");
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if(response.code() == 200) {
//                    Log.v("Tag", "Response here: "+ response.body().toString());
//
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//
//                    for (MovieModel movie: movies) {
//                        Log.v("Tag", "Movie Name: " + movie.getOriginal_title());
//                    }
//                }
//                else {
//                    try {
//                        Log.v("Tag", "Error" + response.errorBody().string());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void getRetrofitResponseAccordingToID() {
//        Log.v("Debug", "Able to start getRetrofitResponseAccordingToID");
//
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieModel> responseCall = movieApi
//                .getMovie(
//                        550,
//                        Credentials.API_KEY
//                );
//
//        Log.v("Debug", "Able to get response call from movieApi.getMovie");
//
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if(response.code() == 200) {
//                    MovieModel movie = response.body();
//                    Log.v("Tag", "The Response: " + movie.getOriginal_title());
//                } else {
//                    try {
//                        Log.v("Tag", "Error: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }

    // Observing any changes in the data
    private void observeAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change
                if(movieModels != null) {
                    for(MovieModel movieModel: movieModels) {
                        //Get the data in log
                        Log.v("Tag", "onChanges: " + movieModel.getOriginal_title());

                        movieRecyclerViewAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        //Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();

        //We need the ID of the movie in order to get its details
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelected(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }


    //Navigation
}

// https://api.themoviedb.org/3/movie/343611?api_key={api_key}