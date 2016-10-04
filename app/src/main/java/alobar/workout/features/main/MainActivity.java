package alobar.workout.features.main;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import alobar.workout.R;
import alobar.workout.db.DatabaseContract;
import alobar.workout.features.exercise.ExerciseActivity;
import alobar.workout.features.exercise.ExerciseAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final int LOADER_EXERCISES = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.exerciseList)
    ListView exerciseList;

    private ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);

        adapter = new ExerciseAdapter(this);
        exerciseList.setAdapter(adapter);
        exerciseList.setEmptyView(ButterKnife.findById(this, R.id.emptyView));

        getLoaderManager().initLoader(LOADER_EXERCISES, null, exercisesLoader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_exercises, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.addExerciseItem:
                startActivity(ExerciseActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> exercisesLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getApplicationContext(), DatabaseContract.Exercise.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            adapter.changeCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            adapter.changeCursor(null);
        }
    };
}
