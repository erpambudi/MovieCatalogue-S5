package com.erpambudi.moviecatalogue.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.erpambudi.moviecatalogue.BuildConfig;
import com.erpambudi.moviecatalogue.R;
import com.erpambudi.moviecatalogue.reminder.ReminderReceiver;

public class ReminderActivity extends AppCompatActivity {
    private Switch swDailyReminder;
    private Switch swReminderNewFilm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEdit;
    private ReminderReceiver reminderReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        swDailyReminder = findViewById(R.id.sw_day);
        swReminderNewFilm = findViewById(R.id.sw_new_film);

        sharedPreferences = getSharedPreferences(BuildConfig.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        reminderReceiver = new ReminderReceiver(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.notifications));
        }

        listenSwitchChanged();
        setPreferences();

    }

    private void listenSwitchChanged() {
        swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("daily_reminder", isChecked);
                sharedPreferenceEdit.apply();
                if (isChecked) {
                    reminderReceiver.setDailyReminder();
                } else {
                    reminderReceiver.cancelDailyReminder(getApplicationContext());
                }
            }
        });
        swReminderNewFilm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferenceEdit = sharedPreferences.edit();
                sharedPreferenceEdit.putBoolean("release_reminder", isChecked);
                sharedPreferenceEdit.apply();
                if (isChecked) {
                    reminderReceiver.setReleaseTodayReminder();
                } else {
                    reminderReceiver.cancelReleaseToday(getApplicationContext());
                }
            }
        });
    }

    private void setPreferences() {
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);

        swDailyReminder.setChecked(dailyReminder);
        swReminderNewFilm.setChecked(releaseReminder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

}
