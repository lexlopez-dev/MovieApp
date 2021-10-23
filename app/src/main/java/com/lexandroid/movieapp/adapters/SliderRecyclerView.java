package com.lexandroid.movieapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.R;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.utils.Credentials;

import java.util.List;

public class SliderRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchModel> mMovies;
    private OnSliderListener onSliderListener;
    String sliderName;

    public SliderRecyclerView(OnSliderListener onSliderListener) {
        this.onSliderListener = onSliderListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_card, parent,false);
        return new SliderViewHolder(view, onSliderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((MovieViewHolder)holder).title.setText(mMovies.get(position).getOriginal_title());
//        ((MovieViewHolder)holder).category.setText(mMovies.get(position).ge());
//        ((MovieViewHolder)holder).title.setText(mMovies.get(position).getOriginal_title());

        // ** ImageView: Using Glide library **
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(Credentials.IMG_URL + mMovies.get(position).getPoster_path())
                .into(((SliderViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<SearchModel> mMovies, String sliderName) {
        for (SearchModel movie: mMovies
             ) {
            movie.setSliderName(sliderName);
        }
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //Getting the id of the movie clicked
    public SearchModel getSelected(int position) {
        if(mMovies != null) {
            if(mMovies.size() > 0) {
                Log.v("Debug", "sliderName of Clicked --> " + mMovies.get(position).getSliderName());
                return mMovies.get(position);
            }
        }
        return null;
    }



}
