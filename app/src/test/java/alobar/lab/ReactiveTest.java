package alobar.lab;

import org.junit.Test;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

/**
 * Created by rob on 25/04/17.
 */
public class ReactiveTest {

    @Test
    public void foo() {

        final SaveCommand saveFoo = SaveCommand.create("foo");
        final SaveCommand saveBar = SaveCommand.create("bar");

        Observable<Event> saveCommands = Observable.just("foo", "bar")
                .flatMap(name -> validateName(name))
                .flatMap(SaveCommand::create);

        TestObserver<Event> observer = saveCommands.test();

        observer.assertSubscribed();
        observer.assertComplete();
        observer.assertTerminated();
        observer.assertValueCount(2);
        observer.assertValueSequence(Arrays.asList(saveFoo, saveBar));

    }

    private Observable<ValidateEvent> validateName(String name) {
        String result = null;
        if (name == null || name.isEmpty())
            result = "Name is required";
        return Observable.just(ValidateEvent.create(result));
    }

}
