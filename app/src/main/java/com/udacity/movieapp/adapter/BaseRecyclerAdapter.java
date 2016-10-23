package com.udacity.movieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;

/**
 * Created by sha on 23/10/16.
 */

public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private int lastPosition = 0;

    void startScrollAnim(int position, View v) {
        if (position <= lastPosition) {
            v.setAnimation(null);
        } else {
            TranslateAnimation scrollAnim = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.1f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f);
            scrollAnim.setDuration(300);
            v.startAnimation(scrollAnim);
            lastPosition = position;
        }
    }

    abstract ArrayList getList();

    public void clearAll() {
        ArrayList list = getList();
        if (list == null || list.isEmpty()) return;
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                list.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    void clearItem(int pos) {
        ArrayList list = getList();
        if (list == null || list.isEmpty()) return;
        list.remove(pos);
        notifyItemRemoved(pos);
    }

}
