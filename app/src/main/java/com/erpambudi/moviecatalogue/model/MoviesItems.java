package com.erpambudi.moviecatalogue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesItems {

    @SerializedName("results")
    ArrayList<Movies> moviesList;

    public ArrayList<Movies> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(ArrayList<Movies> moviesList) {
        this.moviesList = moviesList;
    }


}
