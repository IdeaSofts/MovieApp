package com.udacity.movieapp.listener;

import com.udacity.movieapp.model.ReviewInfo;

import java.util.ArrayList;

/**
 * Created by sha on 02/10/16.
 */

public interface OnFetchReviewInfoComplete {
    void onFetchReviewInfoComplete(ArrayList<ReviewInfo> reviewInfoList);
}
