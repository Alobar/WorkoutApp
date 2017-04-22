package alobar.workout.features.main;

import javax.inject.Inject;

import alobar.workout.data.Exercise;
import alobar.workout.db.ExerciseRepo;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

/**
 * Use case for deleting an {@link Exercise}
 */
class DeleteExercise {

    @Inject
    ExerciseRepo exerciseRepo;

    @Inject
    DeleteExercise() {
        // Default constructor for Dagger
    }

    Completable execute(long exerciseId) {
        return Completable.create(emitter -> exerciseRepo.deleteById(exerciseId))
                .subscribeOn(Schedulers.io());
    }
}
