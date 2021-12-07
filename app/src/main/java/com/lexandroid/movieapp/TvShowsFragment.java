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

public class TvShowsFragment extends Fragment implements OnSliderListener{

    private RecyclerView recViewPopMovies, recViewNowPlayingMovies, recViewPopTv, recViewMoviesTrendingDay, recViewTvTrendingDay, recViewMoviesTrendingWeek, recViewTvTrendingWeek, recViewTvOnAir, recViewMoviesTopRated, recViewTvTopRated;
    private SliderRecyclerView sliderRecViewAdapterPopMovies, sliderRecViewAdapterNowPlayingMovies, sliderRecViewAdapterPopTv, sliderRecViewAdapterMoviesTrendingDay, sliderRecViewAdapterTvTrendingDay, sliderRecViewAdapterMoviesTrendingWeek, sliderRecViewAdapterTvTrendingWeek, sliderRecViewAdapterTvOnAir, sliderRecViewAdapterMoviesTopRated, sliderRecViewAdapterTvTopRated;

    //ViewModel
    private SliderListViewModel popMoviesSliderViewModel, nowPlayingMoviesSliderViewModel, popTvSliderViewModel, moviesTrendDaySliderViewModel, tvTrendDaySliderViewModel, moviesTrendWeekSliderViewModel, tvTrendWeekSliderViewModel, tvOnAirSliderViewModel, moviesTopSliderViewModel, tvTopSliderViewModel;
    private LinearLayoutManager HorizontalLayout, HorizontalLayout2, HorizontalLayout3, HorizontalLayout4, HorizontalLayout5, HorizontalLayout6, HorizontalLayout7, HorizontalLayout8, HorizontalLayout9, HorizontalLayout10, HorizontalLayout11;

    private ImageView mainImg;

    private TextView txt1, txt2, txt3, txt4, txt5;

    NestedScrollView nestedScrollView;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tv_shows, container, false);

        recViewPopTv = view.findViewById(R.id.recView_PopularTv);
        recViewTvTrendingDay = view.findViewById(R.id.recView_TvTrendingToday);
        recViewTvTrendingWeek = view.findViewById(R.id.recView_TvShowsTrendingWeek);
        recViewTvOnAir = view.findViewById(R.id.recView_TvShowsOnAir);
        recViewTvTopRated = view.findViewById(R.id.recView_TvTopRated);

        popTvSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvTrendDaySliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvTrendWeekSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvOnAirSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);
        tvTopSliderViewModel = new ViewModelProvider(this).get(SliderListViewModel.class);

        mainImg = view.findViewById(R.id.home_main_img);

        txt1 = view.findViewById(R.id.textViewTV);
        txt2 = view.findViewById(R.id.textViewTV4);
        txt3 = view.findViewById(R.id.textViewTV2);
        txt4 = view.findViewById(R.id.textViewTV7);
        txt5 = view.findViewById(R.id.textViewTV10);

        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        txt3.setVisibility(View.GONE);
        txt4.setVisibility(View.GONE);
        txt5.setVisibility(View.GONE);


        nestedScrollView = view.findViewById(R.id.homeScrollView);

        //Calling the observers
        ConfigureRecyclerView();
        ObserveAllSliderData();

        //Getting the data for popular
        popTvSliderViewModel.searchPopularTv(1);
        tvTrendDaySliderViewModel.searchTvTrendingDay(2);
        tvTrendWeekSliderViewModel.searchTvTrendingWeek(3);
        tvOnAirSliderViewModel.searchTvOnAir(1);
        tvTopSliderViewModel.searchTvTopRated(1);
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


        popTvSliderViewModel.getPopularTv().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterPopTv.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                    String imgUrl = sliderRecViewAdapterPopTv.getSelected(0).getPoster_path();
                    GetMainImg(imgUrl);
                }
            }
        });

        tvTrendDaySliderViewModel.getTvTrendingDay().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvTrendingDay.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvTrendWeekSliderViewModel.getTvTrendingWeek().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvTrendingWeek.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvOnAirSliderViewModel.getTvOnAir().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModels) {
                if(searchModels != null) {
                    for (SearchModel searchModel: searchModels) {
                        sliderRecViewAdapterTvOnAir.setmMovies(searchModels, "sliderRecyclerViewAdapter2");
                    }
                }
            }
        });

        tvTopSliderViewModel.getTvTopRated().observe(getViewLifecycleOwner(), new Observer<List<SearchModel>>() {
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

        HorizontalLayout3
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout6
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);


        HorizontalLayout8
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout10
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        HorizontalLayout11
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);


        sliderRecViewAdapterPopTv = new SliderRecyclerView(this);
        sliderRecViewAdapterTvTrendingDay = new SliderRecyclerView(this);
        sliderRecViewAdapterTvTrendingWeek = new SliderRecyclerView(this);
        sliderRecViewAdapterTvOnAir = new SliderRecyclerView(this);
        sliderRecViewAdapterTvTopRated = new SliderRecyclerView(this);

        recViewPopTv.setAdapter(sliderRecViewAdapterPopTv);
        recViewTvTrendingDay.setAdapter(sliderRecViewAdapterTvTrendingDay);
        recViewTvTrendingWeek.setAdapter(sliderRecViewAdapterTvTrendingWeek);
        recViewTvOnAir.setAdapter(sliderRecViewAdapterTvOnAir);
        recViewTvTopRated.setAdapter(sliderRecViewAdapterTvTopRated);

        recViewPopTv.setLayoutManager(HorizontalLayout3);
        recViewTvTrendingDay.setLayoutManager(HorizontalLayout6);
        recViewTvTrendingWeek.setLayoutManager(HorizontalLayout8);
        recViewTvOnAir.setLayoutManager(HorizontalLayout11);
        recViewTvTopRated.setLayoutManager(HorizontalLayout10);

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
        //Log.d("Debug", "MAIN IMG Details: " + sliderRecViewAdapterPopMovies.getSelected(0).getPoster_path());
        ViewParent parent = v.getParent();
        String recView = (((RecyclerView)parent).getTag().toString());

        SliderRecyclerView currentSlider = getSliderClicked(recView);

        SearchModel clickedTile = currentSlider.getSelected(position);

        Log.d("Debug", "TV WAS CLICKED");
        Intent intentTv = new Intent(getActivity(), TvDetails.class);
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



    public interface GetRetrofitResponseAccordingToTvID {
        void onSuccess(@NonNull TvModel tvModel);

        void onError(@NonNull Throwable throwable);

    }

    private void getRetrofitResponseAccordingToTvID(int id, @Nullable GetRetrofitResponseAccordingToTvID callbacks) {

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

}