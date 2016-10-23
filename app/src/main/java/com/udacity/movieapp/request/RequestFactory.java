package com.udacity.movieapp.request;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.udacity.movieapp.constants.C;

/**
 * Created by sha on 15/10/16.
 */

public class RequestFactory {

    public static void parseMoviesInfo(Fragment currentFrag) {
        RequestManager.runInstance(currentFrag.getActivity(), C.REQUEST_MOVIES_INFO, currentFrag);
    }

    public static void parseReviewInfo(Fragment currentFrag, int currentRequest) {
        RequestManager.runInstance(currentFrag.getActivity(), currentRequest, currentFrag);
    }

    public static void parseReviewInfo(Context context, int currentRequest) {
        RequestManager.runInstance(context, currentRequest, null);
    }

    public static void parseTrailerInfo(Context context, int currentRequest) {
        RequestManager.runInstance(context, currentRequest, null);
    }

    public static void parseTrailerInfo(Fragment currentFrag, int currentRequest) {
        RequestManager.runInstance(currentFrag.getActivity(), currentRequest, currentFrag);
    }

    public static void downloadPosterToDiskCache(Fragment currentFrag) {
        RequestManager.runInstance(currentFrag.getActivity(), C.REQUEST_DOWNLOAD_POSTER_TO_DISK_CACHE, currentFrag);
    }

}
