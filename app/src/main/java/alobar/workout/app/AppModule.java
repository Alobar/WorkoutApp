package alobar.workout.app;

import android.app.Application;
import android.content.ContentResolver;

import javax.inject.Singleton;

import alobar.workout.db.DatabaseHelper;
import alobar.workout.db.ExerciseRepo;
import alobar.workout.features.exercise.ExercisePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Main Dagger module
 */
@Module
class AppModule {

    private final Application application;

    AppModule(Application application) {
        this.application = application;
    }

    @Provides
    ContentResolver provideContentResolver() {
        return application.getContentResolver();
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(application);
    }

    @Provides
    ExerciseRepo provideExerciseRepo(DatabaseHelper helper, ContentResolver resolver) {
        return new ExerciseRepo(helper.getWritableDatabase(), resolver);
    }

    @Provides
    @Singleton
    AppStrings provideAppStrings() {
        return new AppStrings(application.getResources());
    }

    @Provides
    @Singleton
    ExercisePresenter.Strings providerExercisePresenterStrings(AppStrings strings) {
        return strings;
    }
}
