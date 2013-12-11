package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.and;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategiesTest.ERROR_KEY;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AndValidationStrategyTest extends SimpleValidatorTestBase{
	
	@Test
	public void should_verify_multiple_validations_with_one_key() {
		validator.validate(0l, and(lessThan(2l), biggerThan(0l)).key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_errors_if_everyting_goes_fine() {
		validator.validate(1l, and(lessThan(2l), biggerThan(0l)).key(ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_two_errors_to_and_rule() {
		validator.validate(1l, and(lessThan(0l), biggerThan(2l)).key(ERROR_KEY));
		verify(validationStrategyHelper, times(1)).addError(ERROR_KEY);
	}

}
