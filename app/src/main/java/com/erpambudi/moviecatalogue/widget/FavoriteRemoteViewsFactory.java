package com.erpambudi.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.erpambudi.moviecatalogue.BuildConfig;
import com.erpambudi.moviecatalogue.R;
import com.erpambudi.moviecatalogue.model.Movies;
import com.erpambudi.moviecatalogue.room.AppDatabaseMovies;
import com.erpambudi.moviecatalogue.room.MovieDao;

import java.util.ArrayList;

public class FavoriteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList<Movies> mWidgetItems = new ArrayList<>();
    private AppDatabaseMovies database;
    private Context mContext;

    public FavoriteRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        final long identityToken = Binder.clearCallingIdentity();
        database = Room.databaseBuilder(mContext.getApplicationContext(), AppDatabaseMovies.class, "movies")
                .allowMainThreadQueries()
                .build();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDataSetChanged() {
        try {
            MovieDao movieDAO = database.movieDao();
            mWidgetItems.clear();
            mWidgetItems.addAll(movieDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        database.close();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Uri img  = Uri.parse(BuildConfig.POSTER_PATH + mWidgetItems.get(position).getPoster());

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(img)
                    .apply(new RequestOptions().fitCenter())
                    .submit(800, 550)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
