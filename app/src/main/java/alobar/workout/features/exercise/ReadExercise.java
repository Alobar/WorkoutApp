package alobar.workout.features.exercise;

import javax.inject.Inject;

import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

/**
 * Use case for reading an Exercise
 */
class ReadExercise {

    @Inject
    ExerciseRepo exerciseRepo;

    @Inject
    ReadExercise() {
        // Default constructor for Dagger
    }

    /**
     * Return a Maybe emitting the specified Exercise, or completes if not found
     *
     * @param exerciseId Id of exercise to read
     */
    Maybe<Exercise> execute(long exerciseId) {
        return Maybe.fromCallable(() -> exerciseRepo.findById(exerciseId))
                .subscribeOn(Schedulers.io());
    }
}
