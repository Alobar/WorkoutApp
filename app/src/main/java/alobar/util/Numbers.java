package alobar.util;

/**
 * Convenience functions for numbers
 */
public class Numbers {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }
}
