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
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
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

    @Provides
    @Singleton
    AppStrings provideAppStrings() {
        return new AppStrings(application.getResources());
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
}
