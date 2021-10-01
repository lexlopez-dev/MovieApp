package com.lexandroid.movieapp.adapters;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.R;
import com.lexandroid.movieapp.models.MovieModel;
import com.lexandroid.movieapp.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent,false);
        return new MovieViewHolder(view, onMovieListener);
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
                .into(((MovieViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //Getting the id of the movie clicked
    public MovieModel getSelected(int position) {
        if(mMovies != null) {
            if(mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }



}
