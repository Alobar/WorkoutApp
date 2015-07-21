package alobar.workout.adapters;

import android.content.Context;
import android.widget.SimpleCursorAdapter;

import alobar.workout.R;
import alobar.workout.provider.DatabaseContract;

/**
 * Created by rob on 21/07/15.
 */
public class ExerciseAdapter extends SimpleCursorAdapter {
    private static String[] FROM = new String[] {DatabaseContract.Exercise.NAME, DatabaseContract.Exercise.WEIGHT};
    private static int[] TO = new int[] {R.id.nameText, R.id.weightText};
    public ExerciseAdapter(Context context) {
        super(context, R.layout.item_exercise, null, FROM, TO, 0);
    }
}
