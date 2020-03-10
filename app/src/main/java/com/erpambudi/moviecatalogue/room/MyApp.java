package com.erpambudi.moviecatalogue.room;

import android.app.Application;

import androidx.room.Room;

public class MyApp extends Application {

    public static final String DATABASE_MOVIES_NAME = "movies";

    public static AppDatabaseMovies dbMovies;

    @Override
    public void onCreate() {
        super.onCreate();
        dbMovies = Room.databaseBuilder(getApplicationContext(),
                AppDatabaseMovies.class, DATABASE_MOVIES_NAME).allowMainThreadQueries().build();

    }
}
