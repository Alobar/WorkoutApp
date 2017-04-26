package alobar.workout.features.exercise;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import alobar.workout.R;
import alobar.workout.features.exercise.ExercisePresenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ExercisePresenter}
 */
@RunWith(MockitoJUnitRunner.class)
public class ExercisePresenterTests {

    private static final String NameRequired = "Please, enter the name.";
    private static final String WeightRequired = "Please, enter the weight.";
    private static final String WeightMustBeNumeric = "Weight should be a number.";

    private ExercisePresenter presenter;

    @Before
    public void setUp() {
        presenter = new ExercisePresenter();
        presenter.resources = mock(Resources.class);
        when(presenter.resources.getString(R.string.exerciseNameRequired)).thenReturn(NameRequired);
        when(presenter.resources.getString(R.string.exerciseWeightRequired)).thenReturn(WeightRequired);
        when(presenter.resources.getString(R.string.exerciseWeightMustBeNumber)).thenReturn(WeightMustBeNumeric);
    }

    @Test
    public void NameChangeShouldValidate() {
        ExercisePresenter.View view = mock(ExercisePresenter.View.class);
        presenter.onStart(view);
        presenter.onNameChanged("foo");
        verify(view).setNameHint(null);
    }

    @Test
    public void WeightChangeShouldValidate() {
        ExercisePresenter.View view = mock(ExercisePresenter.View.class);
        presenter.onStart(view);
        presenter.onWeightChanged("1.0");
        verify(view).setWeightHint(null);
    }

    @Test
    public void validateName() {
        assertEquals(presenter.validateName(null), NameRequired);
        assertEquals(presenter.validateName(""), NameRequired);
        assertEquals(presenter.validateName(" "), NameRequired);
        assertEquals(presenter.validateName("foo"), null);
    }

    @Test
    public void validateWeight() {
        assertEquals(presenter.validateWeight(null), WeightRequired);
        assertEquals(presenter.validateWeight(""), WeightRequired);
        assertEquals(presenter.validateWeight(" "), WeightRequired);
        assertEquals(presenter.validateWeight("foo"), WeightMustBeNumeric);
        assertEquals(presenter.validateWeight("1.0"), null);
    }

    @Test
    public void validate() {
        assertEquals(presenter.validate("foo", "1.0"), null);
        assertEquals(presenter.validate(null, "1.0"), NameRequired);
        assertEquals(presenter.validate("foo", null), WeightRequired);
        assertEquals(presenter.validate(null, null), NameRequired + "\n" + WeightRequired);
    }
}
