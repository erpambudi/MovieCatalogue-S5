package com.erpambudi.moviecatalogue.rest;

import com.erpambudi.moviecatalogue.model.MoviesItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/{type}")
    Call<MoviesItems> getMovie(@Path("type") String movieType, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("search/{type}")
    Call<MoviesItems> getSearchMovie(@Path("type") String movieType,@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String movieName);

    @GET("discover/movie")
    Call<MoviesItems> getNewMovies(@Query("api_key") String apiKey, @Query("primary_release_date.gte") String gte, @Query("primary_release_date.lte") String lte);

}
