package alobar.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link Numbers}
 */
public class NumbersTests {

    @Test
    public void isNumeric() {
        assertFalse(Numbers.isNumeric(null));
        assertFalse(Numbers.isNumeric(""));
        assertFalse(Numbers.isNumeric(" "));
        assertFalse(Numbers.isNumeric("foo"));
        assertTrue(Numbers.isNumeric("1"));
        assertTrue(Numbers.isNumeric("1.0"));
    }
}
