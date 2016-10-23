package com.udacity.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.movieapp.R;
import com.udacity.movieapp.model.TrailerInfo;

import java.util.ArrayList;

public class TrailersAdapter extends BaseRecyclerAdapter<TrailersAdapter.ViewHolder> {

    private ArrayList<TrailerInfo> trailerInfoList;
    private Context context;

    public TrailersAdapter(Context context, ArrayList<TrailerInfo> trailerInfoList) {
        this.trailerInfoList = trailerInfoList;
        this.context = context;
    }

    @Override
    ArrayList getList() {
        return trailerInfoList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View v;
        TextView tvName;

        ViewHolder(View v) {
            super(v);
            this.v = v;
            tvName =   (TextView) v.findViewById(R.id.tvName);
        }

        @Override
        public void onClick(View view) {
            if (view == tvName){
                String url = new StringBuilder()
                        .append("http://www.youtube.com/watch?v=")
                        .append(trailerInfoList.get((getLayoutPosition())).getKey())
                        .toString();
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setData(holder, position);
        startScrollAnim(position, holder.v);
    }

    private void setData(ViewHolder holder, int position) {
        holder.tvName.setText(trailerInfoList.get(position).getName());
    }

    @Override
    public long getItemId(int arg0) {
        return (long) arg0;
    }

    @Override
    public int getItemCount() {
        return trailerInfoList.size();
    }


}