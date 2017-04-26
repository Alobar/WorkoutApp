package alobar.lab;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by rob on 25/04/17.
 */
@AutoValue
public abstract class ValidateEvent extends Event {
    @Nullable
    abstract String nameHint();

    static ValidateEvent create(String nameHint) {
        return new AutoValue_ValidateEvent(nameHint);
    }
}
