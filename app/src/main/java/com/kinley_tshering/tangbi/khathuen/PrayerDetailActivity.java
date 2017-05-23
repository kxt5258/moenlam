package com.kinley_tshering.tangbi.khathuen;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.lang.Integer.valueOf;

/**
 * An activity representing a single Prayer detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PrayerListActivity}.
 */
public class PrayerDetailActivity extends AppCompatActivity {

    private PrayerDetailFragment fragment;
    private MediaPlayer media;
    Menu audioMenu;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //override the menu with audio menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.audio_menu, menu);

        try {
            if (fragment.getItem().audio == R.raw.empty) {
                menu.getItem(0).setTitle(fragment.getItem().content);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
            } else {
                menu.getItem(0).setTitle(fragment.getItem().content);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);

                media = MediaPlayer.create(getApplicationContext(), fragment.getItem().audio);
                media.setOnCompletionListener(new CustomCompleteListener());
            }
        }
        catch (Exception e) {}

        audioMenu = menu;

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PrayerDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(PrayerDetailFragment.ARG_ITEM_ID));
            fragment = new PrayerDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.prayer_detail_container, fragment)
                    .commit();
        }
    }

    //stop the audio if the activity is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (media != null) {
            media.stop();
            media = null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, PrayerListActivity.class));
            return true;
        }

        if (media != null) {

            if (id == R.id.play) {
                media.start();
                audioMenu.getItem(1).setVisible(false);
                audioMenu.getItem(2).setVisible(true);
                audioMenu.getItem(3).setVisible(true);
            }
            if (id == R.id.pause) {
                if (media.isPlaying()) {
                    media.pause();
                    audioMenu.getItem(1).setVisible(true);
                    audioMenu.getItem(2).setVisible(false);
                    audioMenu.getItem(3).setVisible(true);
                }
            }
            if (id == R.id.stop) {
                media.seekTo(0);
                media.pause();
                //media.stop();
                audioMenu.getItem(1).setVisible(true);
                audioMenu.getItem(2).setVisible(false);
                audioMenu.getItem(3).setVisible(false);
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private class CustomCompleteListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            audioMenu.getItem(1).setVisible(true);
            audioMenu.getItem(2).setVisible(false);
            audioMenu.getItem(3).setVisible(false);
        }
    }
}
