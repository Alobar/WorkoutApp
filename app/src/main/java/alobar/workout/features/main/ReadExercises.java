package alobar.workout.features.main;

import android.content.ContentResolver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import alobar.reactivex.RxContentObserver;
import alobar.workout.data.Exercise;
import alobar.workout.db.DatabaseContract;
import alobar.workout.db.ExerciseRepo;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Use case for reading all {@link Exercise}s
 */
class ReadExercises {

    @Inject
    ContentResolver contentResolver;

    @Inject
    ExerciseRepo exerciseRepo;

    @Inject
    ReadExercises() {
        // Default constructor for Dagger
    }

    /**
     * Return an Observable with all Exercises, emitting at subscription and content change notifications.
     */
    Observable<List<Exercise>> execute() {
        return RxContentObserver.create(contentResolver, DatabaseContract.Exercise.uri(), true)
                .throttleWithTimeout(100, TimeUnit.MILLISECONDS)
                .startWith(DatabaseContract.Exercise.uri())
                .flatMap(__ -> Observable.just(exerciseRepo.all()))
                .subscribeOn(Schedulers.io());
    }
}
