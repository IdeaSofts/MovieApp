package com.udacity.movieapp.frag;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.squareup.picasso.Picasso;
        import com.udacity.movieapp.R;
        import com.udacity.movieapp.activity.MainActivity;
        import com.udacity.movieapp.adapter.TrailersAdapter;
        import com.udacity.movieapp.constants.C;
        import com.udacity.movieapp.listener.OnFetchReviewInfoComplete;
        import com.udacity.movieapp.listener.OnFetchTrailerInfoComplete;
        import com.udacity.movieapp.model.MovieInfo;
        import com.udacity.movieapp.model.ReviewInfo;
        import com.udacity.movieapp.model.TrailerInfo;
        import com.udacity.movieapp.request.RequestFactory;

        import java.util.ArrayList;

public class MovieDetailsFrag extends Fragment implements
        OnFetchReviewInfoComplete, OnFetchTrailerInfoComplete{
    private View rootViewDetails;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        rootViewDetails =  view.findViewById(R.id.rootView);
        ivMoviePoster  = (ImageView) view.findViewById(R.id.ivMoviePoster);

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvReleaseDate  = (TextView) view.findViewById(R.id.tvReleaseDate);
        tvAdult  = (TextView) view.findViewById(R.id.tvAdult);
        tvVoteCount  = (TextView) view.findViewById(R.id.tvVoteCount);
        tvVideo  = (TextView) view.findViewById(R.id.tvVideo);
        tvReview  = (TextView) view.findViewById(R.id.tvReview);
        recyclerTrailers  = (RecyclerView) view.findViewById(R.id.recyclerTrailers);
    }

    public void show(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
        rootViewDetails.setVisibility(View.VISIBLE);
        resetViews();
        setData();
    }

    private void resetViews() {
        tvReview.setText("");

        if (recyclerTrailers.getAdapter() == null) return;
        ((TrailersAdapter)recyclerTrailers.getAdapter()).clearAll();
    }


    private void setData() {
        String posterPathFull = new StringBuilder(C.POSTER_URL_BASE)
                .append(C.POSTER_URL_RECOMMENDED_SIZE)
                .append(movieInfo.getPosterPath())
                .toString();
        Picasso.with(getActivity()).load(posterPathFull).into(ivMoviePoster);

        String adult = base().getDisplayTxt(base().getTitle(R.string.adult), base().yesOrNo(movieInfo.getAdult()));
        String video = base().getDisplayTxt(base().getTitle(R.string.video), base().yesOrNo(movieInfo.getVideo()));
        String title = base().getDisplayTxt(base().getTitle(R.string.title), movieInfo.getTitle());
        String releaseDate = base().getDisplayTxt(base().getTitle(R.string.release_date), movieInfo.getReleaseDate());
        String voteCount = base().getDisplayTxt(base().getTitle(R.string.vote_count), movieInfo.getVoteCount());

        tvAdult.setText(adult);
        tvVideo.setText(video);
        tvTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvVoteCount.setText(voteCount);

        RequestFactory.parseTrailerInfo(this, C.REQUEST_TRAILERS_FRAG);
        RequestFactory.parseReviewInfo(this, C.REQUEST_REVIEWS_FRAG);
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
        recyclerTrailers.setAdapter(new TrailersAdapter(getActivity(), trailerInfoList));
        recyclerTrailers.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private MainActivity base(){
        return (MainActivity) getActivity();
    }

    public boolean onBackPressed(){
        if (rootViewDetails.getVisibility() == View.VISIBLE){
            rootViewDetails.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    public String getMovieId() {
        return movieInfo.getId();
    }

    public View getRootViewDetails() {
        return rootViewDetails;
    }
}