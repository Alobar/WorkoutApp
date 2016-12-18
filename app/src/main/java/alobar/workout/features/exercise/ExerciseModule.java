package alobar.workout.features.exercise;

import alobar.workout.app.AppStrings;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for {@link ExerciseComponent}
 */
@Module
class ExerciseModule {

    @Provides
    ExercisePresenter.Strings providerExercisePresenterStrings(AppStrings strings) {
        return strings;
    }
}
