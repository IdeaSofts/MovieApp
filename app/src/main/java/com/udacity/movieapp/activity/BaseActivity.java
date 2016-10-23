package com.udacity.movieapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.movieapp.R;
import com.udacity.movieapp.constants.C;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    public String getTitle(int res) {
        return getString(res);
    }

    public String getDisplayTxt(String title, String val) {
        return new StringBuilder(title)
                .append(": ")
                .append(val).toString();
    }

    public String yesOrNo(String val) {
        if (val.equals("true")) return getString(R.string.yes);
        return getString(R.string.no);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void putSortInPrefs(String sort) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(C.PREFS_SORT, sort)
                .apply();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.loading));
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
