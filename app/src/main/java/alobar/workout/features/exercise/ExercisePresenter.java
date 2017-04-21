package alobar.workout.features.exercise;

import android.content.res.Resources;

import javax.inject.Inject;

import alobar.util.MessageBuilder;
import alobar.util.NullObject;
import alobar.util.Numbers;
import alobar.workout.R;
import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter for {@link ExerciseActivity}
 */
class ExercisePresenter {

    @Inject
    ExerciseRepo exercises;

    @Inject
    Resources resources;

    private View view = NullObject.get(View.class);
    private SerialDisposable exerciseDisposable = new SerialDisposable();
    private long exerciseId;

    @Inject
    ExercisePresenter() {
        // Default constructor for Dagger
    }

    void onStart(View view) {
        this.view = view;
    }

    void onStop() {
        this.view = NullObject.get(View.class);
        exerciseDisposable.dispose();
    }

    void setExerciseId(final long id) {
        exerciseId = id;
        exerciseDisposable.set(Maybe.fromCallable(() -> exercises.findById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exercise -> {
                    view.setName(exercise.name);
                    view.setWeight(Double.toString(exercise.weight));
                }));
    }

    void onNameChanged(String value) {
        view.setNameHint(validateName(value));
    }

    void onWeightChanged(String value) {
        view.setWeightHint(validateWeight(value));
    }

    String validateName(String value) {
        if (value == null || value.trim().length() == 0)
            return resources.getString(R.string.exerciseNameRequired);
        return null;
    }

    String validateWeight(String value) {
        if (value == null || value.trim().length() == 0)
            return resources.getString(R.string.exerciseWeightRequired);
        if (!Numbers.isNumeric(value))
            return resources.getString(R.string.exerciseWeightMustBeNumber);
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
}
