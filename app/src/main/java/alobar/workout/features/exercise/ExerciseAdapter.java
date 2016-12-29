package alobar.workout.features.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import alobar.workout.R;
import alobar.workout.data.Exercise;

/**
 * View Adapter for Exercises
 */
public class ExerciseAdapter extends BaseAdapter {

    private final Context context;
    private final ExerciseHolder.OnExerciseActions listener;
    private final LayoutInflater inflater;
    private List<Exercise> items;

    public ExerciseAdapter(Context context, ExerciseHolder.OnExerciseActions listener) {
        super();
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Exercise getItem(int i) {
        return items != null ? items.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return items != null ? items.get(i)._id : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exercise, parent, false);
            convertView.setTag(new ExerciseHolder(context, convertView, listener));
        }
        ((ExerciseHolder) convertView.getTag()).bind(getItem(position));
        return convertView;
    }

    public void changeItems(List<Exercise> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
