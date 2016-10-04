package alobar.workout.features.exercise;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import alobar.workout.R;
import alobar.workout.database.DatabaseContract;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View Holder for Exercises
 */
public class ExerciseHolder implements PopupMenu.OnMenuItemClickListener {

    private Context context;
    private long _id;

    public ExerciseHolder(View view, Context context) {
        ButterKnife.bind(this, view);
        this.context = context;
    }

    public void bind(long _id) {
        this._id = _id;
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
                String where = DatabaseContract.Exercise._ID + " = ?";
                String[] whereArgs = new String[]{Long.toString(_id)};
                context.getContentResolver().delete(DatabaseContract.Exercise.CONTENT_URI, where, whereArgs);
                return true;
            case R.id.editExerciseItem:
                context.startActivity(ExerciseActivity.newIntent(context, _id));
                return true;
            default:
                return false;
        }
    }
}
