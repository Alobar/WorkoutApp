package alobar.workout.app;

import javax.inject.Singleton;

import alobar.workout.db.DatabaseHelper;
import alobar.workout.features.exercise.ExerciseActivity;
import alobar.workout.features.main.MainActivity;
import dagger.Component;

/**
 * Main Dagger component
 */
@SuppressWarnings("WeakerAccess")
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ExerciseActivity activity);

    DatabaseHelper getDatabaseHelper();
}
