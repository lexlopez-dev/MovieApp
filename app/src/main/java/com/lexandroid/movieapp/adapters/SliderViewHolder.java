package com.lexandroid.movieapp.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lexandroid.movieapp.R;

public class SliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // ** Widgets **

    ImageView imageView;
    //RatingBar ratingBar;
    //TextView title, category, duration;


    //** Click listener **
    OnSliderListener onSliderListener;


    public SliderViewHolder(@NonNull View itemView, OnSliderListener onSliderListener) {
        super(itemView);
        this.onSliderListener = onSliderListener;
        imageView = itemView.findViewById(R.id.home_card_img);

//        title = itemView.findViewById(R.id.movie_title);
//        category = itemView.findViewById(R.id.movie_category);
//        duration = itemView.findViewById(R.id.movie_duration);


        //ratingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("Debug", "clicked");
        onSliderListener.onTileClick(view, getAdapterPosition());
    }
}
