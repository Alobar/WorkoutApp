package alobar.workout.features.exercise;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import alobar.workout.R;
import alobar.workout.database.DatabaseContract;

/**
 * View Adapter for Exercises
 */
public class ExerciseAdapter extends SimpleCursorAdapter {
    private static String[] FROM = new String[]{DatabaseContract.Exercise.NAME, DatabaseContract.Exercise.WEIGHT};
    private static int[] TO = new int[]{R.id.nameEdit, R.id.weightEdit};

    public ExerciseAdapter(Context context) {
        super(context, R.layout.item_exercise, null, FROM, TO, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View result = super.newView(context, cursor, parent);
        result.setTag(new ExerciseHolder(result, context));
        return result;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        Long _id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.Exercise._ID));
        holderFrom(view).bind(_id);
    }

    private ExerciseHolder holderFrom(View view) {
        return (ExerciseHolder) view.getTag();
    }
}
