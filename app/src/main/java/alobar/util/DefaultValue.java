package alobar.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience functions for default values. A default value is, i.e., the value a variable has if
 * left uninitialized, or the value of an auto-initialized instance field.
 */
@SuppressWarnings({"unchecked", "UnnecessaryBoxing"})
public class DefaultValue {

    private static boolean DEFAULT_BOOL;
    private static byte DEFAULT_BYTE;
    private static short DEFAULT_SHORT;
    private static int DEFAULT_INT;
    private static long DEFAULT_LONG;
    private static float DEFAULT_FLOAT;
    private static double DEFAULT_DOUBLE;
    private static char DEFAULT_CHAR;

    private static Map<Class, Object> result = new HashMap<>();

    static {
        result.put(int.class, DEFAULT_INT);
        result.put(long.class, DEFAULT_LONG);
        result.put(boolean.class, DEFAULT_BOOL);
        result.put(float.class, DEFAULT_FLOAT);
        result.put(double.class, DEFAULT_DOUBLE);
        result.put(byte.class, DEFAULT_BYTE);
        result.put(char.class, DEFAULT_CHAR);
        result.put(short.class, DEFAULT_SHORT);
    }

    /**
     * Returns the default value of the specified type/
     * <br/><br/>
     * For example: <code>DefaultValue.get(Boolean.class)</code> returns <code>false</code>.
     *
     * @param clazz The type to get the default value of
     * @param <T>   The type of the type
     * @return The default value, boxed in its wrapper class
     */
    public static <T> T get(Class<T> clazz) {
        return (T) result.get(clazz);
    }
}
