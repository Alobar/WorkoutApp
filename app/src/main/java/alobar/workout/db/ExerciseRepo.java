package alobar.workout.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alobar.workout.data.Exercise;

/**
 * Repository for {@link Exercise} entities
 */
@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class ExerciseRepo {

    private final SQLiteDatabase db;

    public ExerciseRepo(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Exercise> all() {
        Cursor cursor = db.rawQuery("select name, weight from " + DatabaseContract.Exercise.ENTITY_NAME, null);
        try {
            return from(cursor);
        } finally {
            cursor.close();
        }
    }

    public Exercise findById(long id) {
        Cursor cursor = db.rawQuery("select name, weight from " + DatabaseContract.Exercise.ENTITY_NAME + " where _id = ?", new String[]{Long.toString(id)});
        try {
            if (cursor.moveToNext())
                return new Exercise(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.WEIGHT))
                );
            else return null;
        } finally {
            cursor.close();
        }
    }

    public static List<Exercise> from(Cursor cursor) {
        final int nameIndex = cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.NAME);
        final int weightIndex = cursor.getColumnIndexOrThrow(DatabaseContract.Exercise.WEIGHT);
        List<Exercise> result = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            result.add(new Exercise(
                    cursor.getString(nameIndex),
                    cursor.getDouble(weightIndex)
            ));
        }
        return result;
    }
}
