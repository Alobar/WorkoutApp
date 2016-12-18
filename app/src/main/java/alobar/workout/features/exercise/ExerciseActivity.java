package alobar.workout.features.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import alobar.android.text.DebouncingTextWatcher;
import alobar.workout.R;
import alobar.workout.app.AppModule;
import alobar.workout.app.WorkoutApp;
import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExerciseActivity extends AppCompatActivity implements ExercisePresenter.View {

    private static final String ARG_EXERCISE_ID = "exerciseId";

    @BindView(R.id.nameInput)
    TextInputLayout nameInput;
    @BindView(R.id.nameEdit)
    EditText nameEdit;
    @BindView(R.id.weightInput)
    TextInputLayout weightInput;
    @BindView(R.id.weightEdit)
    EditText weightEdit;

    @Inject
    ExerciseRepo exerciseRepo;
    @Inject
    ExercisePresenter presenter;

    private long exerciseId;

    public static Intent newIntent(Context context) {
        return newIntent(context, Exercise.INVALID_ID);
    }

    public static Intent newIntent(Context context, long exerciseId) {
        Intent result = new Intent(context, ExerciseActivity.class);
        result.putExtra(ARG_EXERCISE_ID, exerciseId);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);
        injectDependencies();

        exerciseId = getIntent().getLongExtra(ARG_EXERCISE_ID, Exercise.INVALID_ID);

        nameEdit.addTextChangedListener(new DebouncingTextWatcher() {
            @Override
            public void onDebouncedTextChanged(Editable s) {
                presenter.onNameChanged(s.toString());
            }
        });
        weightEdit.addTextChangedListener(new DebouncingTextWatcher() {
            @Override
            public void onDebouncedTextChanged(Editable s) {
                presenter.onWeightChanged(s.toString());
            }
        });
    }

    private void injectDependencies() {
        DaggerExerciseComponent.builder()
                .appComponent(WorkoutApp.from(this).getComponent())
                .exerciseModule(new ExerciseModule())
                .build()
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
        presenter.setExerciseId(exerciseId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @OnClick(R.id.saveButton)
    void onSaveButtonClick() {
        String name = nameEdit.getText().toString().trim();
        String weight = weightEdit.getText().toString().trim();
        presenter.onSave(name, weight);
    }

    @OnClick(R.id.cancelButton)
    void onCancelButtonClick() {
        finish();
    }

    @Override
    public void setName(String value) {
        nameEdit.setText(value);
    }

    @Override
    public void setNameHint(String message) {
        nameInput.setError(message);
        nameInput.setErrorEnabled(message != null);
    }

    @Override
    public void setWeight(String value) {
        weightEdit.setText(value);
    }

    @Override
    public void setWeightHint(String message) {
        weightInput.setError(message);
        weightInput.setErrorEnabled(message != null);
    }

    @Override
    public void toastError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }
}
