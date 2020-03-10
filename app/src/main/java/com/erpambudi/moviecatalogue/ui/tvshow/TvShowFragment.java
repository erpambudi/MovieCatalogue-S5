package com.erpambudi.moviecatalogue.ui.tvshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
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

public class TvShowFragment extends Fragment {

    public static final String TV_SHOW_KEY = "tv_show_key";
    private RecyclerView rvTvSow;
    private List<Movies> list;
    private MoviesAdapter tvShowAdapter;
    private ProgressBar progressBar;
    private ApiInterface apiServise = ApiClient.getClient().create(ApiInterface.class);

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tvshow, container, false);

        rvTvSow = root.findViewById(R.id.rv_tv_show);
        rvTvSow.setHasFixedSize(true);
        progressBar = root.findViewById(R.id.progressBar);

        showRecyclerList();
        if (savedInstanceState == null){
            getDataTvShow();
        }else {
            onActivityCreated(savedInstanceState);
        }
        return root;
    }

    private void showRecyclerList(){
        rvTvSow.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new MoviesAdapter(getActivity());
    }

    private void getDataTvShow(){
        showLoading(true);
        Call<MoviesItems> call = apiServise.getMovie("tv", BuildConfig.API_KEY, getActivity().getResources().getString(R.string.language));
        list = new ArrayList<>();
        call.enqueue(new Callback<MoviesItems>() {
            @Override
            public void onResponse(Call<MoviesItems> call, Response<MoviesItems> response) {
                if (response.body() != null){
                    list = response.body().getMoviesList();
                }
                tvShowAdapter.setListFilm(list);
                rvTvSow.setAdapter(tvShowAdapter);
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
        outState.putParcelableArrayList(TV_SHOW_KEY, new ArrayList<>(tvShowAdapter.getListFilm()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList(TV_SHOW_KEY);
            tvShowAdapter.setListFilm(list);
            rvTvSow.setAdapter(tvShowAdapter);
        }
    }
}