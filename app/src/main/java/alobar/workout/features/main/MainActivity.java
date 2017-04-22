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
import alobar.workout.dagger.ActivityModule;
import alobar.workout.dagger.ActivityScope;
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
    MainNavigator mainNavigator;

    @Inject
    ReadExercises readExercises;

    @Inject
    DeleteExercise deleteExercise;

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
                .activityModule(new ActivityModule(this))
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
                mainNavigator.showAddExercise();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEditExercise(long _id) {
        mainNavigator.showEditExercise(_id);
    }

    @Override
    public void onDeleteExercise(final long _id) {
        deleteExercise.execute(_id)
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    @ActivityScope
    @Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
    interface ActivityComponent {
        void inject(MainActivity activity);
    }
}
