package alobar.workout.features.exercise;


import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import alobar.android.text.DebouncingTextWatcher;
import alobar.workout.R;
import alobar.workout.database.DatabaseContract;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDialog extends DialogFragment implements ExercisePresenter.View, View.OnClickListener {

    @BindView(R.id.nameInput)
    TextInputLayout nameInput;
    @BindView(R.id.nameEdit)
    EditText nameEdit;
    @BindView(R.id.weightInput)
    TextInputLayout weightInput;
    @BindView(R.id.weightEdit)
    EditText weightEdit;

    private Unbinder unbinder;

    private ExercisePresenter presenter;

    public ExerciseDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ExercisePresenter(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog result = super.onCreateDialog(savedInstanceState);
        result.setTitle(R.string.exercise_dialog_title);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_exercise_dialog, container, false);

        unbinder = ButterKnife.bind(this, result);

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

        result.findViewById(R.id.saveButton).setOnClickListener(this);
        result.findViewById(R.id.cancelButton).setOnClickListener(this);

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                getDialog().cancel();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void saveToDatabase(String name, double weight) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Exercise.NAME, name);
        values.put(DatabaseContract.Exercise.WEIGHT, weight);
        getActivity().getContentResolver().insert(DatabaseContract.Exercise.CONTENT_URI, values);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        getDialog().dismiss();
    }
}
