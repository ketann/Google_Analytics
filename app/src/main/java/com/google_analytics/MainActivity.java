package com.google_analytics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Main Activity";
    private Tracker mTracker;
    private Button btn_second, btn_fragment, btn_event, btn_exception, btn_crash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the shared Tracker instance.
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        btn_second = (Button) findViewById(R.id.btn_second);
        btn_fragment = (Button) findViewById(R.id.btn_fragment);
        btn_event = (Button) findViewById(R.id.btn_event);
        btn_exception = (Button) findViewById(R.id.btn_exception);
        btn_crash = (Button) findViewById(R.id.btn_crashApp);

        btn_second.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
        btn_event.setOnClickListener(this);
        btn_exception.setOnClickListener(this);
        btn_crash.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Main Activity");
        //using tracker variable to set Screen Name
        mTracker.setScreenName("Main Activity");
        //sending the screen to analytics using ScreenViewBuilder() method
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_second:
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
                break;

            case R.id.btn_fragment:

               /* FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = (Fragment) fragmentManager.findFragmentByTag("TAG_FRAGMENT");
                if (fragmentManager == null) {
                    fragment = new FragmentDemo();
                    fragmentTransaction.replace(R.id.container, fragment, "TAG_FRAGMENT");
                    fragmentTransaction.commit();

                } else {
                    fragmentTransaction.remove(fragment).commit();
                }*/

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment myFragment = (Fragment) fragmentManager.findFragmentByTag("TAG_FRAGMENT");
                if (myFragment == null) {

                    myFragment = new FragmentDemo();

                    fragmentTransaction.replace(R.id.container, myFragment, "TAG_FRAGMENT");
                    fragmentTransaction.commit();

                } else {
                    fragmentTransaction.remove(myFragment).commit();


                }
                break;

            case R.id.btn_event:
                // Get tracker.
                Tracker t = ((GoogleAnalyticsApplication) getApplication()).getDefaultTracker();

                // Build and send an Event.
                t.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.categoryId))
                        .setAction(getString(R.string.actionId))
                        .setLabel(getString(R.string.labelId))
                        .build());
                Toast.makeText(MainActivity.this, "Event is recorded. Check Google Analytics!", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_exception:

                Exception e = null;

                try {
                    int num[] = {1, 2, 3, 4};
                    System.out.println(num[5]);
                } catch (Exception f) {
                    e = f;
                }
                if (e != null) {

                    Toast.makeText(MainActivity.this, "The Exception is: " + e, Toast.LENGTH_LONG).show();

                    Tracker tracker = ((GoogleAnalyticsApplication) getApplication()).getDefaultTracker();
                    tracker.send(new HitBuilders.ExceptionBuilder()
                            .setDescription(new StandardExceptionParser(MainActivity.this, null).getDescription(Thread.currentThread().getName(), e))
                            .setFatal(false)
                            .build());
                }

                break;
            case R.id.btn_crashApp:

                Toast.makeText(getApplicationContext(), "Get Ready for App Crash!", Toast.LENGTH_LONG).show();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        startCrash();

                    }

                    public void startCrash() {
                        //Manually throwing nullPointer Exception using throw keyword
                        throw null;
                    }

                };
                Handler h = new Handler();
                h.postDelayed(r, 1500);

                break;

        }
    }
}
