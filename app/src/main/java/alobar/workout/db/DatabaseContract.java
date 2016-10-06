package alobar.workout.db;

import android.net.Uri;
import android.provider.BaseColumns;

import alobar.workout.BuildConfig;

/**
 * Database contract
 */
public class DatabaseContract {

    private static final String Authority = BuildConfig.APPLICATION_ID + ".provider";

    /**
     * Constants for Exercise table.
     */
    public static class Exercise {
        /* Name of table */
        public static final String tableName = "exercise";

        // Content column names
        public static final String _ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String WEIGHT = "weight";

        /**
         * Content notification uri for exercises
         *
         * @return notification uri
         */
        public static Uri uri() {
            return new Uri.Builder().scheme("content").authority(Authority).path(tableName).build();
        }

        /**
         * Content notification uri for the specified exercise
         *
         * @param exerciseId the id of the exercise
         * @return notification uri
         */
        public static Uri uri(long exerciseId) {
            return Uri.withAppendedPath(uri(), Long.toString(exerciseId));
        }
    }
}
