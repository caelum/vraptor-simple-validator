package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.and;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.matches;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmptyNorNull;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategiesTest.NUMBER_ERROR_KEY;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

@RunWith(MockitoJUnitRunner.class)
public class SimpleValidatorTest {
	private SimpleValidator validator;
	private MockValidator defaultValidator;
	@Mock
	private Container container;
	@Mock
	private DefaultMessageHelper messageHelper;
	
	@Before
	public void setUp() {
		defaultValidator = spy(new MockValidator());
		when(messageHelper.addAlert(Mockito.anyString())).thenReturn(messageHelper);
		when(messageHelper.addError(Mockito.anyString())).thenReturn(messageHelper);
		when(messageHelper.addConfirmation(Mockito.anyString())).thenReturn(messageHelper);
		validator = new SimpleValidator(defaultValidator, container, messageHelper);
	}
	
	@Test
	public void should_add_confirmations_if_it_has_no_validation_error() {
		String key = "my.confirmation.message";
		validator.validate(new Dog("A big dog name"), name())
					.onSuccessAddConfirmation(key)
					.onErrorRedirectTo(new MockResult());
		
		verify(messageHelper).addConfirmation(key);
	}
	
	@Test
	public void should_call_default_validators_validate() {
		Dog dog = new Dog("A big dog name");
		validator.validate(dog, name());
		verify(defaultValidator).validate(dog);
	}
	
	@Test
	public void should_verify_multiple_validations_with_one_key() {
		validator.validate(0l, and(lessThan(2l), biggerThan(0l)).key(NUMBER_ERROR_KEY));
		verify(messageHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_not_add_errors_if_everyting_goes_fine() {
		validator.validate(1l, and(lessThan(2l), biggerThan(0l)).key(NUMBER_ERROR_KEY));
		verify(messageHelper, never()).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_not_add_two_errors() {
		validator.validate(1l, and(lessThan(0l), biggerThan(2l)).key(NUMBER_ERROR_KEY));
		verify(messageHelper, times(1)).addError(NUMBER_ERROR_KEY);
	}

	@Test
	public void should_validate_two_different_objects() {
		String myDogName = "john";
		validator.validate(1l, and(lessThan(2l), biggerThan(0l)).key(NUMBER_ERROR_KEY))
					.validate(myDogName, matches(myDogName).key(""));
		verify(messageHelper, never()).addError(Mockito.anyString());
	}
	
	@Test
	public void should_validate_two_null_strings() {
		String emptyPasswordKey = "empty.password";
		String emptyNewPasswordKey = "empty.new.password";
		String passwordDoesntMatchesKey = "password.doesnt.matches";
		validator.validate(null, notEmptyNorNull().key(emptyPasswordKey))
				 .validate(null, notEmptyNorNull().key(emptyNewPasswordKey),
								matches(null).key(passwordDoesntMatchesKey));
		verify(messageHelper, times(1)).addError(emptyNewPasswordKey);
		verify(messageHelper, times(1)).addError(emptyPasswordKey);
		verify(messageHelper, times(1)).addError(passwordDoesntMatchesKey);
	}
	
	public static ValidationStrategy<Dog> name(){
		return new ValidationStrategy<Dog>() {
			@Override
			public void addErrors(Dog dog) {
				if(dog.getName().length() < 5) addError("small_name");
			}
		};
	}

	private class Dog{
		private final String name;

		public Dog(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}

}
