package alobar.workout.dagger;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for Activity related functionality
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    Activity getActivity() {
        return activity;
    }
}
