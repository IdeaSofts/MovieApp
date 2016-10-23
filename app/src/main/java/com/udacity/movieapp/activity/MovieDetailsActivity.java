package com.udacity.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movieapp.R;
import com.udacity.movieapp.adapter.TrailersAdapter;
import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.listener.OnFetchReviewInfoComplete;
import com.udacity.movieapp.listener.OnFetchTrailerInfoComplete;
import com.udacity.movieapp.model.MovieInfo;
import com.udacity.movieapp.model.ReviewInfo;
import com.udacity.movieapp.model.TrailerInfo;
import com.udacity.movieapp.request.RequestFactory;

import java.util.ArrayList;

public class MovieDetailsActivity extends BaseActivity implements
        OnFetchReviewInfoComplete, OnFetchTrailerInfoComplete {

    private View rootView;
    private MovieInfo movieInfo;
    private ImageView ivMoviePoster;
    private TextView
            tvTitle,
            tvReleaseDate,
            tvAdult,
            tvVoteCount,
            tvVideo,
            tvReview;
    private RecyclerView recyclerTrailers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        getIntentData();
        init();
        setData();
    }

    private void init() {
        rootView  = findViewById(R.id.rootView);
        rootView.setVisibility(View.VISIBLE);
        ivMoviePoster = (ImageView) findViewById(R.id.ivMoviePoster);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvAdult = (TextView) findViewById(R.id.tvAdult);
        tvVoteCount = (TextView) findViewById(R.id.tvVoteCount);
        tvVideo = (TextView) findViewById(R.id.tvVideo);
        recyclerTrailers  = (RecyclerView) findViewById(R.id.recyclerTrailers);
        tvReview  = (TextView) findViewById(R.id.tvReview);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.getParcelableExtra(C.INTENT_PARCELABLE_MOVIE_INFO) != null){
            movieInfo = intent.getParcelableExtra(C.INTENT_PARCELABLE_MOVIE_INFO);
        }
    }

    private void setData() {
        String posterPathFull = new StringBuilder(C.POSTER_URL_BASE)
                .append(C.POSTER_URL_RECOMMENDED_SIZE)
                .append(movieInfo.getPosterPath())
                .toString();
        Picasso.with(this).load(posterPathFull).into(ivMoviePoster);

        String adult = getDisplayTxt(getTitle(R.string.adult), yesOrNo(movieInfo.getAdult()));
        String video = getDisplayTxt(getTitle(R.string.video), yesOrNo(movieInfo.getVideo()));
        String title = getDisplayTxt(getTitle(R.string.title), movieInfo.getTitle());
        String releaseDate = getDisplayTxt(getTitle(R.string.release_date), movieInfo.getReleaseDate());
        String voteCount = getDisplayTxt(getTitle(R.string.vote_count), movieInfo.getVoteCount());

        tvAdult.setText(adult);
        tvVideo.setText(video);
        tvTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvVoteCount.setText(voteCount);

        RequestFactory.parseTrailerInfo(this, C.REQUEST_TRAILERS_ACTIVITY);
        RequestFactory.parseReviewInfo(this, C.REQUEST_REVIEWS_ACTIVITY);
    }



    @Override
    public void onFetchReviewInfoComplete(ArrayList<ReviewInfo> reviewInfoList) {
        if (reviewInfoList == null || reviewInfoList.isEmpty()) return;
        String review = new StringBuilder()
                .append(getString(R.string.review))
                .append("\n")
                .append(getString(R.string.author))
                .append(reviewInfoList.get(0).getAuthor())
                .append("\n")
                .append("\n")
                .append(reviewInfoList.get(0).getContent())
                .append("\n")
                .append("\n")
                .append(reviewInfoList.get(0).getUrl())
                .toString();
        tvReview.setText(review);
    }

    @Override
    public void onFetchTrailerInfoComplete(ArrayList<TrailerInfo> trailerInfoList) {
        if (trailerInfoList == null || trailerInfoList.isEmpty()) return;
        recyclerTrailers.setAdapter(new TrailersAdapter(this, trailerInfoList));
        recyclerTrailers.setLayoutManager(new LinearLayoutManager(this));

    }

    public String getMovieId() {
        return movieInfo.getId();
    }
}
