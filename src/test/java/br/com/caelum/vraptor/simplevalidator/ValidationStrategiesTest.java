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

	protected static final String ERROR_KEY = "error.key";
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
		gnarusValidator.validate(new ArrayList<String>(), notEmpty().key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_null() {
		gnarusValidator.validate(null, notEmptyNorNull().key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_empty() {
		gnarusValidator.validate("", notEmptyNorNull().key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_something_is_null() {
		gnarusValidator.validate(null, notNull().key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_matches() {
		gnarusValidator.validate("my_test", matches("my.test").key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_number_is_bigger_than_other() {
		gnarusValidator.validate(0l, biggerThan(1l).key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_number_is_less_than_other() {
		gnarusValidator.validate(1l, lessThan(0l).key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_error_if_string_matches_actually() {
		String matchingString = "my_test";
		gnarusValidator.validate(matchingString, matches(matchingString).key(ERROR_KEY));
		verify(messageHelper, never()).addError(ERROR_KEY);
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_throw_exception_if_key_is_not_present() {
		gnarusValidator.validate("", notEmptyNorNull());
	}

}
