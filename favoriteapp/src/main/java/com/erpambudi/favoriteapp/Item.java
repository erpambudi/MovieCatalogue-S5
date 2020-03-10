package com.erpambudi.favoriteapp;

import android.net.Uri;

public class Item {
        public static final String TABLE_NAME = "movies";
        public static final String AUTHORITY = "com.erpambudi.moviecatalogue.provider";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "overview";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_RATING = "vote_average";
        public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

}
