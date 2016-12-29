package alobar.workout.features.main;

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
class ExerciseAdapter extends BaseAdapter {

    private final Context context;
    private final OnActionsListener listener;
    private final LayoutInflater inflater;
    private List<Exercise> items;

    ExerciseAdapter(Context context, OnActionsListener listener) {
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

    void changeItems(List<Exercise> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    interface OnActionsListener {
        void onEditExercise(long _id);
        void onDeleteExercise(long _id);
    }
}
