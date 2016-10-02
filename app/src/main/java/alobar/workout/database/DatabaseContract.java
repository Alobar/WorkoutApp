package alobar.workout.database;

import android.net.Uri;
import android.provider.BaseColumns;

import alobar.workout.BuildConfig;

/**
 * Database contract
 */
public class DatabaseContract {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".WorkoutProvider";

    private static final Uri AUTHORITY_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).build();

    /** Constants for Exercise table contract. */
    public interface Exercise {
        /* Name of table */
        String ENTITY_NAME = "exercise";

        /** Content URI for this table. */
        Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, ENTITY_NAME);

        /** Content column names */
        String _ID = BaseColumns._ID;
        String NAME = "name";
        String WEIGHT = "weight";
    }
}
