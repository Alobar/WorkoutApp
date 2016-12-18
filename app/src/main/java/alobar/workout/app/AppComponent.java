package alobar.workout.app;

import android.content.res.Resources;

import javax.inject.Singleton;

import alobar.workout.db.DatabaseHelper;
import alobar.workout.db.ExerciseRepo;
import dagger.Component;

/**
 * Main Dagger component
 */
@SuppressWarnings("WeakerAccess")
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    Resources providerResources();
    DatabaseHelper provideDatabaseHelper();
    ExerciseRepo provideExerciseRepo();
}
