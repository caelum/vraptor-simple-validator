package br.com.caelum.vraptor.simplevalidator.strategy;

import static br.com.caelum.vraptor.simplevalidator.strategy.ValidationStrategies.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.simplevalidator.SimpleValidatorTestBase;
import br.com.caelum.vraptor.simplevalidator.strategy.ValidationStrategies;

@RunWith(MockitoJUnitRunner.class)
public class ValidationStrategiesTest extends SimpleValidatorTestBase{


	@Test
	public void should_verify_if_list_is_empty() {
		validator.validate(new ArrayList<String>(), notEmpty());
		verify(validationStrategyHelper).addError(DEFAULT_NOT_EMPTY_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_null() {
		validator.validate(null, notEmptyNorNull());
		verify(validationStrategyHelper).addError(DEFAULT_NOT_EMPTY_NOR_NULL_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_empty() {
		validator.validate("", notEmptyNorNull());
		verify(validationStrategyHelper).addError(DEFAULT_NOT_EMPTY_NOR_NULL_KEY);
	}
	
	@Test
	public void should_verify_if_something_is_null() {
		validator.validate(null, notNull());
		verify(validationStrategyHelper).addError(DEFAULT_NOT_NULL_KEY);
	}
	
	@Test
	public void should_verify_if_string_matches() {
		String matchingString = "my.test";
		validator.validate("my_test", matches(matchingString));
		verify(validationStrategyHelper).addError(DEFAULT_MATCHES_KEY, matchingString);
	}
	
	@Test
	public void should_verify_if_number_is_bigger_than_other() {
		validator.validate(0l, biggerThan(1l));
		verify(validationStrategyHelper).addError(DEFAULT_BIGGER_THAN_KEY, 1l);
	}
	
	@Test
	public void should_verify_if_number_is_less_than_other() {
		validator.validate(1l, lessThan(0l));
		verify(validationStrategyHelper).addError(DEFAULT_LESS_THAN_KEY, 0l);
	}
	
	@Test
	public void should_verify_if_string_length_is_bigger_than_a_number() {
		validator.validate("a", ValidationStrategies.lengthBiggerThan(1l));
		verify(validationStrategyHelper).addError(DEFAULT_LENGTH_BIGGER_KEY, 1l);
	}
	
	@Test
	public void should_verify_if_null_string_length_is_bigger_than_a_number() {
		validator.validate(null, lengthBiggerThan(1l));
		verify(validationStrategyHelper).addError(DEFAULT_LENGTH_BIGGER_KEY, 1l);
	}

	@Test
	public void should_not_add_error_if_string_length_is_bigger_than_a_number() {
		validator.validate("big string", lengthBiggerThan(1l));
		verify(validationStrategyHelper, never()).addError(DEFAULT_LENGTH_BIGGER_KEY, 1l);
	}
	
	@Test
	public void should_verify_if_string_length_is_less_than_a_number() {
		validator.validate("big string", lengthLessThan(1l));
		verify(validationStrategyHelper).addError(DEFAULT_LENGTH_LESS_KEY, 1l);
	}
	
	@Test
	public void should_not_add_error_because_null_string_length_is_always_less_than_a_number() {
		validator.validate(null, lengthLessThan(1l));
		verify(validationStrategyHelper, never()).addError(DEFAULT_LENGTH_LESS_KEY, 1l);
	}
	
	@Test
	public void should_not_add_error_if_string_length_is_less_than_a_number() {
		validator.validate("", lengthLessThan(1l));
		verify(validationStrategyHelper, never()).addError(DEFAULT_LENGTH_LESS_KEY, 1l);
	}
	
	
	@Test
	public void should_not_add_error_if_string_matches_actually() {
		String matchingString = "my_test";
		validator.validate(matchingString, matches(matchingString));
		verify(validationStrategyHelper, never()).addError(DEFAULT_MATCHES_KEY, matchingString);
	}
	
	@Test
	public void should_add_error_in_email_not_valid(){
		String email ="caiocesarcaelum.com";
		validator.validate(email, email());
		verify(validationStrategyHelper, times(1)).addError(DEFAULT_EMAIL_KEY);
	}
	
	@Test
	public void should_add_error_if_email_is_null(){
		String email = null;
		validator.validate(email, email());
		verify(validationStrategyHelper, times(1)).addError(DEFAULT_EMAIL_KEY);
	}
	
	@Test
	public void should_not_add_error_in_email(){
		String email = "caiocesar.msouza@gmail.com";
		validator.validate(email, email());
		verify(validationStrategyHelper, never()).addError(DEFAULT_EMAIL_KEY);
	}
	
}