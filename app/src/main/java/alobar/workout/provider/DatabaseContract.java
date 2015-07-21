package alobar.workout.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import alobar.workout.BuildConfig;

/**
 * Created by rob on 20/07/15.
 */
public class DatabaseContract {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".WorkoutProvider";

    private static final Uri AUTHORITY_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).build();

    /** Constants for Notebooks table contract. */
    public interface Exercise extends BaseColumns {
        /* Name of table */
        String ENTITY_NAME = "exercise";
        /** Content URI for this table. */
        Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, ENTITY_NAME);
        /** Content column names */
        String NAME = "name";
        String WEIGHT = "weight";
    }
}
