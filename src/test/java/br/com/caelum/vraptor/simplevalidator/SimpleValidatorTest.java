package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.and;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategiesTest.ERROR_KEY;
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
	private SimpleValidator dogValidator;
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
		dogValidator = new SimpleValidator(defaultValidator, container, messageHelper);
	}
	
	@Test
	public void should_add_confirmations_if_it_has_no_validation_error() {
		String key = "my.confirmation.message";
		dogValidator.validate(new Dog("A big dog name"), name())
					.onSuccessAddConfirmation(key)
					.onErrorRedirectTo(new MockResult());
		
		verify(messageHelper).addConfirmation(key);
	}
	
	@Test
	public void should_call_default_validators_validate() {
		Dog dog = new Dog("A big dog name");
		dogValidator.validate(dog, name());
		verify(defaultValidator).validate(dog);
	}
	
	@Test
	public void should_verify_multiple_validations_with_one_key() {
		dogValidator.validate(0l, and(lessThan(2l), biggerThan(0l)).key(ERROR_KEY));
		verify(messageHelper).addError(ERROR_KEY);
	}
	
	@Test
	public void should_not_add_errors_if_everyting_goes_fine() {
		dogValidator.validate(1l, and(lessThan(2l), biggerThan(0l)).key(ERROR_KEY));
		verify(messageHelper, never()).addError(ERROR_KEY);
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
