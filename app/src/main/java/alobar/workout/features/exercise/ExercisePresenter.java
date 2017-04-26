package alobar.workout.features.exercise;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import javax.inject.Inject;

import alobar.reactivex.RxAssert;
import alobar.util.MessageBuilder;
import alobar.util.NullObject;
import alobar.util.Numbers;
import alobar.workout.R;
import alobar.workout.data.Exercise;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.internal.operators.observable.ObservableBlockingSubscribe;

/**
 * Presenter for {@link ExerciseActivity}
 */
class ExercisePresenter {

    private final SerialDisposable exerciseDisposable = new SerialDisposable();
    private View view = NullObject.get(View.class);
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

        this.view.name()
                .flatMap(this::validateNameAsync);

//        Observable<ChangeEvent> changeEvents = Observable.zip(
//                this.view.name(),
//                this.view.weight(),
//                ChangeEvent::create
//        );

    }

    private Observable<ValidateNameEvent> validateNameAsync(String name) {
        if (name == null || name.isEmpty())
            return Observable.just(ValidateNameEvent.create("Please, give this a name..."));
        else
            return Observable.just(ValidateNameEvent.clear());
    }

    void onStop() {
        this.view = NullObject.get(View.class);
        exerciseDisposable.dispose();
    }

    void setExerciseId(final long id) {
        exerciseId = id;
        exerciseDisposable.set(readExercise.execute(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showExercise, RxAssert::noError));
    }

    private void showExercise(Exercise exercise) {
        view.render(ViewState.create(exercise.name, Double.toString(exercise.weight)));
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
        writeExercise.execute(exerciseId, name, weightValue)
                .subscribe(RxAssert::noError);
        view.close();
    }

    public interface View {
        void setNameHint(String message);
        void setWeightHint(String message);
        void toastError(String message);
        void close();

        Observable<String> name();
        Observable<String> weight();
        Observable<SaveCommand> saveCommand();

        void render(ViewState state);
    }

    @AutoValue
    static abstract class SaveCommand {
        abstract String name();
        abstract String weight();

        static SaveCommand create(String name, String weight) {
            return new AutoValue_ExercisePresenter_SaveCommand(name, weight);
        }
    }

    @AutoValue
    static abstract class ValidateNameEvent {
        @Nullable abstract String nameHint();
        static ValidateNameEvent create(String nameHint) {
            return new AutoValue_ExercisePresenter_ValidateNameEvent(nameHint);
        }
        static ValidateNameEvent clear() {
            return new AutoValue_ExercisePresenter_ValidateNameEvent(null);
        }
    }

    @AutoValue
    static abstract class ChangeEvent {
        abstract String name();
        abstract String weight();

        static ChangeEvent create(String name, String weight) {
            return new AutoValue_ExercisePresenter_ChangeEvent(name, weight);
        }
    }

    @AutoValue
    static abstract class ViewState {
        abstract String name();
        abstract String nameHint();
        abstract String weight();
        abstract String weightHint();
        abstract Throwable error();
        abstract boolean isFinished();

        static ViewState create(String name, String weight) {
            return new AutoValue_ExercisePresenter_ViewState.Builder().name(name).weight(weight).build();
        }

        abstract ViewState.Builder builder();

        @AutoValue.Builder
        abstract static class Builder {
            abstract ViewState.Builder name(String value);
            abstract ViewState.Builder nameHint(String value);
            abstract ViewState.Builder weight(String value);
            abstract ViewState.Builder weightHint(String value);
            abstract ViewState.Builder error(Throwable value);
            abstract ViewState.Builder isFinished(boolean value);
            abstract ViewState build();
        }
    }
}
