package alobar.workout.features.main;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import alobar.workout.R;
import alobar.workout.data.Exercise;
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

    private ExerciseAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);

        adapter2 = new ExerciseAdapter(this);
        exerciseList.setAdapter(adapter2);
        exerciseList.setEmptyView(ButterKnife.findById(this, R.id.emptyView));

        getSupportLoaderManager().initLoader(LOADER_EXERCISES, null, exercisesLoader2);
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

    private LoaderManager.LoaderCallbacks<List<Exercise>> exercisesLoader2 = new LoaderManager.LoaderCallbacks<List<Exercise>>() {
        @Override
        public android.support.v4.content.Loader<List<Exercise>> onCreateLoader(int id, Bundle args) {
            return new ExercisesLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<List<Exercise>> loader, List<Exercise> data) {
            adapter2.changeItems(data);
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<List<Exercise>> loader) {
            adapter2.changeItems(null);
        }
    };
}
