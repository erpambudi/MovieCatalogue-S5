package com.erpambudi.moviecatalogue.ui.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erpambudi.moviecatalogue.BuildConfig;
import com.erpambudi.moviecatalogue.R;
import com.erpambudi.moviecatalogue.adapter.MoviesAdapter;
import com.erpambudi.moviecatalogue.model.Movies;
import com.erpambudi.moviecatalogue.model.MoviesItems;
import com.erpambudi.moviecatalogue.rest.ApiClient;
import com.erpambudi.moviecatalogue.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.erpambudi.moviecatalogue.ui.search.SearchingActivity.QUERY_STRING_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesSearchingFragment extends Fragment {
    public static final String MOVIES_KEY = "movies_key";
    private RecyclerView rvFilm;
    private List<Movies> list;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;

    private ApiInterface apiServise = ApiClient.getClient().create(ApiInterface.class);


    public MoviesSearchingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        String query = getActivity().getIntent().getStringExtra(QUERY_STRING_EXTRA);

        rvFilm = root.findViewById(R.id.rv_film);
        rvFilm.setHasFixedSize(true);
        progressBar = root.findViewById(R.id.progressBar);
        getSearchMovie(query);
        showRecyclerList();

        return root;
    }

    private void showRecyclerList() {
        rvFilm.setLayoutManager(new LinearLayoutManager(getActivity()));
        moviesAdapter = new MoviesAdapter(getActivity());
    }

    private void getSearchMovie(String search) {
        showLoading(true);
        Call<MoviesItems> call = apiServise.getSearchMovie("movie", BuildConfig.API_KEY, getResources().getString(R.string.language), search);
        list = new ArrayList<>();
        call.enqueue(new Callback<MoviesItems>() {
            @Override
            public void onResponse(Call<MoviesItems> call, Response<MoviesItems> response) {
                if (response.body() != null) {
                    list = response.body().getMoviesList();
                }
                moviesAdapter.setListFilm(list);
                rvFilm.setAdapter(moviesAdapter);
                showLoading(false);
            }

            @Override
            public void onFailure(Call<MoviesItems> call, Throwable t) {
                Toast.makeText(getContext(), "Can't Load Data", Toast.LENGTH_SHORT).show();
            }
        });
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
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            moviesAdapter.setListFilm(list);
            rvFilm.setAdapter(moviesAdapter);
        }
    }

}
