package com.lexandroid.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lexandroid.movieapp.AppExecutors;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.response.MovieResponse;
import com.lexandroid.movieapp.response.SliderSearchResponse;
import com.lexandroid.movieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

public class SliderApiClient {

    //LiveData for movies search (not currently being used)
    private MutableLiveData<MovieModel> mMovies;

    String region = "US";

    //LiveData
    private MutableLiveData<List<SearchModel>> mMoviesPopular;
    private MutableLiveData<List<SearchModel>> mMoviesNowPlaying;
    private MutableLiveData<List<SearchModel>> mTvPopular;
    private MutableLiveData<List<SearchModel>> mMoviesTrendingDay;
    private MutableLiveData<List<SearchModel>> mTvTrendingDay;
    private MutableLiveData<List<SearchModel>> mMoviesTrendingWeek;
    private MutableLiveData<List<SearchModel>> mTvTrendingWeek;
    private MutableLiveData<List<SearchModel>> mTvOnAir;
    private MutableLiveData<List<SearchModel>> mMoviesTopRated;
    private MutableLiveData<List<SearchModel>> mTvTopRated;


    private static SliderApiClient instance;



    private RetrieveMovieRunnable retrieveMovieRunnable;
    private RetrievePopularMovieRunnable retrievePopularMovieRunnable;
    private RetrieveNowPlayingMoviesRunnable retrieveNowPlayingMoviesRunnable;
    private RetrievePopularTvRunnable retrievePopularTvRunnable;
    private RetrieveMoviesTrendingDayRunnable retrieveMoviesTrendingDayRunnable;
    private RetrieveTvTrendingDayRunnable retrieveTvTrendingDayRunnable;
    private RetrieveMoviesTrendingWeekRunnable retrieveMoviesTrendingWeekRunnable;
    private RetrieveTvTrendingWeekRunnable retrieveTvTrendingWeekRunnable;
    private RetrieveTvOnAirRunnable retrieveTvOnAirRunnable;
    private RetrieveMoviesTopRatedRunnable retrieveMoviesTopRatedRunnable;
    private RetrieveTvTopRatedRunnable retrieveTvTopRatedRunnable;




    public static SliderApiClient getInstance() {
        if(instance == null) {
            instance = new SliderApiClient();
        }
        return instance;
    }

    private SliderApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
        mMoviesNowPlaying = new MutableLiveData<>();
        mTvPopular = new MutableLiveData<>();
        mMoviesTrendingDay = new MutableLiveData<>();
        mTvTrendingDay = new MutableLiveData<>();
        mMoviesTrendingWeek = new MutableLiveData<>();
        mTvTrendingWeek = new MutableLiveData<>();
        mTvOnAir = new MutableLiveData<>();
        mMoviesTopRated = new MutableLiveData<>();
        mTvTopRated = new MutableLiveData<>();
    }

    public LiveData<MovieModel> getMovies() {
        return mMovies;
    }
    public LiveData<List<SearchModel>> getPopularMovies() {
        return mMoviesPopular;
    }
    public LiveData<List<SearchModel>> getNowPlayingMovies() {
        return mMoviesNowPlaying;
    }
    public LiveData<List<SearchModel>> getPopularTv() { return mTvPopular; }
    public LiveData<List<SearchModel>> getMoviesTrendingDay() { return mMoviesTrendingDay; }
    public LiveData<List<SearchModel>> getTvTrendingDay() { return mTvTrendingDay; }
    public LiveData<List<SearchModel>> getMoviesTrendingWeek() { return mMoviesTrendingWeek; }
    public LiveData<List<SearchModel>> getTvTrendingWeek() { return mTvTrendingWeek; }
    public LiveData<List<SearchModel>> getTvOnAir() { return mTvOnAir; }
    public LiveData<List<SearchModel>> getMoviesTopRated() { return mMoviesTopRated; }
    public LiveData<List<SearchModel>> getTvTopRated() { return mTvTopRated; }


    // 1 - This method that we are going to call through the classes
    public void searchMovieApi(int id) {
        if(retrieveMovieRunnable != null) {
            retrieveMovieRunnable = null;
        }

       retrieveMovieRunnable = new RetrieveMovieRunnable(id);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for popular movies
    public void searchPopularMoviesApi(int page) {
        if(retrievePopularMovieRunnable != null) {
            retrievePopularMovieRunnable = null;
        }

        retrievePopularMovieRunnable = new RetrievePopularMovieRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrievePopularMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for now playing movies
    public void searchNowPlayingMoviesApi(int page) {
        if(retrieveNowPlayingMoviesRunnable != null) {
            retrieveNowPlayingMoviesRunnable = null;
        }

        retrieveNowPlayingMoviesRunnable = new RetrieveNowPlayingMoviesRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveNowPlayingMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for popular tv
    public void searchPopularTvApi(int page) {
        if(retrievePopularTvRunnable != null) {
            retrievePopularTvRunnable = null;
        }

        retrievePopularTvRunnable = new RetrievePopularTvRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrievePopularTvRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for movies trending day
    public void searchMoviesTrendingDayApi(int page) {
        if(retrieveMoviesTrendingDayRunnable != null) {
            retrieveMoviesTrendingDayRunnable = null;
        }

        retrieveMoviesTrendingDayRunnable = new RetrieveMoviesTrendingDayRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveMoviesTrendingDayRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for tv trending day
    public void searchTvTrendingDayApi(int page) {
        if(retrieveTvTrendingDayRunnable != null) {
            retrieveTvTrendingDayRunnable = null;
        }

        retrieveTvTrendingDayRunnable = new RetrieveTvTrendingDayRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveTvTrendingDayRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for movies trending week
    public void searchMoviesTrendingWeekApi(int page) {
        if(retrieveMoviesTrendingWeekRunnable != null) {
            retrieveMoviesTrendingWeekRunnable = null;
        }

        retrieveMoviesTrendingWeekRunnable = new RetrieveMoviesTrendingWeekRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveMoviesTrendingWeekRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for tv trending week
    public void searchTvTrendingWeekApi(int page) {
        if(retrieveTvTrendingWeekRunnable != null) {
            retrieveTvTrendingWeekRunnable = null;
        }

        retrieveTvTrendingWeekRunnable = new RetrieveTvTrendingWeekRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveTvTrendingWeekRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for tv on air
    public void searchTvOnAirApi(int page) {
        if(retrieveTvOnAirRunnable != null) {
            retrieveTvOnAirRunnable = null;
        }

        retrieveTvOnAirRunnable = new RetrieveTvOnAirRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveTvOnAirRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for movies top rated
    public void searchMoviesTopRatedApi(int page) {
        if(retrieveMoviesTopRatedRunnable != null) {
            retrieveMoviesTopRatedRunnable = null;
        }

        retrieveMoviesTopRatedRunnable = new RetrieveMoviesTopRatedRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveMoviesTopRatedRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    //1 This method we are calling for tv top rated
    public void searchTvTopRatedApi(int page) {
        if(retrieveTvTopRatedRunnable != null) {
            retrieveTvTopRatedRunnable = null;
        }

        retrieveTvTopRatedRunnable = new RetrieveTvTopRatedRunnable(page);
        final Future myHandlerPopular = AppExecutors.getInstance().networkIO().submit(retrieveTvTopRatedRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandlerPopular.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }



    //Retrieving data from rest api
    //We have 2 types of Queries
    private class RetrieveMovieRunnable implements Runnable {

        private int id;
        boolean cancelRequest;

        public RetrieveMovieRunnable(int id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getSpecificMovie(id).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    MovieModel movieFound = ((MovieResponse)response.body()).getMovie();
                        // Sending data to live data
                        // PostValue: used for background thread
                        //setValue: not for background threat
                        mMovies.postValue(movieFound);

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieModel> getSpecificMovie(int movieId) {
            return Service.getTmdbApi().getSpecificMovie(
                    movieId,
                    Credentials.API_KEY
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrievePopularMovieRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrievePopularMovieRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getPopularMovies(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mMoviesPopular.postValue(list);
                    }else {
                        List<SearchModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesPopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getPopularMovies(int page) {
            return Service.getTmdbApi().getPopularMovies(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveNowPlayingMoviesRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveNowPlayingMoviesRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getNowPlayingMovies(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mMoviesNowPlaying.postValue(list);
                    }else {
                        List<SearchModel> currentMovies = mMoviesNowPlaying.getValue();
                        currentMovies.addAll(list);
                        mMoviesNowPlaying.postValue(currentMovies);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesNowPlaying.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesNowPlaying.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getNowPlayingMovies(int page) {
            return Service.getTmdbApi().getNowPlayingMovies(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrievePopularTvRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrievePopularTvRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getPopularTv(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mTvPopular.postValue(list);
                    }else {
                        List<SearchModel> currentTv = mTvPopular.getValue();
                        currentTv.addAll(list);
                        mTvPopular.postValue(currentTv);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mTvPopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTvPopular.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getPopularTv(int page) {
            return Service.getTmdbApi().getPopularTv(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesTrendingDayRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveMoviesTrendingDayRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMoviesTrendingDay(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mMoviesTrendingDay.postValue(list);
                    }else if(page == 2) {
                        mMoviesTrendingDay.postValue(list);
                    }else {
                        List<SearchModel> currentMovies = mMoviesTrendingDay.getValue();
                        currentMovies.addAll(list);
                        mMoviesTrendingDay.postValue(currentMovies);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesTrendingDay.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesTrendingDay.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getMoviesTrendingDay(int page) {
            return Service.getTmdbApi().getTrendingMoviesDay(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveTvTrendingDayRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveTvTrendingDayRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getTvTrendingDay(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mTvTrendingDay.postValue(list);
                    } else if(page == 2) {
                        mTvTrendingDay.postValue(list);
                    }
                    else {
                        List<SearchModel> current = mTvTrendingDay.getValue();
                        current.addAll(list);
                        mTvTrendingDay.postValue(current);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mTvTrendingDay.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTvTrendingDay.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getTvTrendingDay(int page) {
            return Service.getTmdbApi().getTrendingTvDay(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesTrendingWeekRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveMoviesTrendingWeekRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMoviesTrendingWeek(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mMoviesTrendingWeek.postValue(list);
                    }else if(page == 3) {
                        mMoviesTrendingWeek.postValue(list);
                    }
                    else {
                        List<SearchModel> current = mMoviesTrendingWeek.getValue();
                        current.addAll(list);
                        mMoviesTrendingWeek.postValue(current);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesTrendingWeek.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesTrendingWeek.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getMoviesTrendingWeek(int page) {
            return Service.getTmdbApi().getTrendingMoviesWeek(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveTvTrendingWeekRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveTvTrendingWeekRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getTvTrendingWeek(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mTvTrendingWeek.postValue(list);
                    } else if(page == 3) {
                        mTvTrendingWeek.postValue(list);
                    }
                    else {
                        List<SearchModel> currentTv = mTvTrendingWeek.getValue();
                        currentTv.addAll(list);
                        mTvTrendingWeek.postValue(currentTv);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mTvTrendingWeek.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTvTrendingWeek.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getTvTrendingWeek(int page) {
            return Service.getTmdbApi().getTrendingTvWeek(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveTvOnAirRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveTvOnAirRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getTvOnAir(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mTvOnAir.postValue(list);
                    }else {
                        List<SearchModel> currentTv = mTvOnAir.getValue();
                        currentTv.addAll(list);
                        mTvOnAir.postValue(currentTv);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mTvOnAir.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTvOnAir.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getTvOnAir(int page) {
            return Service.getTmdbApi().getTvOnAir(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesTopRatedRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveMoviesTopRatedRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMoviesTopRated(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mMoviesTopRated.postValue(list);
                    }else {
                        List<SearchModel> currentTv = mMoviesTopRated.getValue();
                        currentTv.addAll(list);
                        mMoviesTopRated.postValue(currentTv);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMoviesTopRated.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesTopRated.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getMoviesTopRated(int page) {
            return Service.getTmdbApi().getTopRatedMovies(
                    Credentials.API_KEY,
                    page
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveTvTopRatedRunnable implements Runnable {

        private int page;
        boolean cancelRequest;

        public RetrieveTvTopRatedRunnable(int page) {
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getTvTopRated(page).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<SearchModel> list = new ArrayList<>(((SliderSearchResponse)response.body()).getResults());
                    if(page == 1) {
                        mTvTopRated.postValue(list);
                    }else {
                        List<SearchModel> currentTv = mTvTopRated.getValue();
                        currentTv.addAll(list);
                        mTvTopRated.postValue(currentTv);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mTvTopRated.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTvTopRated.postValue(null);
            }

        }

        //Search method/query
        private Call<SliderSearchResponse> getTvTopRated(int page) {
            return Service.getTmdbApi().getTvTopRated(
                    Credentials.API_KEY,
                    page,
                    region
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }




}














