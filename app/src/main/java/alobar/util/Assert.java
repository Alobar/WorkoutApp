package alobar.util;

import java.util.Locale;

/**
 * Convenience functions for debug assertions
 */
public class Assert {

    public static <T> T assigned(T ref) {
        if (ref == null)
            throw new AssertionError("Reference unassigned");
        return ref;
    }

    public static void check(boolean value, String format, Object... args) {
        if (!value)
            throw new AssertionError(String.format(Locale.ENGLISH, format, args));
    }
}
