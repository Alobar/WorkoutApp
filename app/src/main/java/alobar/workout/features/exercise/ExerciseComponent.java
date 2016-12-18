package alobar.workout.features.exercise;

import alobar.workout.app.AppComponent;
import alobar.workout.dagger.ActivityScope;
import dagger.Component;

/**
 * Dagger component for {@link ExerciseActivity}
 */
@ActivityScope
@Component(modules = {ExerciseModule.class}, dependencies = {AppComponent.class})
interface ExerciseComponent {
    void inject(ExerciseActivity activity);
}
