package alobar.workout.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for database related functionality
 */
@Module
public class DatabaseModule {

    @Singleton
    @Provides
    static DatabaseHelper provideDatabaseHelper(Context applicationContext) {
        return new DatabaseHelper(applicationContext);
    }

    @Singleton
    @Provides
    static SQLiteDatabase provideWritableDatabase(DatabaseHelper helper) {
        return helper.getWritableDatabase();
    }

    @Singleton
    @Provides
    static ExerciseRepo provideExerciseRepo(SQLiteDatabase database, ContentResolver resolver) {
        return new ExerciseRepo(database, resolver);
    }
}
