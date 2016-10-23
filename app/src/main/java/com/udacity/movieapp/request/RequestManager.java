package com.udacity.movieapp.request;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.listener.OnFetchMoviesInfoComplete;
import com.udacity.movieapp.listener.OnFetchReviewInfoComplete;
import com.udacity.movieapp.listener.OnFetchTrailerInfoComplete;
import java.util.Hashtable;

class RequestManager extends RequestParser {
    private static Hashtable<String, RequestManager> sInstances;

    private RequestManager(Context context, Fragment currentFrag, int currentRequest) {
        this.context = context;
        this.currentFrag = currentFrag;
        this.currentRequest = currentRequest;

    }

    static void runInstance(Context context, int currentRequest, Fragment currentFrag) {
        if (sInstances == null) {
            sInstances = new Hashtable<>();
        }
        String instanceKey = currentFrag + "-" + currentRequest;
        RequestManager instance =  sInstances.get(instanceKey);
        if (instance == null) {
            sInstances.put(instanceKey, new RequestManager(context, currentFrag, currentRequest));
            instance =  sInstances.get(instanceKey);
        }
        if (instance != null && instance.getStatus() != Status.RUNNING) {
            if (instance.getStatus() == Status.FINISHED) {
                sInstances.put(instanceKey, new RequestManager(context, currentFrag, currentRequest));
                instance =  sInstances.get(instanceKey);
            }

            if (Build.VERSION.SDK_INT < 11) {
                instance.execute();
            } else {
                instance.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
        switch (currentRequest){

            case C.REQUEST_MOVIES_INFO :
              parseMoviesInfo();
                break;

            case C.REQUEST_TRAILERS_FRAG:
            case C.REQUEST_TRAILERS_ACTIVITY:
                parseTrailerInfo();
                break;

            case C.REQUEST_REVIEWS_FRAG:
            case C.REQUEST_REVIEWS_ACTIVITY:
                parseReviewInfo();
                break;

            case C.REQUEST_DOWNLOAD_POSTER_TO_DISK_CACHE :
                downloadPosterToDiskCache();
                break;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void res) {
        super.onPostExecute(res);
        try {
            switch (currentRequest){
                case C.REQUEST_MOVIES_INFO :
                    ((OnFetchMoviesInfoComplete) currentFrag).onFetchMoviesInfoComplete(movieInfoList);
                    break;

                case C.REQUEST_TRAILERS_ACTIVITY:
                    ((OnFetchTrailerInfoComplete) context).onFetchTrailerInfoComplete(trailerInfoList);
                    break;

                case C.REQUEST_TRAILERS_FRAG:
                    ((OnFetchTrailerInfoComplete) currentFrag).onFetchTrailerInfoComplete(trailerInfoList);
                    break;

                case C.REQUEST_REVIEWS_ACTIVITY:
                    ((OnFetchReviewInfoComplete) context).onFetchReviewInfoComplete(reviewInfoList);
                    break;

                case C.REQUEST_REVIEWS_FRAG:
                    ((OnFetchReviewInfoComplete) currentFrag).onFetchReviewInfoComplete(reviewInfoList);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
