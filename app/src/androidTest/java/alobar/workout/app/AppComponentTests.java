package alobar.workout.app;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import alobar.workout.db.DatabaseHelper;
import alobar.workout.features.main.MainActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link AppComponent}
 */
public class AppComponentTests {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void AppComponentShouldBeSingleton() {
        AppComponent component1 = App.from(mainActivityRule.getActivity()).getComponent();
        AppComponent component2 = App.from(mainActivityRule.getActivity()).getComponent();
        assertNotNull(component1);
        assertNotNull(component2);
        assertEqualIdentities(component1, component2);
    }

    @Test
    public void DatabaseHelperShouldBeSingleton() {
        AppComponent component = App.from(mainActivityRule.getActivity()).getComponent();
        DatabaseHelper helper1 = component.getDatabaseHelper();
        DatabaseHelper helper2 = component.getDatabaseHelper();
        assertNotNull(helper1);
        assertNotNull(helper2);
        assertEqualIdentities(helper1, helper2);
    }

    private void assertEqualIdentities(Object a, Object b) {
        assertEquals(System.identityHashCode(a), System.identityHashCode(b));
    }
}
