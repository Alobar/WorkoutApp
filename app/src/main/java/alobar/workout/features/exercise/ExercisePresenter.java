package alobar.workout.features.exercise;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import alobar.util.MessageBuilder;
import alobar.util.NullObject;
import alobar.util.Numbers;
import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Presenter for {@link ExerciseActivity}
 */
public class ExercisePresenter {

    private final ExerciseRepo exercises;
    private final Strings strings;
    private View view;
    private long exerciseId;
    private Subscription exerciseSubscription;

    @Inject
    ExercisePresenter(ExerciseRepo exercises, Strings strings) {
        this.exercises = exercises;
        this.strings = strings;
        this.view = NullObject.get(View.class);
    }

    void onStart(View view) {
        this.view = view;
    }

    void onStop() {
        this.view = NullObject.get(View.class);
        if (exerciseSubscription != null && !exerciseSubscription.isUnsubscribed())
            exerciseSubscription.unsubscribe();
    }

    void setExerciseId(final long id) {
        exerciseSubscription = Observable
                .fromCallable(new Callable<Exercise>() {
                    @Override
                    public Exercise call() throws Exception {
                        return exercises.findById(id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Exercise>() {
                    @Override
                    public void call(Exercise exercise) {
                        exerciseId = id;
                        if (exercise != null) {
                            view.setName(exercise.name);
                            view.setWeight(Double.toString(exercise.weight));
                        }
                    }
                });
    }

    void onNameChanged(String value) {
        view.setNameHint(validateName(value));
    }

    void onWeightChanged(String value) {
        view.setWeightHint(validateWeight(value));
    }

    String validateName(String value) {
        if (value == null || value.trim().length() == 0)
            return strings.exerciseNameRequired();
        return null;
    }

    String validateWeight(String value) {
        if (value == null || value.trim().length() == 0)
            return strings.exerciseWeightRequired();
        if (!Numbers.isNumeric(value))
            return strings.exerciseWeightMustBeNumber();
        return null;
    }

    String validate(String name, String weight) {
        MessageBuilder errors = new MessageBuilder(new StringBuilder());
        errors.appendLine(validateName(name));
        errors.appendLine(validateWeight(weight));
        return errors.length() > 0 ? errors.toString() : null;
    }

    void onSave(String name, String weight) {
        String error = validate(name, weight);
        if (error != null) {
            view.toastError(error);
            return;
        }

        double weightValue = Double.parseDouble(weight);
        exercises.save(new Exercise(exerciseId, name, weightValue));
        view.close();
    }

    public interface View {
        void setName(String value);
        void setNameHint(String message);
        void setWeight(String value);
        void setWeightHint(String message);
        void toastError(String message);
        void close();
    }

    public interface Strings {
        String exerciseNameRequired();
        String exerciseWeightRequired();
        String exerciseWeightMustBeNumber();
    }
}
