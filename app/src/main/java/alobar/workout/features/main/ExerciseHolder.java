package alobar.workout.features.main;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import alobar.workout.R;
import alobar.workout.data.Exercise;
import alobar.workout.features.main.ExerciseAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View Holder for Exercises
 */
class ExerciseHolder implements PopupMenu.OnMenuItemClickListener {

    private final Context context;
    private final ExerciseAdapter.OnActionsListener listener;
    private long _id;

    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.weightText)
    TextView weightText;

    ExerciseHolder(Context context, View view, ExerciseAdapter.OnActionsListener listener) {
        this.context = context;
        this.listener = listener;
        ButterKnife.bind(this, view);
    }

    void bind(Exercise exercise) {
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
}
