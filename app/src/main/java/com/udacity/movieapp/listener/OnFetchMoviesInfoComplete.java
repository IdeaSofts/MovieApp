package com.udacity.movieapp.listener;

import com.udacity.movieapp.model.MovieInfo;

import java.util.ArrayList;

/**
 * Created by sha on 02/10/16.
 */

public interface OnFetchMoviesInfoComplete {
    void onFetchMoviesInfoComplete(ArrayList<MovieInfo> movieInfoList);
}
