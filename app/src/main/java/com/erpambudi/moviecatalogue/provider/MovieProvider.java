package com.erpambudi.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;

import com.erpambudi.moviecatalogue.room.AppDatabaseMovies;
import com.erpambudi.moviecatalogue.room.MovieDao;

import static com.erpambudi.moviecatalogue.room.MyApp.DATABASE_MOVIES_NAME;


public class MovieProvider extends ContentProvider {
    private AppDatabaseMovies appDatabaseMovies;
    private MovieDao movieDao;

    private static final String DBNAME = DATABASE_MOVIES_NAME;
    private static final String DB_TABLE = "movies";
    private static final String AUTHORITY = "com.erpambudi.moviecatalogue.provider";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_FAV_DIR = 1;
    private static final int CODE_FAV_ITEM = 2;

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, CODE_FAV_DIR);
        uriMatcher.addURI(AUTHORITY, DB_TABLE + "/#", CODE_FAV_ITEM);
    }

    public MovieProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        appDatabaseMovies = Room.databaseBuilder(getContext(), AppDatabaseMovies.class, DBNAME).build();
        movieDao = appDatabaseMovies.movieDao();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM) {
            final Context context = getContext();
            if (context == null)
                return null;
            final Cursor cursor;
            if (code == CODE_FAV_DIR)
                cursor = movieDao.selectAll();
            else
                cursor = movieDao.selectById(ContentUris.parseId(uri));
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
