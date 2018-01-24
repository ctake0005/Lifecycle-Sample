package jp.ctake.lifecyclessample;

import android.app.Application;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;

/**
 * TODO@chiharu-takenaka: Write a description of this file !
 */

public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();

    final LifecycleObserver mObserver = new GenericLifecycleObserver() {
        @Override
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            Log.d(TAG, "onStateChanged: " + source.getLifecycle().getCurrentState().name() + ", " + event.name());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(mObserver);
    }
}
