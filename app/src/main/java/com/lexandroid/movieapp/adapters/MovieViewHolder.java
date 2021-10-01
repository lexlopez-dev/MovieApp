package com.lexandroid.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lexandroid.movieapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // ** Widgets **

    ImageView imageView;
    //RatingBar ratingBar;
    //TextView title, category, duration;


    //** Click listener **
    OnMovieListener onMovieListener;


    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        imageView = itemView.findViewById(R.id.movie_img);

//        title = itemView.findViewById(R.id.movie_title);
//        category = itemView.findViewById(R.id.movie_category);
//        duration = itemView.findViewById(R.id.movie_duration);


        //ratingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
