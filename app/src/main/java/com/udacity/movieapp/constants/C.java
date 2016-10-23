package com.udacity.movieapp.constants;

public class C {
/** THEMOVIEDB_API */
    public static final String THEMOVIEDB_URL_BASE = "http://api.themoviedb.org/3/movie/";
    public static final String THEMOVIEDB_API_KEY = "api_key= YOUR API KEY";
    public static final String THEMOVIEDB_API_TRAILER = "/videos?";
    public static final String THEMOVIEDB_API_REVIEW = "/reviews?";

    public static final String THEMOVIEDB_URL_ENDPOINT_POP = "popular?";
    public static final String THEMOVIEDB_URL_ENDPOINT_TOP = "top_rated?";

    public static final String POSTER_URL_BASE = "http://image.tmdb.org/t/p/";
    public static final String POSTER_URL_RECOMMENDED_SIZE = "w185";

/** JSON */
    public static final String JSON_ARRAY_RESULTS = "results";
    public static final String JSON_VAR_POSTER_PATH = "poster_path";
    public static final String JSON_VAR_ADULT = "adult";
    public static final String JSON_VAR_OVERVIEW = "overview";
    public static final String JSON_VAR_RELEASE_DATE = "release_date";
    public static final String JSON_VAR_ID = "id";
    public static final String JSON_VAR_ORIGINAL_TITLE = "original_title";
    public static final String JSON_VAR_ORIGINAL_LANG = "original_language";
    public static final String JSON_VAR_TITLE = "title";
    public static final String JSON_VAR_BACKDROP = "backdrop_path";
    public static final String JSON_VAR_POPULARITY = "popularity";
    public static final String JSON_VAR_VOTE_COUNT = "vote_count";
    public static final String JSON_VAR_VIDEO = "video";
    public static final String JSON_VAR_VOTE_AVERAGE = "vote_average";

    public static final String JSON_VAR_ISO_639_1 = "iso_639_1";
    public static final String JSON_VAR_3166_1 = "iso_3166_1";
    public static final String JSON_VAR_KEY = "key";
    public static final String JSON_VAR_NAME = "name";
    public static final String JSON_VAR_SITE = "site";
    public static final String JSON_VAR_SIZE = "size";
    public static final String JSON_VAR_TYPE = "type";

    public static final String JSON_VAR_AUTHOR = "author";
    public static final String JSON_VAR_CONTENT = "content";
    public static final String JSON_VAR_URL = "url";

/** PREFS */
    public static final String PREFS_SORT = "prefsSort";
    public static final String INTENT_PARCELABLE_MOVIE_INFO = "parcelableMoviesInfo";

/** REQUESTS */
    public static final int REQUEST_MOVIES_INFO = 1;
    public static final int REQUEST_TRAILERS_ACTIVITY = 2;
    public static final int REQUEST_TRAILERS_FRAG = 3;
    public static final int REQUEST_REVIEWS_FRAG = 4;
    public static final int REQUEST_REVIEWS_ACTIVITY = 5;
    public static final int REQUEST_DOWNLOAD_POSTER_TO_DISK_CACHE = 6;

  
/** DATABASE */
    public static final String DB_NAME = "FavoriteMovies";
    public static final String COLUMN_ID = "_id";
    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_ADULT = "adult";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_ORIGINAL_LANG = "original_language";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BACKDROP = "backdrop_path";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_VIDEO = "video";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";



    public static final String CREATE_TABLE_MOVIES =
            new StringBuilder("CREATE TABLE ")
                      .append(TABLE_MOVIES)
                      .append("(" )
                      .append(COLUMN_ID)
                      .append(" INTEGER PRIMARY KEY,")
                      .append(COLUMN_POSTER_PATH)
                      .append(" TEXT,")
                      .append(COLUMN_ADULT)
                      .append(" TEXT,")
                      .append(COLUMN_OVERVIEW)
                      .append(" TEXT,")
                      .append(COLUMN_RELEASE_DATE)
                      .append(" TEXT,")
                      .append(COLUMN_MOVIE_ID)
                      .append(" TEXT,")
                      .append(COLUMN_ORIGINAL_TITLE)
                      .append(" TEXT,")
                      .append(COLUMN_ORIGINAL_LANG)
                      .append(" TEXT,")
                      .append(COLUMN_TITLE)
                      .append(" TEXT,")
                      .append(COLUMN_BACKDROP)
                      .append(" TEXT,")
                      .append(COLUMN_POPULARITY)
                      .append(" TEXT,")
                      .append(COLUMN_VOTE_COUNT)
                      .append(" TEXT,")
                      .append(COLUMN_VIDEO)
                      .append(" TEXT,")
                      .append(COLUMN_VOTE_AVERAGE)
                      .append(" TEXT")
                      .append(" )")
                      .toString();

/** CACHE */
    public static final String DISK_CACHE_DIR = "/MovieApp";
    public static final String IMG_EXTENSION = ".jpeg";


}
