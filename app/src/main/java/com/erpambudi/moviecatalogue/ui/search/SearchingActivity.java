package com.erpambudi.moviecatalogue.ui.search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.erpambudi.moviecatalogue.R;
import com.google.android.material.tabs.TabLayout;

public class SearchingActivity extends AppCompatActivity {

    public static final String QUERY_STRING_EXTRA = "QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.search_result));
        }

        SectionsSearchAdapter secttionPagerAdapter = new SectionsSearchAdapter(SearchingActivity.this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(secttionPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}
