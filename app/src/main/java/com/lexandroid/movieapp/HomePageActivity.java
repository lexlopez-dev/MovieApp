package com.lexandroid.movieapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lexandroid.movieapp.adapters.MovieRecyclerView;
import com.lexandroid.movieapp.adapters.OnMovieListener;
import com.lexandroid.movieapp.viewmodels.MovieListViewModel;

//TODO: THIS CLASS IS A COPY OF MOVIESEARCHACTIVITY BEFORE ANY CHANGES, THIS WILL BE USED LATER ON AS MAIN HOME PAGE
public class HomePageActivity extends AppCompatActivity implements OnMovieListener {

    //Before we run our app, we need to add the Network Security config

    // ** RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;


    //ViewModel
    private MovieListViewModel movieListViewModel;
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.home_rec_view_1);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //Calling the observers
        ConfigureRecyclerView();
        //observeAnyChange();
        //SetupSearchView();

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
//    private void SetupSearchView() {
//        final SearchView searchView = findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                //This should call a viewModel for not only movies, but tv as well
//                movieListViewModel.searchMovieApi(s, 1);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            } //TODO: eventually set upQueryTextChange
//        });
//    }

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
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        //recyclerView.setLayoutManager(HorizontalLayout);  //use this instead of below to create longer views like netflix
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        // RecyclerView Pagination
        //Loading next pages of results
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)) {
//                    //Here we need to display next search results
//                    movieListViewModel.searchNextPage();
//                }
//            }
//        });


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

    @Override
    public void onMovieClick(int position) {
        //Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();

        //We need the ID of the movie in order to get its details
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelected(position));
        startActivity(intent);

        //TODO: Need to adjust this method to check whether a movie or a tv show was clicked, and send to a TvDetails.class as needed
    }

    @Override
    public void onCategoryClick(String category) {

    }


    //Navigation
}

// https://api.themoviedb.org/3/movie/343611?api_key={api_key}