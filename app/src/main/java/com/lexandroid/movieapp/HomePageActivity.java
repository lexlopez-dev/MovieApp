package com.lexandroid.movieapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lexandroid.movieapp.adapters.SliderRecyclerView;
import com.lexandroid.movieapp.adapters.OnSliderListener;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.models.tv.TvModel;
import com.lexandroid.movieapp.request.Service;
import com.lexandroid.movieapp.utils.Credentials;
import com.lexandroid.movieapp.utils.TmdbApi;
import com.lexandroid.movieapp.viewmodels.SliderListViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity implements OnSliderListener {

    //Before we run our app, we need to add the Network Security config

    // ** RecyclerView
    private RecyclerView recViewPopMovies, recViewNowPlayingMovies, recViewPopTv, recViewMoviesTrendingDay, recViewTvTrendingDay, recViewMoviesTrendingWeek, recViewTvTrendingWeek, recViewTvOnAir, recViewMoviesTopRated, recViewTvTopRated;
    private SliderRecyclerView sliderRecViewAdapterPopMovies, sliderRecViewAdapterNowPlayingMovies, sliderRecViewAdapterPopTv, sliderRecViewAdapterMoviesTrendingDay, sliderRecViewAdapterTvTrendingDay, sliderRecViewAdapterMoviesTrendingWeek, sliderRecViewAdapterTvTrendingWeek, sliderRecViewAdapterTvOnAir, sliderRecViewAdapterMoviesTopRated, sliderRecViewAdapterTvTopRated;

    //ViewModel
    private SliderListViewModel popMoviesSliderViewModel, nowPlayingMoviesSliderViewModel, popTvSliderViewModel, moviesTrendDaySliderViewModel, tvTrendDaySliderViewModel, moviesTrendWeekSliderViewModel, tvTrendWeekSliderViewModel, tvOnAirSliderViewModel, moviesTopSliderViewModel, tvTopSliderViewModel;
    private LinearLayoutManager HorizontalLayout, HorizontalLayout2, HorizontalLayout3, HorizontalLayout4, HorizontalLayout5, HorizontalLayout6, HorizontalLayout7, HorizontalLayout8, HorizontalLayout9, HorizontalLayout10, HorizontalLayout11;

    private ImageView mainImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        recViewPopMovies = findViewById(R.id.recView_popularMovies);
        recViewNowPlayingMovies = findViewById(R.id.recView_NowPlayingMovies);
        recViewPopTv = findViewById(R.id.recView_PopularTv);
        recViewMoviesTrendingDay = findViewById(R.id.recView_MoviesTrendingToday);
        recViewTvTrendingDay = findViewById(R.id.recView_TvTrendingToday);
        recViewMoviesTrendingWeek = findViewById(R.id.recView_MoviesTrendingWeek);
        recViewTvTrendingWeek = findViewById(R.id.recView_TvShowsTrendingWeek);
        recViewTvOnAir = findViewById(R.id.recView_TvShowsOnAir);
        recViewMoviesTopRated = findViewById(R.id.recView_MoviesTopRated);
        recViewTvTopRated = findViewById(R.id.recView_TvTopRated);

        popMoviesSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        nowPlayingMoviesSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        popTvSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        moviesTrendDaySliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvTrendDaySliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        moviesTrendWeekSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvTrendWeekSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvOnAirSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        moviesTopSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvTopSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);

        mainImg = findViewById(R.id.home_main_img);

        //Calling the observers
        ConfigureRecyclerView();
        ObserveAllSliderData();

        //Getting the data for popular
        popMoviesSliderViewModel.searchPopularMoviesApi(1);
        nowPlayingMoviesSliderViewModel.searchNowPlayingMoviesApi(1);
        popTvSliderViewModel.searchPopularTv(1);
        moviesTrendDaySliderViewModel.searchMoviesTrendingDay(2);
        tvTrendDaySliderViewModel.searchTvTrendingDay(2);
        moviesTrendWeekSliderViewModel.searchMoviesTrendingWeek(3);
        tvTrendWeekSliderViewModel.searchTvTrendingWeek(3);
        tvOnAirSliderViewModel.searchTvOnAir(1);
        moviesTopSliderViewModel.searchMoviesTopRated(1);
        tvTopSliderViewModel.searchTvTopRated(1);

        //**********Main IMG ****************
//        mainImg = findViewById(R.id.home_main_img);
//        String mainPoster = sliderRecViewAdapterPopMovies.getSelected(0).getPoster_path();
//        Glide.with(this)
//                .load(Credentials.IMG_URL + mainPoster)
//                .into(mainImg);

        //********* Bottom Nav ********************
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setSelectedItemId(R.id.home);


        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.coming_soon:
                        startActivity(new Intent(getApplicationContext(),ComingSoonActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.my_stuff:
                        startActivity(new Intent(getApplicationContext(),MyStuffActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        GetMainImg();



    }

    private void GetMainImg() {
        //SearchModel mainImg = popMoviesSliderViewModel.getPopularMovies().getValue().get(0);
        Log.d("Debug", "MAIN IMG Details: " + sliderRecViewAdapterPopMovies.getSelected(0));
//        mainImg = findViewById(R.id.home_main_img);
//        String mainPoster = sliderRecViewAdapterPopMovies.getSelected(0).getPoster_path();
//        Glide.with(this)
//                .load(Credentials.IMG_URL + mainPoster)
//                .into(mainImg);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void ObserveAllSliderData() {
        popMoviesSliderViewModel.getPopularMovies().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterPopMovies.setmMovies(searchModels, "sliderRecyclerViewAdapter");
                    }
                }
            }
        });

        nowPlayingMoviesSliderViewModel.getNowPlayingMovies().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterNowPlayingMovies.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        popTvSliderViewModel.getPopularTv().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterPopTv.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        moviesTrendDaySliderViewModel.getMoviesTrendingDay().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterMoviesTrendingDay.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvTrendDaySliderViewModel.getTvTrendingDay().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvTrendingDay.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        moviesTrendWeekSliderViewModel.getMoviesTrendingWeek().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterMoviesTrendingWeek.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvTrendWeekSliderViewModel.getTvTrendingWeek().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvTrendingWeek.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvOnAirSliderViewModel.getTvOnAir().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvOnAir.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        moviesTopSliderViewModel.getMoviesTopRated().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterMoviesTopRated.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvTopSliderViewModel.getTvTopRated().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvTopRated.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });
    }

    // ** 5 - Initializing recyclerView & adding data
    private void ConfigureRecyclerView() {

        HorizontalLayout
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout2
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout3
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout4
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout5
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout6
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout7
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout8
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout9
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout10
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout11
                = new LinearLayoutManager(
                HomePageActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);


        sliderRecViewAdapterPopMovies = new SliderRecyclerView(this);
        sliderRecViewAdapterNowPlayingMovies = new SliderRecyclerView(this);
        sliderRecViewAdapterPopTv = new SliderRecyclerView(this);
        sliderRecViewAdapterMoviesTrendingDay = new SliderRecyclerView(this);
        sliderRecViewAdapterTvTrendingDay = new SliderRecyclerView(this);
        sliderRecViewAdapterMoviesTrendingWeek = new SliderRecyclerView(this);
        sliderRecViewAdapterTvTrendingWeek = new SliderRecyclerView(this);
        sliderRecViewAdapterTvOnAir = new SliderRecyclerView(this);
        sliderRecViewAdapterMoviesTopRated = new SliderRecyclerView(this);
        sliderRecViewAdapterTvTopRated = new SliderRecyclerView(this);

        recViewPopMovies.setAdapter(sliderRecViewAdapterPopMovies);
        recViewNowPlayingMovies.setAdapter(sliderRecViewAdapterNowPlayingMovies);
        recViewPopTv.setAdapter(sliderRecViewAdapterPopTv);
        recViewMoviesTrendingDay.setAdapter(sliderRecViewAdapterMoviesTrendingDay);
        recViewTvTrendingDay.setAdapter(sliderRecViewAdapterTvTrendingDay);
        recViewMoviesTrendingWeek.setAdapter(sliderRecViewAdapterMoviesTrendingWeek);
        recViewTvTrendingWeek.setAdapter(sliderRecViewAdapterTvTrendingWeek);
        recViewTvOnAir.setAdapter(sliderRecViewAdapterTvOnAir);
        recViewMoviesTopRated.setAdapter(sliderRecViewAdapterMoviesTopRated);
        recViewTvTopRated.setAdapter(sliderRecViewAdapterTvTopRated);

        recViewPopMovies.setLayoutManager(HorizontalLayout);
        recViewNowPlayingMovies.setLayoutManager(HorizontalLayout2);
        recViewPopTv.setLayoutManager(HorizontalLayout3);
        recViewMoviesTrendingDay.setLayoutManager(HorizontalLayout4);
        recViewMoviesTrendingDay.setLayoutManager(HorizontalLayout5);
        recViewTvTrendingDay.setLayoutManager(HorizontalLayout6);
        recViewMoviesTrendingWeek.setLayoutManager(HorizontalLayout7);
        recViewTvTrendingWeek.setLayoutManager(HorizontalLayout8);
        recViewTvOnAir.setLayoutManager(HorizontalLayout11);
        recViewMoviesTopRated.setLayoutManager(HorizontalLayout9);
        recViewTvTopRated.setLayoutManager(HorizontalLayout10);

        Log.d("Debug", "MAIN IMG Details: " + sliderRecViewAdapterPopMovies.getSelected(0));
    }


    public SliderRecyclerView getSliderClicked(String recView) {
            if(recView.equals("recViewAdapterPopularMovies")) {
                return sliderRecViewAdapterPopMovies;
            }else if(recView.equals("recViewAdapterNowPlayingMovies")){
                return sliderRecViewAdapterNowPlayingMovies;
            }else if(recView.equals("recViewAdapterPopularTv")){
                return sliderRecViewAdapterPopTv;
            }else if(recView.equals("recViewAdapterMoviesTrendingToday")){
                return sliderRecViewAdapterMoviesTrendingDay;
            }else if(recView.equals("recViewAdapterTvTrendingToday")){
                return sliderRecViewAdapterTvTrendingDay;
            }else if(recView.equals("recViewAdapterMoviesTrendingWeek")){
                return sliderRecViewAdapterMoviesTrendingWeek;
            }else if(recView.equals("recViewAdapterTvShowsTrendingWeek")){
                return sliderRecViewAdapterTvTrendingWeek;
            }else if(recView.equals("recViewAdapterTvShowsOnAir")){
                return sliderRecViewAdapterTvOnAir;
            }else if(recView.equals("recViewAdapterMoviesTopRated")){
                return sliderRecViewAdapterMoviesTopRated;
            }else if(recView.equals("recViewAdapterTvTopRated")){
                return sliderRecViewAdapterTvTopRated;
            }
            return null;
    }

    @Override
    public void onTileClick(View v, int position) {
        Log.d("Debug", "MAIN IMG Details: " + sliderRecViewAdapterPopMovies.getSelected(0).getPoster_path());
        ViewParent parent = v.getParent();
        String recView = (((RecyclerView)parent).getTag().toString());

        SliderRecyclerView currentSlider = getSliderClicked(recView);

        SearchModel clickedTile = currentSlider.getSelected(position);

        if(clickedTile.getRelease_date() != null) {
            Log.d("Debug", "Made it inside onTileClick inside if statement");
            Intent intent = new Intent(this, MovieDetails.class);
            getRetrofitResponseAccordingToID(clickedTile.getId(), new GetRetrofitResponseAccordingToID() {
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
        } else {
            Log.d("Debug", "TV WAS CLICKED");
            Intent intentTv = new Intent(this, TvDetails.class);
            getRetrofitResponseAccordingToTvID(clickedTile.getId(), new GetRetrofitResponseAccordingToTvID() {
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
        }
    }




    public interface GetRetrofitResponseAccordingToID {
        void onSuccess(@NonNull MovieModel movieModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToID(int id, @Nullable HomePageActivity.GetRetrofitResponseAccordingToID callbacks) {
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

    public interface GetRetrofitResponseAccordingToTvID {
        void onSuccess(@NonNull TvModel tvModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToTvID(int id, @Nullable HomePageActivity.GetRetrofitResponseAccordingToTvID callbacks) {

        TmdbApi tmdbApi = Service.getTmdbApi();

        Call<TvModel> responseCall = tmdbApi
                .getSpecificTv(
                        id,
                        Credentials.API_KEY
                );

        Log.v("Debug", "Able to get response call from movieApi.getMovie");

        responseCall.enqueue(new Callback<TvModel>() {
            @Override
            public void onResponse(Call<TvModel> call, Response<TvModel> response) {


                if (response.code() == 200) {
                    TvModel clickedTvResult = response.body();
                    callbacks.onSuccess(clickedTvResult);
                } else {
                    try {
                        Log.v("Debug", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<TvModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCategoryClick(String category) {

    }


    //Navigation
}

// https://api.themoviedb.org/3/movie/343611?api_key={api_key}