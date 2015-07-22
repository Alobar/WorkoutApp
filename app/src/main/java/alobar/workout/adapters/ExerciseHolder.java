package alobar.workout.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.Toast;

import alobar.workout.R;
import alobar.workout.provider.DatabaseContract;

/**
 * Created by rob on 22/07/15.
 */
public class ExerciseHolder implements OnClickListener, PopupMenu.OnMenuItemClickListener {

    private Context context;
    private long _id;

    public ExerciseHolder(View view, Context context) {
        View menuButton = view.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this);
        this.context = context;
    }

    public void bind(long _id) {
        this._id = _id;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuButton:
                PopupMenu menu = new PopupMenu(context, v);
                menu.inflate(R.menu.item_exercise);
                menu.setOnMenuItemClickListener(this);
                menu.show();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.removeExerciseItem:
                String where = DatabaseContract.Exercise._ID + " = ?";
                String[] whereArgs = new String[] { Long.toString(_id) };
                context.getContentResolver().delete(DatabaseContract.Exercise.CONTENT_URI, where, whereArgs);
                return true;
            default:
                return false;
        }
    }
}
