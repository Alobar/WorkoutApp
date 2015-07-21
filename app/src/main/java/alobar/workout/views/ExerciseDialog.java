package alobar.workout.views;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import alobar.util.LineBuilder;
import alobar.workout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDialog extends DialogFragment implements View.OnClickListener {

    private EditText nameEdit;
    private EditText weightEdit;

    public ExerciseDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog result = super.onCreateDialog(savedInstanceState);
        result.setTitle(R.string.exercise_dialog_title);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_exercise_dialog, container, false);
        nameEdit = (EditText) result.findViewById(R.id.nameEdit);
        weightEdit = (EditText) result.findViewById(R.id.weightEdit);
        result.findViewById(R.id.saveButton).setOnClickListener(this);
        result.findViewById(R.id.cancelButton).setOnClickListener(this);
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                String errors = validate();
                if (!TextUtils.isEmpty(errors)) {
                    Toast.makeText(getActivity(), errors, Toast.LENGTH_LONG).show();
                } else {
                    getDialog().dismiss();
                }
                break;
            case R.id.cancelButton:
                getDialog().cancel();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private String validate() {
        LineBuilder result = new LineBuilder();
        String nameText = nameEdit.getText().toString().trim();
        if (TextUtils.isEmpty(nameText)) {
            result.appendLine("Name is required");
        }
        String weightText = weightEdit.getText().toString();
        if (TextUtils.isEmpty(weightText)) {
            result.appendLine("Weight is required");
        } else {
            double weight = Double.parseDouble(weightText);
            if (weight > 15)
                result.appendLine("Weight: i don't believe you");
        }
        return result.toString();
    }
}
