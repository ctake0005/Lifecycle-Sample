package jp.ctake.lifecyclessample;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

/**
 * TODO@chiharu-takenaka: Write a description of this file !
 */

public class SimpleJobIntentService extends JobIntentService {

    private static final String TAG = SimpleJobIntentService.class.getSimpleName();

    private static final int JOB_ID = 1000;

    static void enqueueWork(@NonNull Context context, @NonNull Intent work) {
        enqueueWork(context, SimpleJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork() called with: intent = [" + intent + "]");

        for (int i = 0; i < 5; i++) {
            Log.i("SimpleJobIntentService", "Running service " + (i + 1)
                    + "/5 @ " + SystemClock.elapsedRealtime());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            startActivity(DialogActivity.getStartIntent(this));
        } else {
            Toast.makeText(this, "All work complete", Toast.LENGTH_SHORT).show();
        }
    }
}
