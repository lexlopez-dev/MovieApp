package com.lexandroid.movieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.lexandroid.movieapp.adapters.OnSearchListener;
import com.lexandroid.movieapp.adapters.SearchRecyclerView;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.request.Service;
import com.lexandroid.movieapp.utils.Credentials;
import com.lexandroid.movieapp.utils.TmdbApi;
import com.lexandroid.movieapp.viewmodels.MovieListViewModel;
import com.lexandroid.movieapp.viewmodels.SearchListViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements OnSearchListener {

    //Before we run our app, we need to add the Network Security config

    // ** RecyclerView
    private RecyclerView recyclerView;
    private SearchRecyclerView searchRecyclerViewAdapter;


    //ViewModel
    private SearchListViewModel searchListViewModel;
    private LinearLayoutManager HorizontalLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "SearchActivity onCreate started");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.searchToolbar);

        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.searchRecyclerView);


        searchListViewModel = new ViewModelProvider(this).get(SearchListViewModel.class);


        //Calling the observers
        ConfigureRecyclerView();
        observeAnyChange();
        SetupSearchView();

        Log.d("Debug", "SearchActivity onCreate complete");

    }

    //Getting data from searchview & query the api to get the results (Movies)
    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.all_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //This should call a viewModel for not only movies, but tv as well
                searchListViewModel.searchAllApi(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            } //TODO: eventually set upQueryTextChange
        });
    }

//    // 4 - Calling Method in Main Activity
//    private void searchMovieApi(String query, int page) {
//        movieListViewModel.searchMovieApi(query, page);
//    }

    // ** 5 - Initializing recyclerView & adding data
    private void ConfigureRecyclerView() {
        searchRecyclerViewAdapter = new SearchRecyclerView( this);

        recyclerView.setAdapter(searchRecyclerViewAdapter);

        HorizontalLayout
                = new LinearLayoutManager(
                SearchActivity.this,
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
                    searchListViewModel.searchNextPage();
                }
            }
        });


    }

    // Observing any changes in the data
    private void observeAnyChange() {
        searchListViewModel.getResults().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                // Observing for any data change
                if(searchModels != null) {
                    for(SearchModel searchModel: searchModels) {
                        //Get the data in log
                        Log.v("Tag", "onChanges: " + searchModel.getMedia_type());

                        searchRecyclerViewAdapter.setmResults(searchModels);
                    }
                }
            }
        });
    }


    @Override
    public void onSearchClick(int position) {
        //Getting the ID of the clicked movie and sending it to movielistviewmodel
        Log.d("Debug", "Sending this id to search: " + searchRecyclerViewAdapter.getSelected(position).getId());
        if(searchRecyclerViewAdapter.getSelected(position).getMedia_type().equals("movie")) {
            Intent intent = new Intent(this, MovieDetails.class);
            getRetrofitResponseAccordingToID(searchRecyclerViewAdapter.getSelected(position).getId(), new GetRetrofitResponseAccordingToID() {
                @Override
                public void onSuccess(@NonNull MovieModel movieModel) {
                    intent.putExtra("movie", (Parcelable) movieModel);
                    startActivity(intent);
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    Log.d("Debug", "Error: Throwable = " + throwable);
                }
            });
        }else if(searchRecyclerViewAdapter.getSelected(position).getMedia_type().equals("tv")) {
            Toast.makeText(this, "TV Activity Will Open", Toast.LENGTH_SHORT).show();
        }else if(searchRecyclerViewAdapter.getSelected(position).getMedia_type().equals("person")){
            Toast.makeText(this, "Person Activity Will Open", Toast.LENGTH_SHORT).show();
        }


        //TODO: Need to adjust this method to check whether a movie or a tv show was clicked, and send to a TvDetails.class as needed
    }


    public interface GetRetrofitResponseAccordingToID {
        void onSuccess(@NonNull MovieModel movieModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToID(int id, @Nullable GetRetrofitResponseAccordingToID callbacks) {
        Log.v("Debug", "Able to start getRetrofitResponseAccordingToID");

        TmdbApi tmdbApi = Service.getTmdbApi();

        Call<MovieModel> responseCall = tmdbApi
                .getSpecificMovie(
                        id,
                        Credentials.API_KEY
                );

        Log.v("Debug", "Able to get response call from movieApi.getMovie");

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {


                if (response.code() == 200) {
                    MovieModel clickedMovieResult = response.body(); //TODO: Complete this
                    callbacks.onSuccess(clickedMovieResult);
                    Log.v("Debug", "The Response:" + clickedMovieResult.getOriginal_title());
                } else {
                    try {
                        Log.v("Debug", "Error: " + response.errorBody().string());
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


    @Override
    public void onCategoryClick(String category) {

    }


    //Navigation
}

// https://api.themoviedb.org/3/movie/343611?api_key={api_key}