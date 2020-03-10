package com.erpambudi.moviecatalogue.ui.favorite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.erpambudi.moviecatalogue.R;
import com.erpambudi.moviecatalogue.adapter.FavoriteMoviesAdapter;
import com.erpambudi.moviecatalogue.model.Movies;
import com.erpambudi.moviecatalogue.room.AppDatabaseMovies;

import java.util.ArrayList;
import java.util.List;

import static com.erpambudi.moviecatalogue.room.MyApp.dbMovies;

public class FavoriteFragment extends Fragment {

    private static final String MOVIES_KEY = "movies_key";
    private RecyclerView rvFilm;
    private FavoriteMoviesAdapter moviesAdapter;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFilm = root.findViewById(R.id.rv_film);
        rvFilm.setHasFixedSize(true);
        progressBar = root.findViewById(R.id.progressBar);

        showRecyclerList();

        if (savedInstanceState == null){
            getMovie(getActivity());
        }else {
            onActivityCreated(savedInstanceState);
        }
        return root;
    }

    private void showRecyclerList() {
        rvFilm.setLayoutManager(new LinearLayoutManager(getActivity()));
        moviesAdapter = new FavoriteMoviesAdapter(getActivity());
    }

    private void getMovie(Context context) {
        showLoading(true);
        dbMovies = Room.databaseBuilder(context,
                AppDatabaseMovies.class, "movies").allowMainThreadQueries().build();
        List<Movies> list = dbMovies.movieDao().getAll();

        moviesAdapter.setListFilm(list);
        rvFilm.setAdapter(moviesAdapter);
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
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIES_KEY, new ArrayList<>(moviesAdapter.getListFilm()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            List<Movies> list;
            list = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            moviesAdapter.setListFilm(list);
            rvFilm.setAdapter(moviesAdapter);
        }
    }
}