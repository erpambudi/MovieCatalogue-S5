package com.erpambudi.moviecatalogue.ui.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erpambudi.moviecatalogue.BuildConfig;
import com.erpambudi.moviecatalogue.MainActivity;
import com.erpambudi.moviecatalogue.R;
import com.erpambudi.moviecatalogue.model.Movies;
import com.erpambudi.moviecatalogue.room.AppDatabaseMovies;

import java.util.List;

import static com.erpambudi.moviecatalogue.room.MyApp.dbMovies;

public class DetailMoviesActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";

    ImageView ivFilm;
    TextView tvNama, tvRelease, tvOverview, tvRating;
    private ProgressBar progressBar;
    private Movies detail;
    private int love;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        ivFilm = findViewById(R.id.iv_img_film);
        tvNama = findViewById(R.id.tv_name);
        tvRelease = findViewById(R.id.tv_release_date);
        tvRating = findViewById(R.id.tv_rating);
        tvOverview = findViewById(R.id.tv_overview);
        progressBar = findViewById(R.id.progress);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.detail));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        detail = getIntent().getParcelableExtra(EXTRA_FILM);
        int id = detail.getId();
        fetchData(id);
        if (savedInstanceState == null){
            setData(detail);
        }else{
            onRestoreInstanceState(savedInstanceState);
        }

    }

    private void setData(Movies data){
        showLoading(true);
        String txt = "";
        String photo = data.getPoster();
        Log.d("DetailMovieActivity","getPoster: "+data.getPoster());
        String name = data.getTitle();
        double rating = data.getVoteAverage();
        String overview = data.getOverview();
        String release = data.getReleaseDate();

        Glide.with(this).load(BuildConfig.POSTER_PATH + photo).into(ivFilm);
        tvNama.setText(name);
        tvRating.setText(String.valueOf(rating));
        tvOverview.setText(overview);
        tvRelease.setText(release);

        if (data.getOverview().equals(txt)){
            tvOverview.setText(this.getResources().getString(R.string.overview_nothing));
        }else {
            tvOverview.setText(overview);
        }
        showLoading(false);
    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("DetailMovieActivity","onSaveInstanceState detail data: "+detail.getPoster());
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_FILM, detail);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d("DetailMovieActivity","onRestoreInstanceState: running");
        super.onRestoreInstanceState(savedInstanceState);
        Movies detail;
        detail = savedInstanceState.getParcelable(EXTRA_FILM);
        setData(detail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_no_favorite);
        if (love == 1){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));
        }else{
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp));
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int getId = item.getItemId();

        if (getId == R.id.menu_no_favorite) {
            if (love == 0) {
                item.setIcon(R.drawable.ic_favorite_white_24dp);
                dbMovies.movieDao().insertMovie(detail);
                love = 1;
                Toast.makeText(DetailMoviesActivity.this, getResources().getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();
            } else {
                item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                dbMovies.movieDao().deleteMovie(detail);
                love = 0;
                Toast.makeText(DetailMoviesActivity.this, getResources().getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
            }
        }
        if (getId == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchData(int id){
        dbMovies = Room.databaseBuilder(this,
                AppDatabaseMovies.class, "movies").allowMainThreadQueries().build();
        List<Movies> list = dbMovies.movieDao().getAll();
        for (int i = 0; i < list.size() ; i++) {
            if (list.get(i).getId() == id){
                love = 1;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivityForResult(new Intent(DetailMoviesActivity.this, MainActivity.class), 200);
    }
}
