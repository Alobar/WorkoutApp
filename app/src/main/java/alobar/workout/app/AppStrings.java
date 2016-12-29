package alobar.workout.app;

import android.content.res.Resources;

import javax.inject.Inject;

import alobar.workout.R;
import alobar.workout.features.exercise.ExercisePresenter;

/**
 * The app's resource strings
 */

public class AppStrings implements ExercisePresenter.Strings {

    private final Resources resources;

    @Inject
    public AppStrings(Resources resources) {
        this.resources = resources;
    }

    @Override
    public String exerciseNameRequired() {
        return resources.getString(R.string.exerciseNameRequired);
    }

    @Override
    public String exerciseWeightRequired() {
        return resources.getString(R.string.exerciseWeightRequired);
    }

    @Override
    public String exerciseWeightMustBeNumber() {
        return resources.getString(R.string.exerciseWeightMustBeNumber);
    }
}
