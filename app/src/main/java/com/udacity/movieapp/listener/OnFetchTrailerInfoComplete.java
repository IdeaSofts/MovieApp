package com.udacity.movieapp.listener;

import com.udacity.movieapp.model.TrailerInfo;

import java.util.ArrayList;

/**
 * Created by sha on 02/10/16.
 */

public interface OnFetchTrailerInfoComplete {
    void onFetchTrailerInfoComplete(ArrayList<TrailerInfo> trailerInfoList);
}
