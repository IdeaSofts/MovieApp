package com.udacity.movieapp.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.udacity.movieapp.cache.CacheWrapper;
import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.frag.MoviesCategoryFrag;
import com.udacity.movieapp.model.MovieInfo;
import com.udacity.movieapp.model.ReviewInfo;
import com.udacity.movieapp.model.TrailerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sha on 15/10/16.
 */

abstract class RequestParser extends RequestUtils {

    void parseMoviesInfo() throws IOException, JSONException {
        String url = getMoviesInfoUrl();
        String jsonString ;
        jsonString = getJSONString(url);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(C.JSON_ARRAY_RESULTS);
         loadMovies(jsonArray);
    }

    void parseTrailerInfo() throws IOException, JSONException {
        String url = getTrailerOrReviewUrl();
        String jsonString ;
        jsonString = getJSONString(url);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(C.JSON_ARRAY_RESULTS);
        loadTrailer(jsonArray);
    }

    void parseReviewInfo() throws IOException, JSONException {
        String url = getTrailerOrReviewUrl();
        String jsonString ;
        jsonString = getJSONString(url);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(C.JSON_ARRAY_RESULTS);
        loadReview(jsonArray);
    }

    private void loadMovies(JSONArray jsonArray) throws JSONException {
        ArrayList<MovieInfo> infoList = new ArrayList<>();
        MovieInfo info ;
        for (int i = 0 ; i < jsonArray.length() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String posterPath = jsonObject.getString(C.JSON_VAR_POSTER_PATH);
            String adult = jsonObject.getString(C.JSON_VAR_ADULT);
            String overview = jsonObject.getString(C.JSON_VAR_OVERVIEW);
            String releaseDate = jsonObject.getString(C.JSON_VAR_RELEASE_DATE);
            String id = jsonObject.getString(C.JSON_VAR_ID);
            String originalTitle = jsonObject.getString(C.JSON_VAR_ORIGINAL_TITLE);
            String originalLang = jsonObject.getString(C.JSON_VAR_ORIGINAL_LANG);
            String title = jsonObject.getString(C.JSON_VAR_TITLE);
            String backdrop = jsonObject.getString(C.JSON_VAR_BACKDROP);
            String popularity = jsonObject.getString(C.JSON_VAR_POPULARITY);
            String voteCount = jsonObject.getString(C.JSON_VAR_VOTE_COUNT);
            String video = jsonObject.getString(C.JSON_VAR_VIDEO);
            String voteRange = jsonObject.getString(C.JSON_VAR_VOTE_AVERAGE);
            info = new MovieInfo(
                    posterPath,
                    adult,
                    overview,
                    releaseDate,
                    id,
                    originalTitle,
                    originalLang,
                    title,
                    backdrop,
                    popularity,
                    voteCount,
                    video,
                    voteRange);
            infoList.add(info);
        }
        movieInfoList = infoList;
    }

    private void loadTrailer(JSONArray jsonArray) throws JSONException {
        ArrayList<TrailerInfo> infoList = new ArrayList<>();
        TrailerInfo info ;
        for (int i = 0 ; i < jsonArray.length() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String iso_639_1 = jsonObject.getString(C.JSON_VAR_ISO_639_1);
            String iso_3166_1 = jsonObject.getString(C.JSON_VAR_3166_1);
            String key = jsonObject.getString(C.JSON_VAR_KEY);
            String name = jsonObject.getString(C.JSON_VAR_NAME);
            String site = jsonObject.getString(C.JSON_VAR_SITE);
            String size = jsonObject.getString(C.JSON_VAR_SIZE);
            String type = jsonObject.getString(C.JSON_VAR_TYPE);

            info = new TrailerInfo(
                    iso_639_1,
                    iso_3166_1,
                    key,
                    name,
                    site,
                    size,
                    type);
            infoList.add(info);
        }
        trailerInfoList = infoList;
    }

    private void loadReview(JSONArray jsonArray) throws JSONException {
        ArrayList<ReviewInfo> infoList = new ArrayList<>();
        ReviewInfo info ;
        for (int i = 0 ; i < jsonArray.length() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString(C.JSON_VAR_ID);
            String author = jsonObject.getString(C.JSON_VAR_AUTHOR);
            String content = jsonObject.getString(C.JSON_VAR_CONTENT);
            String url = jsonObject.getString(C.JSON_VAR_URL);

            info = new ReviewInfo(
                    id,
                    author,
                    content,
                    url);
            infoList.add(info);
        }
        reviewInfoList = infoList;
    }

    void downloadPosterToDiskCache() throws IOException {
        MovieInfo movieInfo = ((MoviesCategoryFrag) currentFrag).getMovieInfo();
        Bitmap bitmap;
        if (movieInfo == null) return;
        bitmap = BitmapFactory.decodeStream(new URL(getPosterUrl(movieInfo.getPosterPath())).openConnection().getInputStream());
        CacheWrapper.addPosterToDiskCache(movieInfo.getId(), bitmap);
    }


}
