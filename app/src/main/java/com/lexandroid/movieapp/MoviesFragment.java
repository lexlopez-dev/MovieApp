package com.lexandroid.movieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lexandroid.movieapp.adapters.OnSliderListener;
import com.lexandroid.movieapp.adapters.SliderRecyclerView;
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

public class MoviesFragment extends Fragment implements OnSliderListener{
// Inflate the layout for this fragment

    private RecyclerView recViewPopMovies, recViewNowPlayingMovies, recViewPopTv, recViewMoviesTrendingDay, recViewTvTrendingDay, recViewMoviesTrendingWeek, recViewTvTrendingWeek, recViewTvOnAir, recViewMoviesTopRated, recViewTvTopRated;
    private SliderRecyclerView sliderRecViewAdapterPopMovies, sliderRecViewAdapterNowPlayingMovies, sliderRecViewAdapterPopTv, sliderRecViewAdapterMoviesTrendingDay, sliderRecViewAdapterTvTrendingDay, sliderRecViewAdapterMoviesTrendingWeek, sliderRecViewAdapterTvTrendingWeek, sliderRecViewAdapterTvOnAir, sliderRecViewAdapterMoviesTopRated, sliderRecViewAdapterTvTopRated;

    //ViewModel
    private SliderListViewModel popMoviesSliderViewModel, nowPlayingMoviesSliderViewModel, popTvSliderViewModel, moviesTrendDaySliderViewModel, tvTrendDaySliderViewModel, moviesTrendWeekSliderViewModel, tvTrendWeekSliderViewModel, tvOnAirSliderViewModel, moviesTopSliderViewModel, tvTopSliderViewModel;
    private LinearLayoutManager HorizontalLayout, HorizontalLayout2, HorizontalLayout3, HorizontalLayout4, HorizontalLayout5, HorizontalLayout6, HorizontalLayout7, HorizontalLayout8, HorizontalLayout9, HorizontalLayout10, HorizontalLayout11;

    private TextView txt1, txt2, txt3, txt4, txt5;

    private ImageView mainImg;

    NestedScrollView nestedScrollView;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movies, container, false);

        recViewPopMovies = view.findViewById(R.id.recView_popularMovies);
        recViewNowPlayingMovies = view.findViewById(R.id.recView_NowPlayingMovies);
        recViewMoviesTrendingDay = view.findViewById(R.id.recView_MoviesTrendingToday);
        recViewMoviesTrendingWeek = view.findViewById(R.id.recView_MoviesTrendingWeek);
        recViewMoviesTopRated = view.findViewById(R.id.recView_MoviesTopRated);

        popMoviesSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        nowPlayingMoviesSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        moviesTrendDaySliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        moviesTrendWeekSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        moviesTopSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);

        txt1 = view.findViewById(R.id.textView3);
        txt2 = view.findViewById(R.id.textView5);
        txt3 = view.findViewById(R.id.textView9);
        txt4 = view.findViewById(R.id.textView8);
        txt5 = view.findViewById(R.id.textView11);

        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        txt3.setVisibility(View.GONE);
        txt4.setVisibility(View.GONE);
        txt5.setVisibility(View.GONE);

        mainImg = view.findViewById(R.id.home_main_img);


        nestedScrollView = view.findViewById(R.id.homeScrollView);

        //Calling the observers
        ConfigureRecyclerView();
        ObserveAllSliderData();

        //Getting the data for popular
        popMoviesSliderViewModel.searchPopularMoviesApi(1);
        nowPlayingMoviesSliderViewModel.searchNowPlayingMoviesApi(2);
        moviesTrendDaySliderViewModel.searchMoviesTrendingDay(2);
        moviesTrendWeekSliderViewModel.searchMoviesTrendingWeek(3);
        moviesTopSliderViewModel.searchMoviesTopRated(1);
        return view;
    }


    private void GetMainImg(String imgUrl) {
        Glide.with(this)
                .asBitmap()
                .load(Credentials.BEST_IMG_URL + imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontTransform()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mainImg.setImageBitmap(resource);
                    }
                });


    }

    private void ObserveAllSliderData() {
        popMoviesSliderViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterPopMovies.setmMovies(searchModels, "sliderRecyclerViewAdapter");
                    }
                    Log.d("Debug", "MAIN IMG Details: " + sliderRecViewAdapterPopMovies.getSelected(0).getPoster_path());
                    String imgUrl = sliderRecViewAdapterPopMovies.getSelected(1).getPoster_path();
                    GetMainImg(imgUrl);
                }
            }
        });


        nowPlayingMoviesSliderViewModel.getNowPlayingMovies().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterNowPlayingMovies.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });


        moviesTrendDaySliderViewModel.getMoviesTrendingDay().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterMoviesTrendingDay.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });



        moviesTrendWeekSliderViewModel.getMoviesTrendingWeek().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterMoviesTrendingWeek.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });



        moviesTopSliderViewModel.getMoviesTopRated().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterMoviesTopRated.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });


    }

    // ** 5 - Initializing recyclerView & adding data
    private void ConfigureRecyclerView() {

        HorizontalLayout
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout2
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);


        HorizontalLayout5
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);


        HorizontalLayout7
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout9
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);


        sliderRecViewAdapterPopMovies = new SliderRecyclerView(this);
        sliderRecViewAdapterNowPlayingMovies = new SliderRecyclerView(this);
        sliderRecViewAdapterMoviesTrendingDay = new SliderRecyclerView(this);
        sliderRecViewAdapterMoviesTrendingWeek = new SliderRecyclerView(this);
        sliderRecViewAdapterMoviesTopRated = new SliderRecyclerView(this);

        recViewPopMovies.setAdapter(sliderRecViewAdapterPopMovies);
        recViewNowPlayingMovies.setAdapter(sliderRecViewAdapterNowPlayingMovies);
        recViewMoviesTrendingDay.setAdapter(sliderRecViewAdapterMoviesTrendingDay);
        recViewMoviesTrendingWeek.setAdapter(sliderRecViewAdapterMoviesTrendingWeek);
        recViewMoviesTopRated.setAdapter(sliderRecViewAdapterMoviesTopRated);

        recViewPopMovies.setLayoutManager(HorizontalLayout);
        recViewNowPlayingMovies.setLayoutManager(HorizontalLayout2);
        recViewMoviesTrendingDay.setLayoutManager(HorizontalLayout5);
        recViewMoviesTrendingWeek.setLayoutManager(HorizontalLayout7);
        recViewMoviesTopRated.setLayoutManager(HorizontalLayout9);

    }


    public SliderRecyclerView getSliderClicked(String recView) {
        if(recView.equals("recViewAdapterPopularMovies")) {
            return sliderRecViewAdapterPopMovies;
        }else if(recView.equals("recViewAdapterNowPlayingMovies")){
            return sliderRecViewAdapterNowPlayingMovies;
        }else if(recView.equals("recViewAdapterMoviesTrendingToday")){
            return sliderRecViewAdapterMoviesTrendingDay;
        }else if(recView.equals("recViewAdapterMoviesTrendingWeek")){
            return sliderRecViewAdapterMoviesTrendingWeek;
        }else if(recView.equals("recViewAdapterMoviesTopRated")){
            return sliderRecViewAdapterMoviesTopRated;
        }
        return null;
    }

    @Override
    public void onTileClick(View v, int position) {
        //Log.d("Debug", "MAIN IMG Details: " + sliderRecViewAdapterPopMovies.getSelected(0).getPoster_path());
        ViewParent parent = v.getParent();
        String recView = (((RecyclerView)parent).getTag().toString());

        SliderRecyclerView currentSlider = getSliderClicked(recView);

        SearchModel clickedTile = currentSlider.getSelected(position);

        Log.d("Debug", "Made it inside onTileClick inside if statement");
        Intent intent = new Intent(getActivity(), MovieDetails.class);
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
    }




    public interface GetRetrofitResponseAccordingToID {
        void onSuccess(@NonNull MovieModel movieModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToID(int id, @Nullable MoviesFragment.GetRetrofitResponseAccordingToID callbacks) {
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