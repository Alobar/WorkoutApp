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
            DatabaseContract.Exercise.tableName + " ( " +
            DatabaseContract.Exercise._ID + " INTEGER PRIMARY KEY" +
            ", " + DatabaseContract.Exercise.NAME + " TEXT NOT NULL" +
            ", " + DatabaseContract.Exercise.WEIGHT + " REAL NOT NULL" +
            ")";

    private static final String LOAD_STATEMENT = "INSERT INTO " +
            DatabaseContract.Exercise.tableName + " (" +
            DatabaseContract.Exercise.NAME + ", " +
            DatabaseContract.Exercise.WEIGHT +
            ") VALUES ('Dumbbell chest press', 20.0), ('Barbell squat', 58.0), ('Dumbbell row', 20.0), ('Elevated lunge, dumbbells', 15.0), ('Dumbbell shoulder press', 14.0)";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
        db.execSQL(LOAD_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new AssertionError("Unreachable");
    }
}
