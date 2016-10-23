package com.udacity.movieapp.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.movieapp.R;
import com.udacity.movieapp.activity.MainActivity;
import com.udacity.movieapp.adapter.MoviesAdapter;
import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.db.DbWrapper;
import com.udacity.movieapp.listener.OnFetchMoviesInfoComplete;
import com.udacity.movieapp.model.MovieInfo;
import com.udacity.movieapp.request.RequestFactory;

import java.util.ArrayList;

public class MoviesCategoryFrag extends Fragment implements OnFetchMoviesInfoComplete {
    private RecyclerView recyclerMovies;
    private TextView tvNote;
    private MovieInfo movieInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_movie_category, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        recyclerMovies = (RecyclerView) v.findViewById(R.id.recyclerMovies);
        recyclerMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tvNote = (TextView) v.findViewById(R.id.tvNote);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMoviesInfoFromTheMovieDb();
    }

    private void fetchMoviesInfoFromTheMovieDb() {
        if(!base().isNetworkAvailable()){
            Toast.makeText(getActivity(), getString(R.string.connect_to_network), Toast.LENGTH_SHORT).show();
            return;
        }
        base().showProgressDialog();
        RequestFactory.parseMoviesInfo(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSortPop:
                resetViewsIfSelectedMenu();

                base().putSortInPrefs(C.THEMOVIEDB_URL_ENDPOINT_POP);
                fetchMoviesInfoFromTheMovieDb();
                return true;

            case R.id.menuSortTop:
                resetViewsIfSelectedMenu();

                base().putSortInPrefs(C.THEMOVIEDB_URL_ENDPOINT_TOP);
                fetchMoviesInfoFromTheMovieDb();
                return true;

            case R.id.menuFavorite:
                resetViewsIfSelectedMenu();

                ArrayList<MovieInfo> movieInfoList =  DbWrapper.getInstance(getActivity()).getFavoriteMovies();
                if (movieInfoList == null || movieInfoList.isEmpty()){
                   tvNote.setText(getString(R.string.no_favorites));

                    return true;
                }
                recyclerMovies.setAdapter(new MoviesAdapter(this, movieInfoList, true));
                return true;
        }
        return false;
    }

    private void resetViewsIfSelectedMenu() {
        if (base().isTablet())
            base().getMovieDetailsFrag().getRootViewDetails().setVisibility(View.GONE);
        tvNote.setText("");
        if (recyclerMovies.getAdapter() == null) return;
        ((MoviesAdapter)recyclerMovies.getAdapter()).clearAll();
    }

    @Override
    public void onFetchMoviesInfoComplete(ArrayList<MovieInfo> movieInfoList) {
        if (movieInfoList != null && !movieInfoList.isEmpty()){
            recyclerMovies.setAdapter(new MoviesAdapter(this, movieInfoList, false));
        }
        base().hideProgressDialog();
    }

    private MainActivity base(){
        return (MainActivity) getActivity();
    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }
}
