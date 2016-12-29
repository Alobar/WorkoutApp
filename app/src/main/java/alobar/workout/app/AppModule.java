package alobar.workout.app;

import android.app.Application;
import android.content.ContentResolver;
import android.content.res.Resources;

import javax.inject.Singleton;

import alobar.workout.db.DatabaseHelper;
import alobar.workout.db.ExerciseRepo;
import dagger.Module;
import dagger.Provides;

/**
 * Main Dagger module
 */
@Module
class AppModule {

    private final WorkoutApp application;

    AppModule(WorkoutApp application) {
        this.application = application;
    }

    @Provides
    Resources provideResources() {
        return application.getResources();
    }

    @Provides
    ContentResolver provideContentResolver() {
        return application.getContentResolver();
    }

    @Singleton
    @Provides
    AppStrings provideAppStrings() {
        return new AppStrings(application.getResources());
    }

    @Singleton
    @Provides
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(application);
    }

    @Singleton
    @Provides
    ExerciseRepo provideExerciseRepo(DatabaseHelper helper, ContentResolver resolver) {
        return new ExerciseRepo(helper.getWritableDatabase(), resolver);
    }
}
