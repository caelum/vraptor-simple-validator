package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.email;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lengthBiggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lengthLessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.matches;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmpty;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmptyNorNull;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notNull;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidationStrategiesTest extends SimpleValidatorTestBase{

	protected static final String ERROR_KEY = "error.key";
	
	@Test
	public void should_verify_if_list_is_empty() {
		validator.validate(new ArrayList<String>(), notEmpty().key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_null() {
		validator.validate(null, notEmptyNorNull().key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_empty() {
		validator.validate("", notEmptyNorNull().key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_something_is_null() {
		validator.validate(null, notNull().key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_matches() {
		validator.validate("my_test", matches("my.test").key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_number_is_bigger_than_other() {
		validator.validate(0l, biggerThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_number_is_less_than_other() {
		validator.validate(1l, lessThan(0l).key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_length_is_bigger_than_a_number() {
		validator.validate("a", ValidationStrategies.lengthBiggerThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_null_string_length_is_bigger_than_a_number() {
		validator.validate(null, lengthBiggerThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}

	@Test
	public void should_not_add_error_if_string_length_is_bigger_than_a_number() {
		validator.validate("big string", lengthBiggerThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_length_is_less_than_a_number() {
		validator.validate("big string", lengthLessThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_error_because_null_string_length_is_always_less_than_a_number() {
		validator.validate(null, lengthLessThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_error_if_string_length_is_less_than_a_number() {
		validator.validate("", lengthLessThan(1l).key(ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(ERROR_KEY);
	}
	
	
	@Test
	public void should_not_add_error_if_string_matches_actually() {
		String matchingString = "my_test";
		validator.validate(matchingString, matches(matchingString).key(ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(ERROR_KEY);
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_throw_exception_if_key_is_not_present() {
		validator.validate("", notEmptyNorNull());
	}
	
	@Test
	public void should_add_error_in_email_not_valid(){
		String email ="caiocesarcaelum.com";
		validator.validate(email, email().key(ERROR_KEY));
		verify(validationStrategyHelper, times(1)).addError(ERROR_KEY);
	}
	
	@Test
	public void should_add_error_if_email_is_null(){
		String email = null;
		validator.validate(email, email().key(ERROR_KEY));
		verify(validationStrategyHelper, times(1)).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_error_in_email(){
		String email = "caiocesar.msouza@gmail.com";
		validator.validate(email, email().key(ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(ERROR_KEY);;
	}
}
