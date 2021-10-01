package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.utils.Credentials;

public class MovieDetails extends AppCompatActivity {
    //Widgets
    private ImageView imageViewDetails;
    private TextView titleDetails, descriptionDetails;
    private RatingBar ratingBarDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.title_bold);
        descriptionDetails = findViewById(R.id.movie_desc);
        ratingBarDetails = findViewById(R.id.movie_rating_bar);

        GetDataFromIntent();

    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("Tagy", "incoming intent" + movieModel.getMovie_id());

            titleDetails.setText(movieModel.getOriginal_title());
            descriptionDetails.setText(movieModel.getOverview());
            ratingBarDetails.setRating(movieModel.getVote_average());

            Glide.with(this)
                    .load(Credentials.IMG_URL + movieModel.getBackdrop_path())
                    .into(imageViewDetails);
        }
    }
}