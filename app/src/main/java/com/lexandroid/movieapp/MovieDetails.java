package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.utils.Credentials;

public class MovieDetails extends AppCompatActivity {
    //Widgets
    private ImageView imageViewDetails;
    private TextView titleDetails, descriptionDetails, movieRatingCount;
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



        GetDataFromIntent();

        Log.v("Tagy", "Passed movieDetails onCreate");

    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("Tagy", "incoming intent" + movieModel.getMovie_id());

            titleDetails.setText(movieModel.getOriginal_title());
            descriptionDetails.setText(movieModel.getOverview());
            ratingBarDetails.setNumStars(5);
            ratingBarDetails.setRating(movieModel.getVote_average() / 2);

            Log.v("Tagy", "Int Votes: " + movieModel.getVote_count());
            movieRatingCount.setText(String.valueOf(movieModel.getVote_count()));
            Log.v("Tagy", "Runtime " + movieModel.getRuntime());
            Log.v("Tagy", "Date: " + movieModel.getRelease_date());
            Log.v("Tagy", "Rating Average: " + movieModel.getVote_average());

            Glide.with(this)
                    .load(Credentials.IMG_URL + movieModel.getPoster_path())
                    .into(imageViewDetails);
        }
    }


}