package jp.ctake.lifecyclessample;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Lifecycle mLifecycle;

    final LifecycleObserver mObserver = new LifecycleObserver() {

        // ログが見づらくなるので
//        @OnLifecycleEvent(Lifecycle.Event.ON_START)
//        public void calledWhenOnStart(LifecycleOwner source) {
//            Log.d(TAG, "calledWhenOnStart: ON_START " + source.getLifecycle().getCurrentState().name());
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//        public void calledWhenOnStop(LifecycleOwner source) {
//            Log.d(TAG, "calledWhenOnStop: ON_STOP " + source.getLifecycle().getCurrentState().name());
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//        public void calledWhenOnDestroy(LifecycleOwner source) {
//            source.getLifecycle().removeObserver(mObserver);
//        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void calledWhenOnAny(LifecycleOwner source, Lifecycle.Event event) {
            Log.d(TAG, "calledWhenOnAny: " + event + " " + source.getLifecycle().getCurrentState().name());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: " + getLifecycle().getCurrentState().name());

        new Handler().postDelayed(() ->
                Log.d(TAG, "onCreate: after 1s " + getLifecycle().getCurrentState().name()), 1000);

        mLifecycle = getLifecycle();
        mLifecycle.addObserver(mObserver);
        // 2-2
//        lifecycle.addObserver(mObserver);
//        lifecycle.addObserver(mObserver);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v ->
                startActivity(NextActivity.getStartIntent(MainActivity.this)));

        Single.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Log.d(TAG, "end timer !!!!");
                    if (mLifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        button.setTextColor(Color.RED);
                    }
                });

        final Button dialogButton = findViewById(R.id.button2);
        dialogButton.setOnClickListener(v -> SimpleJobIntentService.enqueueWork(this, new Intent()));

        final Button sensorButton = findViewById(R.id.button3);
        sensorButton.setOnClickListener(v -> startActivity(SensorActivity.getStartIntent(this)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + getLifecycle().getCurrentState().name());

        mLifecycle.removeObserver(mObserver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart: " + getLifecycle().getCurrentState().name());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: before " + getLifecycle().getCurrentState().name());

        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: after " + getLifecycle().getCurrentState().name());
    }
}
