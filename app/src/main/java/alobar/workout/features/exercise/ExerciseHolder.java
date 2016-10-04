package alobar.workout.features.exercise;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import alobar.workout.R;
import alobar.workout.data.Exercise;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View Holder for Exercises
 */
public class ExerciseHolder implements PopupMenu.OnMenuItemClickListener {

    private final OnExerciseActions listener;

    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.weightText)
    TextView weightText;

    private Context context;
    private long _id;

    public ExerciseHolder(OnExerciseActions listener, View view, Context context) {
        this.listener = listener;
        ButterKnife.bind(this, view);
        this.context = context;
    }

    public void bind(Exercise exercise) {
        this._id = exercise._id;
        nameText.setText(exercise.name);
        weightText.setText(String.format("%s", exercise.weight));
    }

    @OnClick(R.id.menuButton)
    void onMenuButtonClick(View v) {
        PopupMenu menu = new PopupMenu(context, v);
        menu.inflate(R.menu.item_exercise);
        menu.setOnMenuItemClickListener(this);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.removeExerciseItem:
                listener.onDeleteExercise(_id);
                return true;
            case R.id.editExerciseItem:
                listener.onEditExercise(_id);
                return true;
            default:
                return false;
        }
    }

    public interface OnExerciseActions {
        void onEditExercise(long _id);
        void onDeleteExercise(long _id);
    }
}
