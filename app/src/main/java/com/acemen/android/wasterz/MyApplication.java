package com.acemen.android.wasterz;

import android.app.Application;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

/**
 * Created by Audrik ! on 26/03/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FirebaseCrashReportingTree());
        }

    }

    private static class FirebaseCrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FirebaseCrash.logcat(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR || priority == Log.WARN) {
                    FirebaseCrash.report(t);
                }
            }
        }
    }
}
