package alobar.workout.features.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import alobar.workout.R;
import alobar.workout.app.AppComponent;
import alobar.workout.app.WorkoutApp;
import alobar.workout.dagger.ActivityModule;
import alobar.workout.dagger.ActivityScope;
import alobar.workout.data.Exercise;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Component;


public class MainActivity extends AppCompatActivity implements MainPresenter.View, ExerciseAdapter.OnActionsListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.exerciseList)
    ListView exerciseList;

    @Inject
    MainPresenter presenter;

    private ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectDependencies();

        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);

        adapter = new ExerciseAdapter(this, this);
        exerciseList.setAdapter(adapter);
        exerciseList.setEmptyView(ButterKnife.findById(this, R.id.emptyView));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
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
                presenter.onAddExerciseClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEditExercise(long _id) {
        presenter.onEditExercise(_id);
    }

    @Override
    public void onDeleteExercise(final long _id) {
        presenter.onDeleteExercise(_id);
    }

    @Override
    public void setExercises(List<Exercise> exercises) {
        adapter.changeItems(exercises);
    }

    private void injectDependencies() {
        DaggerMainActivity_ActivityComponent.builder()
                .appComponent(WorkoutApp.from(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @ActivityScope
    @Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
    interface ActivityComponent {
        void inject(MainActivity activity);
    }
}
