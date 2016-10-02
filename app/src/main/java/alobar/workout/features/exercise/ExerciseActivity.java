package alobar.workout.features.exercise;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import alobar.android.text.DebouncingTextWatcher;
import alobar.workout.R;
import alobar.workout.database.DatabaseContract;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends AppCompatActivity implements ExercisePresenter.View, View.OnClickListener {

    @BindView(R.id.nameInput)
    TextInputLayout nameInput;
    @BindView(R.id.nameEdit)
    EditText nameEdit;
    @BindView(R.id.weightInput)
    TextInputLayout weightInput;
    @BindView(R.id.weightEdit)
    EditText weightEdit;

    private ExercisePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);

        presenter = new ExercisePresenter(this);

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

        findViewById(R.id.saveButton).setOnClickListener(this);
        findViewById(R.id.cancelButton).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                String name = nameEdit.getText().toString().trim();
                String weight = weightEdit.getText().toString().trim();
                presenter.onSave(name, weight);
                break;
            case R.id.cancelButton:
                finish();
                break;
            default:
                throw new UnsupportedOperationException();
        }
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
    public void saveToDatabase(String name, double weight) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Exercise.NAME, name);
        values.put(DatabaseContract.Exercise.WEIGHT, weight);
        getContentResolver().insert(DatabaseContract.Exercise.CONTENT_URI, values);
    }

    @Override
    public void close() {
        finish();
    }
}
