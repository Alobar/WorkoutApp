package alobar.workout.features.main;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import alobar.workout.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests for {@link MainActivity}
 */
public class MainActivityTests {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldStart() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void addExercise() {
        onView(withId(R.id.addExerciseItem)).perform(click());
        onView(withId(R.id.nameInput)).check(matches(isDisplayed()));
    }
}
