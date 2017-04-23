package alobar.android.text;

import android.support.annotation.NonNull;
import android.text.Editable;

import org.junit.Test;

import alobar.util.NullObject;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link DebouncingTextWatcher}
 */
public class DebouncingTextWatcherTests {

    private final SpyWatcher watcher = new SpyWatcher();

    @Test
    public void nullToNull() {
        assertEquals(0, watcher.callCount);
        watcher.beforeTextChanged(null, 0, 0, 0);
        watcher.onTextChanged(null, 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(0, watcher.callCount);
    }

    @Test
    public void nullToEmpty() {
        assertEquals(0, watcher.callCount);
        watcher.beforeTextChanged(null, 0, 0, 0);
        watcher.onTextChanged("", 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(1, watcher.callCount);
    }

    @Test
    public void emptyToNull() {
        assertEquals(0, watcher.callCount);
        watcher.beforeTextChanged("", 0, 0, 0);
        watcher.onTextChanged(null, 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(1, watcher.callCount);
    }

    @Test
    public void unchanged() {
        assertEquals(0, watcher.callCount);
        watcher.beforeTextChanged("foo", 0, 0, 0);
        watcher.onTextChanged("foo", 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(0, watcher.callCount);
    }

    @Test
    public void added() {
        assertEquals(0, watcher.callCount);
        watcher.beforeTextChanged("foo", 0, 0, 0);
        watcher.onTextChanged("foobar", 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(1, watcher.callCount);
    }

    @Test
    public void removed() {
        assertEquals(0, watcher.callCount);
        watcher.beforeTextChanged("foobar", 0, 0, 0);
        watcher.onTextChanged("foo", 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(1, watcher.callCount);
    }

    @Test
    public void mutableCharSequence() {
        char[] foo = new char[]{'f', 'o', 'o'};
        char[] bar = new char[]{'b', 'a', 'r'};

        MutableCharSequence s = new MutableCharSequence();

        s.setCharacters(foo);
        watcher.beforeTextChanged(s, 0, 0, 0);
        s.setCharacters(bar);
        watcher.onTextChanged(s, 0, 0, 0);
        watcher.afterTextChanged(NullObject.get(Editable.class));
        assertEquals(1, watcher.callCount);
    }

    private static class SpyWatcher extends DebouncingTextWatcher {
        public int callCount = 0;

        @Override
        public void onDebouncedTextChanged(Editable s) {
            this.callCount += 1;
        }
    }

    private static class MutableCharSequence implements CharSequence {
        public char[] characters;

        public void setCharacters(char[] characters) {
            this.characters = characters;
        }

        @Override
        public int length() {
            return characters != null ? characters.length : 0;
        }

        @Override
        public char charAt(int index) {
            return characters[index];
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            throw new UnsupportedOperationException();
        }

        @NonNull
        @Override
        public String toString() {
            return new String(characters);
        }
    }
}
