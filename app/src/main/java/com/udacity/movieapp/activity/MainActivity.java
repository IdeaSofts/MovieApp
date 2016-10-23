package com.udacity.movieapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.udacity.movieapp.R;
import com.udacity.movieapp.frag.MovieDetailsFrag;
import com.udacity.movieapp.frag.MoviesCategoryFrag;

public class MainActivity extends BaseActivity {
    private MoviesCategoryFrag moviesCategoryFrag;
    private MovieDetailsFrag movieDetailsFrag;
    private boolean isTablet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moviesCategoryFrag = (MoviesCategoryFrag) getSupportFragmentManager()
                .findFragmentById(R.id.moviesCategoryFrag);
        movieDetailsFrag = (MovieDetailsFrag) getSupportFragmentManager()
                .findFragmentById(R.id.movieDetailsFrag);

        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    public boolean isTablet() {
        return isTablet;
    }

    public MovieDetailsFrag getMovieDetailsFrag() {
        return movieDetailsFrag;
    }

    @Override
    public void onBackPressed() {
        if(isTablet){
            boolean doNotCallSuper = movieDetailsFrag.onBackPressed();
            if (doNotCallSuper) return;
        }
        super.onBackPressed();
    }
}
