package alobar.workout.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite Database helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "Workout";

    private static final String CREATE_STATEMENT = "CREATE TABLE " +
            DatabaseContract.Exercise.ENTITY_NAME + " ( " +
            DatabaseContract.Exercise._ID + " INTEGER PRIMARY KEY" +
            ", " + DatabaseContract.Exercise.NAME + " TEXT NOT NULL" +
            ", " + DatabaseContract.Exercise.WEIGHT + " INTEGER NOT NULL" +
            ")";

    private static final String LOAD_STATEMENT = "INSERT INTO " +
            DatabaseContract.Exercise.ENTITY_NAME + " (" +
            DatabaseContract.Exercise.NAME + ", " +
            DatabaseContract.Exercise.WEIGHT +
            ") VALUES ('Arm push', 45.5)";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        // Creates the main table
        db.execSQL(CREATE_STATEMENT);
        db.execSQL(LOAD_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // todo
    }
}
