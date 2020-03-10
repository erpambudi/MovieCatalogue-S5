package com.erpambudi.moviecatalogue.room;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.erpambudi.moviecatalogue.model.Movies;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("Select * from movies")
    List<Movies> getAll();

    @Query("SELECT * FROM movies")
    Cursor selectAll();

    @Query("SELECT * FROM movies where id = :uid")
    Cursor selectById(long uid);

    @Insert
    void  insertMovie(Movies movies);

    @Delete
    void deleteMovie(Movies movies);

}
