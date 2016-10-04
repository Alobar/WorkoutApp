package alobar.workout.data;

/**
 * Immutable value for exercises
 */
public class Exercise {

    public final long _id;
    public final String name;
    public final double weight;

    public Exercise(long _id, String name, double weight) {
        this._id = _id;
        this.name = name;
        this.weight = weight;
    }
}
