package alobar.workout.features.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import alobar.android.text.DebouncingTextWatcher;
import alobar.workout.R;
import alobar.workout.app.AppComponent;
import alobar.workout.app.WorkoutApp;
import alobar.workout.dagger.ActivityScope;
import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Component;
import io.reactivex.Observable;

public class ExerciseActivity extends AppCompatActivity implements ExercisePresenter.View {

    private static final String ARG_EXERCISE_ID = "exerciseId";

    private long exerciseId;

    @BindView(R.id.nameInput)
    TextInputLayout nameInput;
    @BindView(R.id.nameEdit)
    EditText nameEdit;
    @BindView(R.id.weightInput)
    TextInputLayout weightInput;
    @BindView(R.id.weightEdit)
    EditText weightEdit;
    @BindView(R.id.saveButton)
    Button saveButton;

    @Inject
    ExerciseRepo exerciseRepo;
    @Inject
    ExercisePresenter presenter;

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
    public void setNameHint(String message) {
        nameInput.setError(message);
        nameInput.setErrorEnabled(message != null);
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

    @Override
    public Observable<String> name() {
        return RxTextView.textChanges(nameEdit)
                .map(CharSequence::toString)
                .distinctUntilChanged();
    }

    @Override
    public Observable<String> weight() {
        return RxTextView.textChanges(weightEdit)
                .map(CharSequence::toString)
                .distinctUntilChanged();
    }

    @Override
    public Observable<ExercisePresenter.SaveCommand> saveCommand() {
        return RxView.clicks(saveButton)
                .map(o -> ExercisePresenter.SaveCommand.create(
                        nameEdit.getText().toString(),
                        weightEdit.getText().toString())
                );
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    public void render(ExercisePresenter.ViewState state) {
        nameEdit.setText(state.name());
        nameInput.setErrorEnabled(state.nameHint() != null);
        nameInput.setError(state.nameHint());
        weightEdit.setText(state.weight());
        weightInput.setErrorEnabled(state.weightHint() != null);
        weightInput.setError(state.weight());
        if (state.error() != null) {
            Toast.makeText(this, state.error().getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (state.isFinished()) {
            finish();
        }
    }

    private void injectDependencies() {
        DaggerExerciseActivity_ActivityComponent.builder()
                .appComponent(WorkoutApp.from(this).getComponent())
                .build()
                .inject(this);
    }

    @ActivityScope
    @Component(dependencies = {AppComponent.class})
    interface ActivityComponent {
        void inject(ExerciseActivity activity);
    }
}
