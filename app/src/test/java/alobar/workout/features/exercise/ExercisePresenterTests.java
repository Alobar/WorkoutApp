package alobar.workout.features.exercise;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import alobar.workout.db.ExerciseRepo;

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

    private ExercisePresenter.Strings strings;

    @Before
    public void setUp() {
        strings = mock(ExercisePresenter.Strings.class);
        when(strings.exerciseNameRequired()).thenReturn(NameRequired);
        when(strings.exerciseWeightRequired()).thenReturn(WeightRequired);
        when(strings.exerciseWeightMustBeNumber()).thenReturn(WeightMustBeNumeric);
    }

    @Test
    public void NameChangeShouldValidate() {
        ExercisePresenter presenter = new ExercisePresenter(mock(ExerciseRepo.class), strings);
        ExercisePresenter.View view = mock(ExercisePresenter.View.class);
        presenter.onStart(view);
        presenter.onNameChanged("foo");
        verify(view).setNameHint(null);
    }

    @Test
    public void WeightChangeShouldValidate() {
        ExercisePresenter presenter = new ExercisePresenter(mock(ExerciseRepo.class), strings);
        ExercisePresenter.View view = mock(ExercisePresenter.View.class);
        presenter.onStart(view);
        presenter.onWeightChanged("1.0");
        verify(view).setWeightHint(null);
    }

    @Test
    public void validateName() {
        ExercisePresenter presenter = new ExercisePresenter(mock(ExerciseRepo.class), strings);
        assertEquals(presenter.validateName(null), NameRequired);
        assertEquals(presenter.validateName(""), NameRequired);
        assertEquals(presenter.validateName(" "), NameRequired);
        assertEquals(presenter.validateName("foo"), null);
    }

    @Test
    public void validateWeight() {
        ExercisePresenter presenter = new ExercisePresenter(mock(ExerciseRepo.class), strings);
        assertEquals(presenter.validateWeight(null), WeightRequired);
        assertEquals(presenter.validateWeight(""), WeightRequired);
        assertEquals(presenter.validateWeight(" "), WeightRequired);
        assertEquals(presenter.validateWeight("foo"), WeightMustBeNumeric);
        assertEquals(presenter.validateWeight("1.0"), null);
    }

    @Test
    public void validate() {
        ExercisePresenter presenter = new ExercisePresenter(mock(ExerciseRepo.class), strings);
        assertEquals(presenter.validate("foo", "1.0"), null);
        assertEquals(presenter.validate(null, "1.0"), NameRequired);
        assertEquals(presenter.validate("foo", null), WeightRequired);
        assertEquals(presenter.validate(null, null), NameRequired + "\n" + WeightRequired);
    }
}
