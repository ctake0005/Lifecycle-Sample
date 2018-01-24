package jp.ctake.lifecyclessample;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SensorActivity extends AppCompatActivity {

    private static final String TAG = SensorActivity.class.getSimpleName();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SensorActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        final SensorHandler sensorHandler = new SensorHandler(this);
        getLifecycle().addObserver(sensorHandler);
    }

    public static class SensorHandler implements SensorEventListener, LifecycleObserver {

        private final SensorManager mSensorManager;
        private final Sensor mAccelerometer;

        public SensorHandler(Context context) {
            mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void calledWhenOnResume(LifecycleOwner source) {
            Log.d(TAG, "calledWhenOnResume() called with: source = [" + source + "]");
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void calledWhenOnPause(LifecycleOwner source) {
            Log.d(TAG, "calledWhenOnPause() called with: source = [" + source + "]");
            mSensorManager.unregisterListener(this);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void calledWhenOnDestroy(LifecycleOwner source) {
            Log.d(TAG, "calledWhenOnDestroy() called with: source = [" + source + "]");
            source.getLifecycle().removeObserver(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.d(TAG, "onSensorChanged() called with: event = [" + event + "]");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG, "onAccuracyChanged() called with: sensor = [" + sensor + "], accuracy = [" + accuracy + "]");
        }
    }
}
