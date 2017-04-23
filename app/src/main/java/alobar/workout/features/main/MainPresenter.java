package alobar.workout.features.main;

import org.apache.commons.lang3.Validate;

import java.util.List;

import javax.inject.Inject;

import alobar.reactivex.RxAssert;
import alobar.workout.data.Exercise;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Presenter for {@link MainActivity}
 */
class MainPresenter {

    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private View view;

    @Inject
    MainNavigator mainNavigator;

    @Inject
    ReadExercises readExercises;

    @Inject
    DeleteExercise deleteExercise;

    @Inject
    MainPresenter() {
        // Default constructor for Dagger
    }

    void onStart(View view) {
        this.view = Validate.notNull(view);

        subscriptions.add(
                readExercises.execute()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::setExercises, RxAssert::noError));
    }

    void onStop() {
        subscriptions.clear();
        view = null;
    }

    void onAddExerciseClick() {
        mainNavigator.showAddExercise();
    }

    void onEditExercise(long exerciseId) {
        mainNavigator.showEditExercise(exerciseId);
    }

    void onDeleteExercise(long exerciseId) {
        deleteExercise.execute(exerciseId)
                .subscribe(RxAssert::noError);
    }

    interface View {
        void setExercises(List<Exercise> exercises);
    }
}
