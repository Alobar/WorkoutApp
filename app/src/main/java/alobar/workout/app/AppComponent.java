package alobar.workout.app;

import javax.inject.Singleton;

import alobar.workout.db.DatabaseModule;
import alobar.workout.db.ExerciseRepo;
import alobar.android.dagger.SystemComponent;
import alobar.android.dagger.SystemModule;
import dagger.Component;

/**
 * Main Dagger component
 */
@SuppressWarnings("WeakerAccess")
@Singleton
@Component(modules = {DatabaseModule.class, SystemModule.class})
public interface AppComponent extends SystemComponent {
    ExerciseRepo provideExerciseRepo();
}
