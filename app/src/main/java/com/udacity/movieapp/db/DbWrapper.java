package com.udacity.movieapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.movieapp.constants.C;
import com.udacity.movieapp.model.MovieInfo;

import java.util.ArrayList;

/**
 * Created by sha on 22/10/16.
 */

public class DbWrapper extends SQLiteOpenHelper {
    private static DbWrapper dbInstance;

    public static  DbWrapper getInstance(Context ctx) {
        if (dbInstance == null) {
            dbInstance = new DbWrapper(ctx.getApplicationContext());
        }
        return dbInstance;
    }


    private DbWrapper(Context context) {
        super(context, C.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(C.CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    private boolean isCursorInvalid(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0){
            if (cursor != null) cursor.close();
            return true;
        }
        return false;
    }

    public long insertFavoriteMovie(MovieInfo m){
        ContentValues values = new ContentValues();

        values.put(C.COLUMN_POSTER_PATH , m.getPosterPath());
        values.put(C.COLUMN_ADULT , m.getAdult());
        values.put(C.COLUMN_OVERVIEW , m.getOverview());
        values.put(C.COLUMN_RELEASE_DATE , m.getReleaseDate());
        values.put(C.COLUMN_MOVIE_ID, m.getId());
        values.put(C.COLUMN_ORIGINAL_TITLE , m.getOriginalTitle());
        values.put(C.COLUMN_ORIGINAL_LANG , m.getOriginalLang());
        values.put(C.COLUMN_TITLE , m.getTitle());
        values.put(C.COLUMN_BACKDROP , m.getBackdrop());
        values.put(C.COLUMN_POPULARITY , m.getPopularity());
        values.put(C.COLUMN_VOTE_COUNT , m.getVoteCount());
        values.put(C.COLUMN_VIDEO , m.getVideo());
        values.put(C.COLUMN_VOTE_AVERAGE , m.getVoteAverage());

       return getWritableDatabase().insert(C.TABLE_MOVIES, null, values);
    }

    public ArrayList<MovieInfo> getFavoriteMovies(){
      Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + C.TABLE_MOVIES, null);

        if (isCursorInvalid(cursor)) return null;

        ArrayList<MovieInfo> movieInfoList = new ArrayList<>();

        while (cursor.moveToNext()){
            MovieInfo movieInfo = new MovieInfo(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13));

            movieInfoList.add(movieInfo);
        }

        cursor.close();
        return movieInfoList;
    }

    public boolean isMovieFavorite(String id){
        Cursor cursor = getReadableDatabase()
                .query(
                        C.TABLE_MOVIES,
                        null,
                        C.COLUMN_MOVIE_ID + "=? ",
                        new String[]{id},
                        null, null, null);
        return !isCursorInvalid(cursor);
    }

    public int deleteMovie(String id){
      return getWritableDatabase().delete(C.TABLE_MOVIES, C.COLUMN_MOVIE_ID + "=? ", new String[]{id});
    }
}
