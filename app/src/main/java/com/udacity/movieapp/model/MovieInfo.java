package com.udacity.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable{
    private String posterPath;
    private String adult;
    private String overview;
    private String releaseDate;
    private String id;
    private String originalTitle;
    private String originalLang;
    private String title;
    private String backdrop;
    private String popularity;
    private String voteCount;
    private String video;
    private String voteAverage;


    public MovieInfo(
            String posterPath,
            String adult,
            String overview,
            String releaseDate,
            String id,
            String originalTitle,
            String originalLang,
            String title,
            String backdrop,
            String popularity,
            String voteCount,
            String video,
            String voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLang = originalLang;
        this.title = title;
        this.backdrop = backdrop;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    /**THIS
     * IS
     * FOR
     * PARCELABLE
    */

    private MovieInfo(Parcel in) {
        posterPath = in.readString();
        adult = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readString();
        originalTitle = in.readString();
        originalLang = in.readString();
        title = in.readString();
        backdrop = in.readString();
        popularity = in.readString();
        voteCount = in.readString();
        video = in.readString();
        voteAverage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(posterPath);
        out.writeString(adult);
        out.writeString(overview);
        out.writeString(releaseDate);
        out.writeString(id);
        out.writeString(originalTitle);
        out.writeString(originalLang);
        out.writeString(title);
        out.writeString(backdrop);
        out.writeString(popularity);
        out.writeString(voteCount);
        out.writeString(video);
        out.writeString(voteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    /** GETTERS */

    public String getPosterPath() {
        return posterPath;
    }

    public String getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVideo() {
        return video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

}