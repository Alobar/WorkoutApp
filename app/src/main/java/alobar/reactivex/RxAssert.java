package alobar.reactivex;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Convenience functions for asserting ReactiveX events
 */
public class RxAssert {

    /**
     * Assert that no error is emitted
     */
    public static void noError(Throwable throwable) {
        throw new AssertionError("Observed an error", throwable);
    }

    /**
     * Assert that no error is emitted
     */
    public static CompletableObserver noError() {
        return AssertNoErrorCompletableObserverSingleton.INSTANCE;
    }

    private static class AssertNoErrorCompletableObserverSingleton {
        final static AssertNoErrorCompletableObserver INSTANCE = new AssertNoErrorCompletableObserver();
    }

    private static class AssertNoErrorCompletableObserver implements CompletableObserver {

        @Override
        public void onSubscribe(@NonNull Disposable disposable) {
            // no operation
        }

        @Override
        public void onComplete() {
            // no operation
        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            throw new AssertionError("Observed an error", throwable);
        }
    }
}
