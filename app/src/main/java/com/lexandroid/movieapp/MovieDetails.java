package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.request.MovieApiClient;
import com.lexandroid.movieapp.request.Service;
import com.lexandroid.movieapp.response.MovieResponse;
import com.lexandroid.movieapp.utils.Credentials;
import com.lexandroid.movieapp.utils.TmdbApi;
import com.lexandroid.movieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {
    //Widgets
    private ImageView imageViewDetails;
    private TextView titleDetails, descriptionDetails, movieRatingCount, movieRatingFloat, movieGenre, movieReleaseYear, movieRuntime;
    private RatingBar ratingBarDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.title_bold);
        descriptionDetails = findViewById(R.id.movie_desc);
        ratingBarDetails = (RatingBar) findViewById(R.id.movie_rating_bar);
        movieRatingCount = findViewById(R.id.movie_rating_count);
        movieRatingFloat = findViewById(R.id.movie_rating_float);
        movieGenre = findViewById(R.id.movie_genre);
        movieReleaseYear = findViewById(R.id.movie_release_year);
        movieRuntime = findViewById(R.id.movie_runtime);

        try {
            GetDataFromIntent();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void GetDataFromIntent() throws ParseException {
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getOriginal_title());
            descriptionDetails.setText(movieModel.getOverview());
            ratingBarDetails.setNumStars(5);
            ratingBarDetails.setRating(movieModel.getVote_average() / 2);

            NumberFormat numberFormat = NumberFormat.getInstance();
            movieRatingCount.setText(String.valueOf(numberFormat.format(movieModel.getVote_count())));
            movieRatingFloat.setText(String.valueOf(movieModel.getVote_average()));
            movieReleaseYear.setText(movieModel.getRelease_year());
            movieRuntime.setText(movieModel.getRuntime());

            movieGenre.setVisibility(View.GONE);


            Glide.with(this)
                    .load(Credentials.IMG_URL + movieModel.getPoster_path())
                    .into(imageViewDetails);
        }
    }

}