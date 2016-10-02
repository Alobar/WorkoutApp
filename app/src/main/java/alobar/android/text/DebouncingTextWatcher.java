package alobar.android.text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * A TextWatcher that filters the system events for actual differences in text before invoking its
 * own {@link #onDebouncedTextChanged(Editable)}} callback.
 */
public abstract class DebouncingTextWatcher implements TextWatcher {
    private String before;
    private String after;

    private static boolean areEqual(String before, String after) {
        // We compare Strings because they are immutable. In Android L, TextInputEditText widgets
        // pass the same CharSequence to beforeTextChanged() and onTextChanged() but with mutated
        // contents. Storing and comparing the CharSequences would mean we do not detect changes.
        return before == null ? after == null : before.equals(after);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.before = s != null ? s.toString() : null; // storing an immutable copy
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.after = s != null ? s.toString() : null; // storing an immutable copy
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!areEqual(before, after))
            onDebouncedTextChanged(s);
        before = null;
        after = null;
    }

    /**
     * Called after the text has changed and is actually different from before
     */
    public abstract void onDebouncedTextChanged(Editable s);
}
