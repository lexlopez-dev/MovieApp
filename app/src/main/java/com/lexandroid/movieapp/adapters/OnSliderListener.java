package com.lexandroid.movieapp.adapters;

import android.view.View;

public interface OnSliderListener {

    void onTileClick (View view, int position);

    void onCategoryClick(String category);
}
