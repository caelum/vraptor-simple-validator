package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.matches;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmpty;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmptyNorNull;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.util.test.MockValidator;

@RunWith(MockitoJUnitRunner.class)
public class ValidationStrategiesTest {

	private SimpleValidator gnarusValidator;
	private MockValidator defaultValidator;
	@Mock
	private Container container;
	@Mock
	private MessageHelper messageHelper;
	
	@Before
	public void setUp() {
		defaultValidator = spy(new MockValidator());
		when(messageHelper.addAlert(Mockito.anyString())).thenReturn(messageHelper);
		when(messageHelper.addError(Mockito.anyString())).thenReturn(messageHelper);
		when(messageHelper.addConfirmation(Mockito.anyString())).thenReturn(messageHelper);
		gnarusValidator = new SimpleValidator(defaultValidator, container, messageHelper);
	}
	
	@Test
	public void should_verify_if_list_is_empty() {
		String errorKey = "its.empty";
		gnarusValidator.validate(new ArrayList<String>(), notEmpty().key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_verify_if_string_is_null() {
		String errorKey = "its.empty";
		gnarusValidator.validate(null, notEmptyNorNull().key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_verify_if_string_is_empty() {
		String errorKey = "its.empty";
		gnarusValidator.validate("", notEmptyNorNull().key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_verify_if_something_is_null() {
		String errorKey = "its.empty";
		gnarusValidator.validate(null, notNull().key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_verify_if_string_matches() {
		String errorKey = "its.empty";
		gnarusValidator.validate("my_test", matches("my.test").key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_verify_if_number_is_bigger_than_other() {
		String errorKey = "its.empty";
		gnarusValidator.validate(1l, biggerThan(0l).key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_verify_if_number_is_less_than_other() {
		String errorKey = "its.empty";
		gnarusValidator.validate(0l, lessThan(1l).key(errorKey));
		verify(messageHelper).addError(errorKey);
	}
	
	@Test
	public void should_not_add_error_if_string_matches_actually() {
		String errorKey = "its.empty";
		String matchingString = "my_test";
		gnarusValidator.validate(matchingString, matches(matchingString).key(errorKey));
		verify(messageHelper, never()).addError(errorKey);
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_throw_exception_if_key_is_not_present() {
		gnarusValidator.validate("", notEmptyNorNull());
	}

}
