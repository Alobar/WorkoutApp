package alobar.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link DefaultValue}
 */
public class DefaultValueTests {

    @SuppressWarnings({"PointlessBooleanExpression", "RedundantCast"})
    @Test
    public void primitives() {
        assertTrue(DefaultValue.get(boolean.class) == false);
        assertTrue(DefaultValue.get(byte.class) == (byte) 0);
        assertTrue(DefaultValue.get(short.class) == (short) 0);
        assertTrue(DefaultValue.get(int.class) == (int) 0);
        assertTrue(DefaultValue.get(long.class) == (long) 0);
        assertTrue(DefaultValue.get(float.class) == (float) 0);
        assertTrue(DefaultValue.get(double.class) == (double) 0);
        assertTrue(DefaultValue.get(char.class) == (char) 0);
    }

    @Test
    public void reference() {
        assertNull(DefaultValue.get(Object.class));
        assertNull(DefaultValue.get(Boolean.class));
        assertNull(DefaultValue.get(Byte.class));
        assertNull(DefaultValue.get(Short.class));
        assertNull(DefaultValue.get(Integer.class));
        assertNull(DefaultValue.get(Long.class));
        assertNull(DefaultValue.get(Float.class));
        assertNull(DefaultValue.get(Double.class));
        assertNull(DefaultValue.get(Character.class));
        assertNull(DefaultValue.get(String.class));
        assertNull(DefaultValue.get(Date.class));
    }

    @Test
    public void performance() {
        final long SecondInNanos = 1000L * 1000L * 1000L;
        final int Iterations = 1000000;
        final int RequiredOperationsPerSecond = 1000000;

        {
            int warmup = 0;
            for (long i = 0; i < Iterations; i += 1) {
                warmup += DefaultValue.get(int.class);
                warmup += 1;
            }
            assertEquals(Iterations, warmup);
        }

        long actualRuntime;
        {
            long startTime = System.nanoTime();
            int accumulator = 0;
            for (long i = 0; i < Iterations; i += 1) {
                accumulator += DefaultValue.get(int.class);
                accumulator += 1;
            }
            actualRuntime = System.nanoTime() - startTime;
            assertEquals(Iterations, accumulator);
        }

        long emptyRuntime;
        {
            long startTime = System.nanoTime();
            int accumulator = 0;
            for (long i = 0; i < Iterations; i += 1) {
                accumulator += 1;
            }
            emptyRuntime = System.nanoTime() - startTime;
            assertEquals(Iterations, accumulator);
        }

        assertTrue(actualRuntime > emptyRuntime);
        long netRuntime = actualRuntime - emptyRuntime;
        long operationsPerSecond = Iterations * SecondInNanos / netRuntime;

        assertTrue(operationsPerSecond > RequiredOperationsPerSecond);
    }
}
