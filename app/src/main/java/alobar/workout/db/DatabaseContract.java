package alobar.workout.db;

import android.provider.BaseColumns;

/**
 * Database contract
 */
public class DatabaseContract {

    /**
     * Constants for Exercise table.
     */
    public interface Exercise {
        /* Name of table */
        String tableName = "exercise";

        // Content column names
        String _ID = BaseColumns._ID;
        String NAME = "name";
        String WEIGHT = "weight";
    }
}
