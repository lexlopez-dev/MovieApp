package com.lexandroid.movieapp.request;

import android.util.Log;

import com.lexandroid.movieapp.utils.Credentials;
import com.lexandroid.movieapp.utils.TmdbApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static TmdbApi tmdbApi = retrofit.create(TmdbApi.class);

    public static TmdbApi getTmdbApi() {//had to make this static because of the main activity getRetrofitResponse
        Log.v("Debug", "Able to start Service.getTmdbAPI");
        return tmdbApi;
    }
}
