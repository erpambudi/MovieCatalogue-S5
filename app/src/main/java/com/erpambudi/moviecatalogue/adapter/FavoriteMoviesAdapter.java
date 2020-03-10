package com.erpambudi.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.erpambudi.moviecatalogue.BuildConfig;
import com.erpambudi.moviecatalogue.R;
import com.erpambudi.moviecatalogue.model.Movies;
import com.erpambudi.moviecatalogue.ui.movies.DetailMoviesActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder> {

    private List<Movies> listFilm = new ArrayList<>();
    private Context context;

    public FavoriteMoviesAdapter(Context context) {
        this.context = context;
    }

    public List<Movies> getListFilm() {
        return listFilm;
    }

    public void setListFilm(List<Movies> listFilm) {
        this.listFilm = listFilm;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_movies, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String txt = "";
        final Movies film = listFilm.get(position);
        Log.d("MoviesAdapter", "data: " + film.getId());
        holder.txtName.setText(film.getTitle());
        holder.tvVoteAverage.setText(String.valueOf(film.getVoteAverage()));

        Glide.with(context)
                .load(BuildConfig.POSTER_PATH + film.getPoster())
                .into(holder.imgPhoto);

        holder.cvItemFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMoviesActivity.class);
                intent.putExtra(DetailMoviesActivity.EXTRA_FILM, listFilm.get(position));
                context.startActivity(intent);
            }
        });

        if (film.getOverview().equals(txt)) {
            holder.txtOverview.setText(context.getResources().getString(R.string.overview_nothing));
        } else {
            holder.txtOverview.setText(film.getOverview());
        }

    }

    @Override
    public int getItemCount() {
        return listFilm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtOverview;
        TextView tvVoteAverage;
        ImageView imgPhoto;
        final CardView cvItemFilm;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtOverview = itemView.findViewById(R.id.txt_overview);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            cvItemFilm = itemView.findViewById(R.id.cv_item_film);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average);
        }
    }

}
