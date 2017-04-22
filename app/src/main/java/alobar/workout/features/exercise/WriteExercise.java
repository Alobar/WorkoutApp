package alobar.workout.features.exercise;

import javax.inject.Inject;

import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

/**
 * Use case for writing an Exercise
 */
class WriteExercise {

    @Inject
    ExerciseRepo exerciseRepo;

    @Inject
    WriteExercise() {
        // Default constructor for Dagger
    }

    /**
     * Creates or updates an Exercise with the specified data
     *
     * @param _id    Id of the exercise to update, or 0 to create it
     * @param name   Name of the exercise
     * @param weight Weight of the exercise
     */
    Completable execute(long _id, String name, double weight) {
        return Completable.fromAction(() -> exerciseRepo.save(new Exercise(_id, name, weight)))
                .subscribeOn(Schedulers.io());
    }
}
