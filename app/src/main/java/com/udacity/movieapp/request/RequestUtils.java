package com.udacity.movieapp.request;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.udacity.movieapp.activity.MovieDetailsActivity;
import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.frag.MovieDetailsFrag;
import com.udacity.movieapp.model.MovieInfo;
import com.udacity.movieapp.model.ReviewInfo;
import com.udacity.movieapp.model.TrailerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public abstract class RequestUtils extends AsyncTask<Void, Void, Void> {
    Fragment currentFrag;
    int currentRequest;
    Context context;
    ArrayList<MovieInfo> movieInfoList;
    ArrayList<TrailerInfo> trailerInfoList;
    ArrayList<ReviewInfo> reviewInfoList;

    String getJSONString(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        StringBuilder sb = null;
        if (conn.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
            reader.close();
        }
        return sb != null ?  sb.toString() : null;
    }


    String getMoviesInfoUrl() {
        String sort = PreferenceManager.getDefaultSharedPreferences(
                currentFrag.getActivity()).getString(C.PREFS_SORT,
                C.THEMOVIEDB_URL_ENDPOINT_POP);

        return new StringBuilder(C.THEMOVIEDB_URL_BASE)
                .append(sort)
                .append(C.THEMOVIEDB_API_KEY)
                .toString();
    }

    String getTrailerOrReviewUrl() {

        String id = null,
               what = null;

        switch (currentRequest){
            case C.REQUEST_TRAILERS_FRAG:
                id = ((MovieDetailsFrag) currentFrag).getMovieId();
                what = C.THEMOVIEDB_API_TRAILER;
                break;
            case C.REQUEST_REVIEWS_FRAG:
                id = ((MovieDetailsFrag) currentFrag).getMovieId();
                what = C.THEMOVIEDB_API_REVIEW;
                break;
            case C.REQUEST_TRAILERS_ACTIVITY:
                id = ((MovieDetailsActivity) context).getMovieId();
                what = C.THEMOVIEDB_API_TRAILER;
                break;
            case C.REQUEST_REVIEWS_ACTIVITY:
                id = ((MovieDetailsActivity) context).getMovieId();
                what = C.THEMOVIEDB_API_REVIEW;
                break;
        }

        return new StringBuilder(C.THEMOVIEDB_URL_BASE)
                .append(id)
                .append(what)
                .append(C.THEMOVIEDB_API_KEY)
                .toString();
    }

    String getPosterUrl(String posterPath) {
        return new StringBuilder(C.POSTER_URL_BASE)
                .append(C.POSTER_URL_RECOMMENDED_SIZE)
                .append(posterPath).toString();
    }


}
