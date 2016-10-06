package alobar.workout.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link DatabaseContract}
 */
public class DatabaseContractTests {

    @Test
    public void exercises_uri() {
        assertEquals("content://com.alobarproductions.workout.provider/exercise",
                DatabaseContract.Exercise.uri().toString());

        assertEquals("content://com.alobarproductions.workout.provider/exercise/123",
                DatabaseContract.Exercise.uri(123).toString());
    }
}
