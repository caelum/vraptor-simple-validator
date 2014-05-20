package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.getDefaultKey;

public class AndValidationStrategy<T> extends SimpleValidationStrategy<T> {
	
	private final SimpleValidationStrategy<T>[] validations;
	private final FakeStrategy fakeStrategy = new FakeStrategy();

	public AndValidationStrategy(String defaultKey,
			SimpleValidationStrategy<T>[] validations) {
		super(defaultKey, validations);
		this.validations = validations;
	}

	@Override
	public boolean shouldAddError(T obj) {
		for (SimpleValidationStrategy<T> validation : validations) {
			validation.addErrors(obj, fakeStrategy);
		}
		boolean thereAreValidationErrors = fakeStrategy.isInvalid();
		return thereAreValidationErrors;
	}
		
	@Override
	public SimpleValidationStrategy<T> key(String message,
			Object... parameters) {
		super.key(message, parameters);
		for (SimpleValidationStrategy<T> validation : validations) {
			validation.key(message, parameters);
		}
		return this;
	}
	

}
