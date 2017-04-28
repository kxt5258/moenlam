package com.kinley_tshering.tangbi.khathuen;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Kinley Tshering on 4/28/17.
 */

public class PrayerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Jomolhari.tff")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}