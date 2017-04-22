package alobar.workout.features.main;

import android.app.Activity;

import javax.inject.Inject;

import alobar.workout.features.exercise.ExerciseActivity;

/**
 * Navigator for {@link MainActivity}
 */
class MainNavigator {

    @Inject
    Activity activity;

    @Inject
    MainNavigator() {
        // Default constructor for Dagger
    }

    /**
     * Navigate to the Edit Exercise activity for the specified exercise
     */
    void showEditExercise(long exerciseId) {
        activity.startActivity(ExerciseActivity.newIntent(activity, exerciseId));
    }

    /**
     * Navigate to the Add Exercise activity
     */
    void showAddExercise() {
        activity.startActivity(ExerciseActivity.newIntent(activity));
    }
}
