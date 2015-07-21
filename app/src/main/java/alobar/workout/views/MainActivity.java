package alobar.workout.views;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import alobar.workout.R;
import alobar.workout.provider.DatabaseContract;


public class MainActivity extends AppCompatActivity {

    private static final int LOADER_EXERCISES = 1;

    private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseAdapter = new ExerciseAdapter(this);
        ListView exerciseList = (ListView) findViewById(R.id.exerciseList);
        exerciseList.setAdapter(exerciseAdapter);

        getLoaderManager().initLoader(LOADER_EXERCISES, null, exercisesLoader);
    }

    private static class ExerciseAdapter extends SimpleCursorAdapter {
        private static String[] FROM = new String[] {DatabaseContract.Exercise.NAME, DatabaseContract.Exercise.WEIGHT};
        private static int[] TO = new int[] {R.id.nameText, R.id.weightText};
        public ExerciseAdapter(Context context) {
            super(context, R.layout.item_exercise, null, FROM, TO, 0);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> exercisesLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getApplicationContext(), DatabaseContract.Exercise.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            exerciseAdapter.changeCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            exerciseAdapter.changeCursor(null);
        }
    };
}
