package com.erpambudi.favoriteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {
    private Cursor mCursor;
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MovieViewHolder holder, int position) {
        holder.bind(mCursor.moveToPosition(position));
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtOverview;
        ImageView imgPhoto;
        TextView txtRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            txtRating = itemView.findViewById(R.id.tv_vote_average);
        }

        public void bind(boolean moveToPosition) {
            if (moveToPosition) {
                txtName.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Item.COLUMN_TITLE)));
                txtOverview.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Item.COLUMN_DESCRIPTION)));
                txtRating.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Item.COLUMN_RATING)));
                Glide.with(context).load(Item.POSTER_BASE_URL + mCursor.getString(mCursor.getColumnIndexOrThrow(Item.COLUMN_POSTER))).into(imgPhoto);
            }
        }
    }
}
