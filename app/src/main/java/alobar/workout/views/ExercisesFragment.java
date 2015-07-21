package alobar.workout.views;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;

import alobar.workout.adapters.ExerciseAdapter;
import alobar.workout.provider.DatabaseContract;

public class ExercisesFragment extends ListFragment {

    private static final int LOADER_EXERCISES = 1;

    public ExercisesFragment() {
        /* Required default constructor */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ExerciseAdapter(getActivity()));
        getLoaderManager().initLoader(LOADER_EXERCISES, null, exercisesLoader);
    }

    @Override
    public ExerciseAdapter getListAdapter() {
        return (ExerciseAdapter) super.getListAdapter();
    }

    private LoaderManager.LoaderCallbacks<Cursor> exercisesLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), DatabaseContract.Exercise.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            getListAdapter().changeCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            getListAdapter().changeCursor(null);
        }
    };
}
