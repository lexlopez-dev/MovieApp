package com.lexandroid.movieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lexandroid.movieapp.adapters.ComingSoonRecyclerView;
import com.lexandroid.movieapp.adapters.OnSearchListener;
import com.lexandroid.movieapp.adapters.SearchRecyclerView;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.PersonModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.models.tv.TvModel;
import com.lexandroid.movieapp.request.Service;
import com.lexandroid.movieapp.utils.Credentials;
import com.lexandroid.movieapp.utils.TmdbApi;
import com.lexandroid.movieapp.viewmodels.SearchListViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComingSoonActivity extends AppCompatActivity implements OnSearchListener {

    private RecyclerView recyclerViewUpcoming;
    private ComingSoonRecyclerView searchUpcomingRecyclerViewAdapter;

    private SearchListViewModel searchListViewModelUpcoming;
    private LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);


        //Toolbar toolbar = findViewById(R.id.comingSoonToolbar);

        recyclerViewUpcoming = findViewById(R.id.comingSoonRecyclerView);
        searchListViewModelUpcoming = new ViewModelProvider(this).get(SearchListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();

        //Getting coming soon data
        searchListViewModelUpcoming.searchUpcomingApi(1);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setSelectedItemId(R.id.coming_soon);

        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.coming_soon:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.my_stuff:
//                        startActivity(new Intent(getApplicationContext(),MyStuffActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });
    }

    private void ObserveAnyChange() {
        searchListViewModelUpcoming.getResultsUpcoming().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                // Observing for any data change
                if(searchModels != null) {
                    for(SearchModel searchModel: searchModels) {
                        //Get the data in log
                        Log.v("Tag", "onChanges: " + searchModel.getMedia_type());

                        searchUpcomingRecyclerViewAdapter.setmResults(searchModels);
                    }
                }
            }
        });
    }

    private void ConfigureRecyclerView() {
        searchUpcomingRecyclerViewAdapter = new ComingSoonRecyclerView(this);

        recyclerViewUpcoming.setAdapter(searchUpcomingRecyclerViewAdapter);

        HorizontalLayout
                = new LinearLayoutManager(
                ComingSoonActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        //recyclerView.setLayoutManager(HorizontalLayout);  //use this instead of below to create longer views like netflix
        recyclerViewUpcoming.setLayoutManager(new GridLayoutManager(this, 3));


        // RecyclerView Pagination
        //Loading next pages of results
        recyclerViewUpcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)) {
                    //Here we need to display next search results
                    searchListViewModelUpcoming.searchNextPageUpcoming();
                }
            }
        });


    }


    @Override
    public void onSearchClick(int position) {
        Log.d("Debug", "Sending this id to search: " + searchUpcomingRecyclerViewAdapter.getSelected(position).getId());
        Intent intent = new Intent(this, MovieDetails.class);
        getRetrofitResponseAccordingToID(searchUpcomingRecyclerViewAdapter.getSelected(position).getId(), new ComingSoonActivity.GetRetrofitResponseAccordingToID() {
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
    }

    public interface GetRetrofitResponseAccordingToID {
        void onSuccess(@NonNull MovieModel movieModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToID(int id, @Nullable ComingSoonActivity.GetRetrofitResponseAccordingToID callbacks) {
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
                    MovieModel clickedMovieResult = response.body();
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
}