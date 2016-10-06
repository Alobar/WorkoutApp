package alobar.workout.features.main;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import alobar.workout.data.Exercise;
import alobar.workout.db.DatabaseContract;
import alobar.workout.db.DatabaseHelper;
import alobar.workout.db.ExerciseRepo;

/**
 * Loader for all {@link Exercise}s
 */
public class ExercisesLoader extends AsyncTaskLoader<List<Exercise>> {

    private final DatabaseHelper helper;
    private final ContentResolver resolver;
    private ContentObserver observer;
    private List<Exercise> data;

    public ExercisesLoader(Context context) {
        super(context);
        helper = new DatabaseHelper(getContext());
        resolver = getContext().getContentResolver();
    }

    @Override
    protected void onStartLoading() {
        if (data != null)
            deliverResult(data);

        if (observer == null) {
            observer = new ForceLoadContentObserver();
            resolver.registerContentObserver(DatabaseContract.Exercise.uri(), true, observer);
        }

        if (takeContentChanged() || data == null)
            forceLoad();
    }

    @Override
    public List<Exercise> loadInBackground() {
        return new ExerciseRepo(helper.getWritableDatabase(), resolver).all();
    }

    @Override
    public void deliverResult(List<Exercise> data) {
        this.data = data;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        if (observer != null) {
            resolver.unregisterContentObserver(observer);
            observer = null;
        }
        helper.close();
    }
}
