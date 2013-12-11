package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.and;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.matches;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmptyNorNull;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategiesTest.ERROR_KEY;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.util.test.MockResult;

@RunWith(MockitoJUnitRunner.class)
public class SimpleValidatorTest extends SimpleValidatorTestBase{
	
	@Before
	public void setUp() {
		super.setUp();
		when(container.instanceFor(DogValidator.class)).thenReturn(new DogValidator(strategy));
	}
	
	@Test
	public void should_add_confirmations_if_it_has_no_validation_error() {
		String key = "my.confirmation.message";
		validator.validate(new Dog("A big dog name"), DogValidator.class)
					.onSuccessAddConfirmation(key)
					.onErrorRedirectTo(new MockResult());
		
		verify(validationStrategyHelper).addConfirmation(key);
	}
	
	@Test
	public void should_call_vraptor_validators_validate() {
		Dog dog = new Dog("A big dog name");
		validator.validate(dog, DogValidator.class);
		verify(vraptorValidator).validate(dog);
	}

	@Test
	public void should_validate_two_different_objects() {
		String myDogName = "john";
		validator.validate(1l, and(lessThan(2l), biggerThan(0l)).key(ERROR_KEY))
					.validate(myDogName, matches(myDogName).key(""));
		verify(validationStrategyHelper, never()).addError(Mockito.anyString());
	}
	
	@Test
	public void should_validate_two_null_strings() {
		String emptyPasswordKey = "empty.password";
		String emptyNewPasswordKey = "empty.new.password";
		String passwordDoesntMatchesKey = "password.doesnt.matches";
		validator.validate(null, notEmptyNorNull().key(emptyPasswordKey))
				 .validate(null, notEmptyNorNull().key(emptyNewPasswordKey),
								matches(null).key(passwordDoesntMatchesKey));
		verify(validationStrategyHelper, times(1)).addError(emptyNewPasswordKey);
		verify(validationStrategyHelper, times(1)).addError(emptyPasswordKey);
		verify(validationStrategyHelper, times(1)).addError(passwordDoesntMatchesKey);
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
	
	private class DogValidator implements CustomValidationStrategy<Dog>{
		private DefaultValidationStrategyHelper strategy;

		public DogValidator(DefaultValidationStrategyHelper strategy) {
			this.strategy = strategy;
		}
		
		@Override
		public void addErrors(Dog dog) {
			if(dog.getName().length() < 5) strategy.addError("small_name");
		}
		
	}

}
