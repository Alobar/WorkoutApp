package alobar.workout.features.exercise;

import android.content.res.Resources;

import com.google.auto.value.AutoValue;

import org.apache.commons.lang3.Validate;

import javax.inject.Inject;

import alobar.reactivex.RxAssert;
import alobar.util.MessageBuilder;
import alobar.util.Numbers;
import alobar.workout.R;
import alobar.workout.data.Exercise;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Presenter for {@link ExerciseActivity}
 */
class ExercisePresenter {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private View view;
    private long exerciseId;

    @Inject
    ReadExercise readExercise;

    @Inject
    WriteExercise writeExercise;

    @Inject
    Resources resources;

    @Inject
    ExercisePresenter() {
        // Default constructor for Dagger
    }

    void onStart(View view) {
        this.view = view;
        disposables.add(view.getName().subscribe(this::onNameChanged));
        disposables.add(view.getWeight().subscribe(this::onWeightChanged));
        disposables.add(view.getSaveAction().subscribe(this::onSave));
        disposables.add(view.getCloseAction().subscribe(this::onClose));
    }

    void onStop() {
        disposables.clear();
        this.view = null;
    }

    void setExerciseId(final long id) {
        exerciseId = id;
        disposables.add(readExercise.execute(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showExercise, RxAssert::noError));
    }

    private void showExercise(Exercise exercise) {
        Validate.notNull(view);
        view.setName(exercise.name);
        view.setWeight(Double.toString(exercise.weight));
    }

    private void onNameChanged(String value) {
        Validate.notNull(view);
        view.setNameHint(validateName(value));
    }

    private void onWeightChanged(String value) {
        Validate.notNull(view);
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

    private void onSave(SaveAction action) {
        Validate.notNull(view);

        String name = action.name();
        String weight = action.weight();

        String error = validate(name, weight);
        if (error != null) {
            view.toastError(error);
            return;
        }

        double weightValue = Double.parseDouble(weight);
        writeExercise.execute(exerciseId, name, weightValue)
                .subscribe(RxAssert::noError);
        view.close();
    }

    private void onClose(Object action) {
        Validate.notNull(view);
        view.close();
    }

    public interface View {
        Observable<String> getName();
        Observable<String> getWeight();
        Observable<SaveAction> getSaveAction();
        Observable<Object> getCloseAction();
        void setName(String value);
        void setNameHint(String message);
        void setWeight(String value);
        void setWeightHint(String message);
        void toastError(String message);
        void close();
    }

    @AutoValue
    static abstract class SaveAction {
        abstract String name();
        abstract String weight();
        static SaveAction create(String name, String weight) {
            return new AutoValue_ExercisePresenter_SaveAction(name, weight);
        }
    }
}
