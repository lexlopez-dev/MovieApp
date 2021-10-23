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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        break;
                    case R.id.coming_soon:
                        Toast.makeText(SearchActivity.this, "Start Coming Soon Act", Toast.LENGTH_SHORT).show();
//                        Intent a = new Intent(MainActivity.this,ActivityOne.class);
//                        startActivity(a);
                        break;
                    case R.id.home:
                        Intent b = new Intent(SearchActivity.this, HomePageActivity.class);
                        startActivity(b);
                        break;
                    case R.id.my_stuff:
                        Toast.makeText(SearchActivity.this, "Start MyStuff Act", Toast.LENGTH_SHORT).show();
//                        Intent c = new Intent(HomePageActivity.this, SearchActivity.class);
//                        startActivity(b);
                        break;
                }
                return false;
            }
        });

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
                searchListViewModel.searchAllApi(s, 1);
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
            Log.d("Debug", "TV WAS CLICKED");
            Intent intentTv = new Intent(this, TvDetails.class);
            getRetrofitResponseAccordingToTvID(searchRecyclerViewAdapter.getSelected(position).getId(), new GetRetrofitResponseAccordingToTvID() {
                @Override
                public void onSuccess(@NonNull TvModel tvModel) {
                    intentTv.putExtra("tv", (Parcelable) tvModel);
                    startActivity(intentTv);
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    Log.d("Debug", "Error: Throwable = " + throwable);
                }
            });
        }else if(searchRecyclerViewAdapter.getSelected(position).getMedia_type().equals("person")){
            Log.d("Debug", "PERSON WAS CLICKED");
            Intent intentPerson = new Intent(this, PersonDetails.class);
            getRetrofitResponseAccordingToPersonID(searchRecyclerViewAdapter.getSelected(position).getId(), new GetRetrofitResponseAccordingToPersonID() {
                @Override
                public void onSuccess(@NonNull PersonModel personModel) {
                    intentPerson.putExtra("person", (Parcelable) personModel);
                    startActivity(intentPerson);
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    Log.d("Debug", "Error: Throwable = " + throwable);
                }
            });
        }


        //TODO: Need to adjust this method to check whether a movie or a tv show was clicked, and send to a TvDetails.class as needed
    }


    //***** FOR MOVIES ******//
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

    //********** FOR TV ************//
    public interface GetRetrofitResponseAccordingToTvID {
        void onSuccess(@NonNull TvModel tvModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToTvID(int id, @Nullable GetRetrofitResponseAccordingToTvID callbacks) {
        Log.v("Debug", "Able to start getRetrofitResponseAccordingToTvID");
        Log.v("Debug", "ID used: " + id);

        TmdbApi tmdbApi = Service.getTmdbApi();

        Call<TvModel> responseCallTv = tmdbApi
                .getSpecificTv(
                        id,
                        Credentials.API_KEY
                );

        Log.v("Debug", "Able to get response call from tvApi");

        responseCallTv.enqueue(new Callback<TvModel>() {
            @Override
            public void onResponse(Call<TvModel> call, Response<TvModel> response) {


                if (response.code() == 200) {
                    TvModel clickedTvResult = response.body(); //TODO: Complete this
                    callbacks.onSuccess(clickedTvResult);
                    Log.v("Debug", "The Response:" + clickedTvResult.getOriginal_name());
                } else if(response.code() == 401 || response.code() == 404){
                    try {
                        Log.v("Debug", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<TvModel> call, Throwable t) {
                Log.d("Debug", "Error throwable: " + t);
            }
        });
    }

    //********** FOR PEOPLE ************//
    public interface GetRetrofitResponseAccordingToPersonID {
        void onSuccess(@NonNull PersonModel personModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToPersonID(int id, @Nullable GetRetrofitResponseAccordingToPersonID callbacks) {
        Log.v("Debug", "Able to start getRetrofitResponseAccordingToPersonID");
        Log.v("Debug", "ID used: " + id);

        TmdbApi tmdbApi = Service.getTmdbApi();

        Call<PersonModel> responseCallPerson = tmdbApi
                .getSpecificPerson(
                        id,
                        Credentials.API_KEY
                );

        Log.v("Debug", "Able to get response call from tvApi");

        responseCallPerson.enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {


                if (response.code() == 200) {
                    PersonModel clickedPersonResult = response.body(); //TODO: Complete this
                    callbacks.onSuccess(clickedPersonResult);
                    Log.v("Debug", "The Response:" + clickedPersonResult.getName());
                } else if(response.code() == 401 || response.code() == 404){
                    try {
                        Log.v("Debug", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.d("Debug", "Error throwable: " + t);
            }
        });
    }

    @Override
    public void onCategoryClick(String category) {

    }


    //Navigation
}

// https://api.themoviedb.org/3/movie/343611?api_key={api_key}