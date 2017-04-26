package alobar.lab;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by rob on 25/04/17.
 */

@AutoValue
public abstract class SaveCommand extends Event {
    @Nullable
    abstract String name();

    static SaveCommand create(String name) {
        return new AutoValue_SaveCommand(name);
    }
}
