package com.google_analytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "Second Activity";
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker= application.getDefaultTracker();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume:");
        mTracker.setScreenName("Second Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
