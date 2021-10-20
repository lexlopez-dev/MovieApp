package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.models.tv.TvModel;
import com.lexandroid.movieapp.utils.Credentials;

import java.text.NumberFormat;
import java.text.ParseException;

public class TvDetails extends AppCompatActivity {

    private ImageView tvImageView;
    private TextView tvTitle, tvSeasons, tvDesc, tvRatingCount, tvRatingFloat;

    private RatingBar tvRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);

        tvImageView = findViewById(R.id.imageView_tv);
        tvTitle = findViewById(R.id.tv_title_bold);
        tvSeasons = findViewById(R.id.tv_seasons);
        tvDesc = findViewById(R.id.tv_desc);
        tvRatingCount = findViewById(R.id.tv_rating_count);
        tvRatingFloat = findViewById(R.id.tv_rating_float);
        tvRatingBar = findViewById(R.id.tv_rating_bar);

        try {
            GetDataFromIntent();
        } catch (ParseException e) {
            e.printStackTrace();
        }

}

    private void GetDataFromIntent() throws ParseException {
        if (getIntent().hasExtra("tv")) {
            TvModel tvModel = getIntent().getParcelableExtra("tv");

            tvTitle.setText(tvModel.getOriginal_name());
            tvDesc.setText(tvModel.getOverview());
            tvSeasons.setText(tvModel.getNumber_of_seasons());
            tvRatingBar.setNumStars(5);
            tvRatingBar.setRating(tvModel.getVote_average() / 2);

            NumberFormat numberFormat = NumberFormat.getInstance();
            tvRatingCount.setText(String.valueOf(numberFormat.format(tvModel.getVote_count())));
            tvRatingFloat.setText(String.valueOf(tvModel.getVote_average()));

            Glide.with(this)
                    .load(Credentials.IMG_URL + tvModel.getPoster_path())
                    .into(tvImageView);


        }

    }


}











