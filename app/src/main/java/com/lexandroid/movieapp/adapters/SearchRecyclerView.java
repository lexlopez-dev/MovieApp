package com.lexandroid.movieapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lexandroid.movieapp.R;
import com.lexandroid.movieapp.models.SearchModel;
import com.lexandroid.movieapp.utils.Credentials;

import java.util.List;

public class SearchRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchModel> mResults;
    private OnSearchListener onSearchListener;

    public LinearLayout.LayoutParams params;

    public SearchRecyclerView(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_card, parent,false);
        return new SearchViewHolder(view, onSearchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((MovieViewHolder)holder).title.setText(mMovies.get(position).getOriginal_title());
//        ((MovieViewHolder)holder).category.setText(mMovies.get(position).ge());
//       ((MovieViewHolder)holder).title.setText(mMovies.get(position).getOriginal_title());


        if(mResults.get(position).getMedia_type().equals("person")) {
            if(mResults.get(position).getProfile_path() == null) {
                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(R.drawable.noimagefound)
                        .into(((SearchViewHolder)holder).imageView);
            }else {
                    Glide.with(holder.itemView.getContext())
                            .asBitmap()
                            .load(Credentials.IMG_URL + mResults.get(position).getProfile_path())
                            .into(((SearchViewHolder) holder).imageView);
            }
        }else {
            if(mResults.get(position).getPoster_path() == null) {
                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(R.drawable.noimagefound)
                        .into(((SearchViewHolder)holder).imageView);
            }else {
                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(Credentials.IMG_URL + mResults.get(position).getPoster_path())
                        .into(((SearchViewHolder) holder).imageView);
            }
        }
    }


    @Override
    public int getItemCount() {
        if(mResults != null) {
            return mResults.size();
        }
        return 0;
    }

    public void setmResults(List<SearchModel> mResults) {
        this.mResults = mResults;
        notifyDataSetChanged();
    }

    //Getting the id of the movie clicked
    public SearchModel getSelected(int position) {
        if(mResults != null) {
            if(mResults.size() > 0) {
                Log.v("Debug", "Integer ID of search clicked --> " + mResults.get(position).getId());
                Log.v("Debug", "Media Type of clicked --> " + mResults.get(position).getMedia_type());
                Log.v("Debug", "Position of clicked --> " + position);
                return mResults.get(position);
            }
        }
        return null;
    }



}
