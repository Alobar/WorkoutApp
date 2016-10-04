package alobar.workout.features.exercise;

import alobar.util.MessageBuilder;
import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;

/**
 * Presenter for {@link ExerciseActivity}
 */
public class ExercisePresenter {

    private final View view;
    private final ExerciseRepo exercises;

    public ExercisePresenter(View view, ExerciseRepo exercises) {
        this.view = view;
        this.exercises = exercises;
    }

    public void setExerciseId(long id) {
        Exercise exercise = exercises.findById(id);
        if (exercise != null) {
            view.setName(exercise.name);
            view.setWeight(Double.toString(exercise.weight));
        }
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
        if (!isNumber(value))
            return "Weight should be a number.";
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    static String validate(String name, String weight) {
        MessageBuilder errors = new MessageBuilder(new StringBuilder());
        errors.appendLine(validateName(name));
        errors.appendLine(validateWeight(weight));
        return errors.length() > 0 ? errors.toString() : null;
    }

    public void onSave(String name, String weight) {
        String error = validate(name, weight);
        if (error != null)
            view.toastError(error);
        else {
            view.saveToDatabase(name, Double.parseDouble(weight)); // todo: do better
            view.close();
        }
    }

    public interface View {
        void setName(String value);
        void setNameHint(String message);
        void setWeight(String value);
        void setWeightHint(String message);
        void toastError(String message);
        void saveToDatabase(String name, double weight);
        void close();
    }
}
