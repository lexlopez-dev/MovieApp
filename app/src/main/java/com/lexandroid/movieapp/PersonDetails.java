package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.models.PersonModel;
import com.lexandroid.movieapp.models.tv.TvModel;
import com.lexandroid.movieapp.utils.Credentials;

import java.text.NumberFormat;
import java.text.ParseException;

public class PersonDetails extends AppCompatActivity {

    private ImageView personImageView;
    private TextView personName, personDepartment, personBio, personPopularityFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        this.personImageView = findViewById(R.id.imageView_person);
        this.personName = findViewById(R.id.person_name_bold);
        this.personDepartment = findViewById(R.id.person_departments);
        this.personBio = findViewById(R.id.person_bio);
        this.personPopularityFloat = findViewById(R.id.person_popularity_float);

        try {
            GetDataFromIntent();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void GetDataFromIntent() throws ParseException {
        if (getIntent().hasExtra("person")) {
            PersonModel personModel = getIntent().getParcelableExtra("person");

            personName.setText(personModel.getName());
            personDepartment.setText(personModel.getKnown_for_department());
            personBio.setText(personModel.getBiography());
            personPopularityFloat.setText(String.valueOf(personModel.getPopularity()));

            Glide.with(this)
                    .load(Credentials.IMG_URL + personModel.getProfile_path())
                    .into(personImageView);


        }

    }
}