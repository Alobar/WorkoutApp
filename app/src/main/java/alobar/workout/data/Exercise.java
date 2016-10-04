package alobar.workout.data;

/**
 * Immutable value for exercises
 */
public class Exercise {

    public final String name;
    public final double weight;

    public Exercise(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }
}
