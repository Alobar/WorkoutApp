package alobar.workout.features.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import javax.inject.Inject;

import alobar.workout.R;
import alobar.workout.app.AppComponent;
import alobar.workout.app.WorkoutApp;
import alobar.workout.dagger.ActivityScope;
import alobar.workout.db.ExerciseRepo;
import alobar.workout.features.exercise.ExerciseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Component;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity implements ExerciseAdapter.OnActionsListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.exerciseList)
    ListView exerciseList;

    @Inject
    ExerciseRepo repo;

    @Inject
    ReadExercises readExercises;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectDependencies();

        toolbar.showOverflowMenu();
        setSupportActionBar(toolbar);

        ExerciseAdapter adapter = new ExerciseAdapter(this, this);
        exerciseList.setAdapter(adapter);
        exerciseList.setEmptyView(ButterKnife.findById(this, R.id.emptyView));

        subscriptions.add(
                readExercises.execute()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(adapter::changeItems)
                        .doOnError(Throwable::printStackTrace)
                        .subscribe());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.dispose();
    }

    private void injectDependencies() {
        DaggerMainActivity_ActivityComponent.builder()
                .appComponent(WorkoutApp.from(this).getComponent())
                .build()
                .inject(this);
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

    @Override
    public void onEditExercise(long _id) {
        startActivity(ExerciseActivity.newIntent(this, _id));
    }

    @Override
    public void onDeleteExercise(final long _id) {
        new DeleteByIdThread(repo, _id).start();
    }

    private static class DeleteByIdThread extends Thread {
        private final ExerciseRepo repo;
        private final long _id;

        DeleteByIdThread(ExerciseRepo repo, long _id) {
            this.repo = repo;
            this._id = _id;
        }

        @Override
        public void run() {
            repo.deleteById(_id);
        }
    }

    @ActivityScope
    @Component(dependencies = {AppComponent.class})
    interface ActivityComponent {
        void inject(MainActivity activity);
    }
}
