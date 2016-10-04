package alobar.workout.features.main;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import alobar.workout.data.Exercise;
import alobar.workout.db.DatabaseHelper;
import alobar.workout.db.ExerciseRepo;

/**
 * Loader for all {@link Exercise}s
 */
public class ExercisesLoader extends AsyncTaskLoader<List<Exercise>> {

    private final DatabaseHelper helper;

    public ExercisesLoader(Context context) {
        super(context);
        helper = new DatabaseHelper(getContext());
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Exercise> loadInBackground() {
        return new ExerciseRepo(helper.getWritableDatabase()).all();
    }

    @Override
    protected void onReset() {
        helper.close();
    }
}
