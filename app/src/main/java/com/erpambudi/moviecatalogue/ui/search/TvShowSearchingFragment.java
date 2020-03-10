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
import com.erpambudi.moviecatalogue.rest.ApiClient;
import com.erpambudi.moviecatalogue.rest.ApiInterface;
import com.erpambudi.moviecatalogue.adapter.MoviesAdapter;
import com.erpambudi.moviecatalogue.model.Movies;
import com.erpambudi.moviecatalogue.model.MoviesItems;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.erpambudi.moviecatalogue.ui.search.SearchingActivity.QUERY_STRING_EXTRA;

public class TvShowSearchingFragment extends Fragment {
    public static final String TVSHOW_KEY = "tv_show_key";
    private RecyclerView rvTvShow;
    private List<Movies> list;
    private MoviesAdapter tvShowAdapter;
    private ProgressBar progressBar;

    private ApiInterface apiServise = ApiClient.getClient().create(ApiInterface.class);


    public TvShowSearchingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        String query = getActivity().getIntent().getStringExtra(QUERY_STRING_EXTRA);

        rvTvShow = root.findViewById(R.id.rv_film);
        rvTvShow.setHasFixedSize(true);
        progressBar = root.findViewById(R.id.progressBar);
        getSearchMovie(query);
        showRecyclerList();

        return root;
    }

    private void showRecyclerList() {
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new MoviesAdapter(getActivity());
    }

    private void getSearchMovie(String search) {
        showLoading(true);
        Call<MoviesItems> call = apiServise.getSearchMovie("tv", BuildConfig.API_KEY, getResources().getString(R.string.language), search);
        list = new ArrayList<>();
        call.enqueue(new Callback<MoviesItems>() {
            @Override
            public void onResponse(Call<MoviesItems> call, Response<MoviesItems> response) {
                if (response.body() != null) {
                    list = response.body().getMoviesList();
                }
                tvShowAdapter.setListFilm(list);
                rvTvShow.setAdapter(tvShowAdapter);
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
        outState.putParcelableArrayList(TVSHOW_KEY, new ArrayList<>(tvShowAdapter.getListFilm()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList(TVSHOW_KEY);
            tvShowAdapter.setListFilm(list);
            rvTvShow.setAdapter(tvShowAdapter);
        }
    }

}
