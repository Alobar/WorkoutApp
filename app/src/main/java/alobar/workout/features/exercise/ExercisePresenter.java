package alobar.workout.features.exercise;

import java.util.concurrent.Callable;

import alobar.util.Assert;
import alobar.util.MessageBuilder;
import alobar.util.NullObject;
import alobar.util.Numbers;
import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Presenter for {@link ExerciseActivity}
 */
public class ExercisePresenter {

    private final ExerciseRepo exercises;
    private View view;
    private long exerciseId;
    private Subscription exerciseSubscription;

    public ExercisePresenter(ExerciseRepo exercises) {
        this.exercises = exercises;
        this.view = NullObject.get(View.class);
    }

    public void onStart(View view) {
        this.view = view;
    }

    public void onStop() {
        this.view = NullObject.get(View.class);
        if (exerciseSubscription != null && !exerciseSubscription.isUnsubscribed())
            exerciseSubscription.unsubscribe();
    }

    public void setExerciseId(final long id) {
        exerciseSubscription = Observable
                .fromCallable(new Callable<Exercise>() {
                    @Override
                    public Exercise call() throws Exception {
                        return exercises.findById(id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .filter(new Func1<Exercise, Boolean>() {
                    @Override
                    public Boolean call(Exercise exercise) {
                        return exercise != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Exercise>() {
                    @Override
                    public void call(Exercise exercise) {
                        exerciseId = id;
                        view.setName(exercise.name);
                        view.setWeight(Double.toString(exercise.weight));
                    }
                });
    }

    public void onNameChanged(String value) {
        view.setNameHint(validateName(value));
    }

    public void onWeightChanged(String value) {
        view.setWeightHint(validateWeight(value));
    }

    static String validateName(String value) {
        if (value == null || value.trim().length() == 0)
            return "Please, enter the name.";
        return null;
    }

    static String validateWeight(String value) {
        if (value == null || value.trim().length() == 0)
            return "Please, enter the weight.";
        if (!Numbers.isNumeric(value))
            return "Weight should be a number.";
        return null;
    }

    static String validate(String name, String weight) {
        MessageBuilder errors = new MessageBuilder(new StringBuilder());
        errors.appendLine(validateName(name));
        errors.appendLine(validateWeight(weight));
        return errors.length() > 0 ? errors.toString() : null;
    }

    public void onSave(String name, String weight) {
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
}
