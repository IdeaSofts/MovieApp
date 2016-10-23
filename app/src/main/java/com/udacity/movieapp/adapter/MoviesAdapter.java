package com.udacity.movieapp.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.movieapp.R;
import com.udacity.movieapp.activity.MainActivity;
import com.udacity.movieapp.activity.MovieDetailsActivity;
import com.udacity.movieapp.cache.CacheWrapper;
import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.db.DbWrapper;
import com.udacity.movieapp.frag.MoviesCategoryFrag;
import com.udacity.movieapp.model.MovieInfo;
import com.udacity.movieapp.request.RequestFactory;

import java.util.ArrayList;

public class MoviesAdapter extends BaseRecyclerAdapter<MoviesAdapter.ViewHolder> {
    private ArrayList<MovieInfo> movieInfoList;
    private MoviesCategoryFrag moviesCategoryFrag;
    private  DbWrapper db;
    private boolean isFavorite;

    public MoviesAdapter(
            MoviesCategoryFrag moviesCategoryFrag,
            ArrayList<MovieInfo> movieInfoList,
            boolean isFavorite) {
        this.movieInfoList = movieInfoList;
        this.moviesCategoryFrag = moviesCategoryFrag;
        this.isFavorite = isFavorite;
         db = DbWrapper.getInstance(moviesCategoryFrag.getActivity());
    }

    @Override
    ArrayList getList() {
        return movieInfoList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View v;
        ImageView ivMoviePoster, ivFavorite;
        ViewHolder(View v) {
            super(v);
            this.v = v;
            ivMoviePoster =   (ImageView) v.findViewById(R.id.ivMoviePoster);
            ivFavorite =   (ImageView) v.findViewById(R.id.ivFavorite);
            ivMoviePoster.setOnClickListener(this);
            ivFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == ivMoviePoster){
                boolean isTablet = ((MainActivity)moviesCategoryFrag.getActivity()).isTablet();
                if (isTablet){
                    ((MainActivity)moviesCategoryFrag.getActivity()).getMovieDetailsFrag().show(movieInfoList.get(getLayoutPosition()));
                    return;
                }
                Intent intent = new Intent(moviesCategoryFrag.getActivity(), MovieDetailsActivity.class);
                intent.putExtra(C.INTENT_PARCELABLE_MOVIE_INFO, movieInfoList.get(getLayoutPosition()));
                moviesCategoryFrag.startActivity(intent);
            }

            else if (view == ivFavorite){
                handleFavorite((ImageView) view, getLayoutPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setData(holder, position);
        setFavIconRes(holder, position);
        startScrollAnim(position, holder.v);
    }

    private void setData(ViewHolder holder, int position) {
       if (isFavorite){
          Bitmap bitmap = CacheWrapper.getPosterFromDiskCache(movieInfoList.get(position).getId());
          holder.ivMoviePoster.setImageBitmap(bitmap);
           return;
       }
        Picasso.with(moviesCategoryFrag.getActivity()).load(getPosterUrl(position)).into(holder.ivMoviePoster);
    }

    private String getPosterUrl(int position) {
        return new StringBuilder(C.POSTER_URL_BASE)
                .append(C.POSTER_URL_RECOMMENDED_SIZE)
                .append(movieInfoList.get(position).getPosterPath()).toString();
    }

    private void setFavIconRes(ViewHolder holder, int position) {
        if (db.isMovieFavorite(movieInfoList.get(position).getId()))
            toggleFavIcon(R.drawable.ic_star_on, holder.ivFavorite);
        else
            toggleFavIcon(R.drawable.ic_star_off, holder.ivFavorite);
    }

    private void handleFavorite(ImageView img, int pos) {
        String movieId = movieInfoList.get(pos).getId();
        boolean isFav = db.isMovieFavorite(movieId);
        if (isFav){
            if (db.deleteMovie(movieId) > 0) {
                if (CacheWrapper.deletePosterFromDiskCache(movieInfoList.get(pos).getId())){
                    toggleFavIcon(R.drawable.ic_star_off, img);
                    if (isFavorite) clearItem(pos);
                }
            }
        }
        else {
            long result = db.insertFavoriteMovie(movieInfoList.get(pos));
            if (result > 0){
                moviesCategoryFrag.setMovieInfo(movieInfoList.get(pos));
                RequestFactory.downloadPosterToDiskCache(moviesCategoryFrag);
                toggleFavIcon(R.drawable.ic_star_on, img);
            }
        }
    }

    private void toggleFavIcon(int res, ImageView img) {
        img.setImageResource(res);
    }

    @Override
    public long getItemId(int arg0) {
        return (long) arg0;
    }

    @Override
    public int getItemCount() {
        return movieInfoList.size();
    }


}