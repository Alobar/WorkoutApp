package alobar.workout.features.exercise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ExercisePresenter}
 */
@RunWith(MockitoJUnitRunner.class)
public class ExercisePresenterTests {

    static final String NameRequired = "Please, enter the name.";
    static final String WeightRequired = "Please, enter the weight.";
    static final String WeightMustBeNumeric = "Weight should be a number.";

    @Test
    public void NameChangeShouldValidate() {
        ExercisePresenter.View view = mock(ExercisePresenter.View.class);
        ExercisePresenter presenter = new ExercisePresenter(view);
        presenter.onNameChanged("foo");
        verify(view, times(1)).setNameHint(null);
    }

    @Test
    public void WeightChangeShouldValidate() {
        ExercisePresenter.View view = mock(ExercisePresenter.View.class);
        ExercisePresenter presenter = new ExercisePresenter(view);
        presenter.onWeightChanged("1.0");
        verify(view).setWeightHint(null);
    }

    @Test
    public void validateName() {
        assertEquals(ExercisePresenter.validateName(null), NameRequired);
        assertEquals(ExercisePresenter.validateName(""), NameRequired);
        assertEquals(ExercisePresenter.validateName(" "), NameRequired);
        assertEquals(ExercisePresenter.validateName("foo"), null);
    }

    @Test
    public void validateWeight() {
        assertEquals(ExercisePresenter.validateWeight(null), WeightRequired);
        assertEquals(ExercisePresenter.validateWeight(""), WeightRequired);
        assertEquals(ExercisePresenter.validateWeight(" "), WeightRequired);
        assertEquals(ExercisePresenter.validateWeight("foo"), WeightMustBeNumeric);
        assertEquals(ExercisePresenter.validateWeight("1.0"), null);
    }

    @Test
    public void isNumber() {
        assertFalse(ExercisePresenter.isNumber(null));
        assertFalse(ExercisePresenter.isNumber(""));
        assertFalse(ExercisePresenter.isNumber(" "));
        assertFalse(ExercisePresenter.isNumber("foo"));
        assertTrue(ExercisePresenter.isNumber("1"));
        assertTrue(ExercisePresenter.isNumber("1.0"));
    }

    @Test
    public void validate() {
        assertEquals(ExercisePresenter.validate("foo", "1.0"), null);
        assertEquals(ExercisePresenter.validate(null, "1.0"), NameRequired);
        assertEquals(ExercisePresenter.validate("foo", null), WeightRequired);
        assertEquals(ExercisePresenter.validate(null, null), NameRequired + "\n" + WeightRequired);
    }
}
