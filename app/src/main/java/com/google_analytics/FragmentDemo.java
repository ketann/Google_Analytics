package com.google_analytics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;



/**
 * Created by GURUKRUPA on 10/27/2016.
 */

public class FragmentDemo extends Fragment {
    private static final String TAG = "FragmentDemo";
    private Tracker mTracker;

    public FragmentDemo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo, container, false);

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        mTracker.setScreenName("Fragment Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

}
