package com.erpambudi.moviecatalogue.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.erpambudi.moviecatalogue.model.Movies;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class AppDatabaseMovies extends RoomDatabase {
    public abstract MovieDao movieDao();
}
