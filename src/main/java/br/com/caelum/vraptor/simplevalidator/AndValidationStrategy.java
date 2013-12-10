package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.Validator;

public class AndValidationStrategy<T> extends DefaultValidationStrategy<T> {

	
	private final DefaultValidationStrategy<T>[] validations;
	private FakeValidator fakeValidator;
	private FakeMessageHelper fakeMessageHelper;

	public AndValidationStrategy(DefaultValidationStrategy<T>[] validations) {
		this.validations = validations;
	}

	@Override
	public boolean shouldAddError(T obj) {
		for (DefaultValidationStrategy<T> validation : validations) {
			validation.addErrors(obj);
		}
		boolean thereAreValidationErrors = fakeValidator.isInvalid() || fakeMessageHelper.isInvalid();
		return thereAreValidationErrors;
	}
		
	@Override
	public DefaultValidationStrategy<T> key(String message,
			Object... parameters) {
		super.key(message, parameters);
		for (DefaultValidationStrategy<T> validation : validations) {
			validation.key(message, parameters);
		}
		return this;
	}
	
	@Override
	protected ValidationStrategy<T> setDependencies(Validator validator, MessageHelper messageHelper) {
		super.setDependencies(validator, messageHelper);
		fakeValidator = new FakeValidator(validator);
		fakeMessageHelper = new FakeMessageHelper();
		for (DefaultValidationStrategy<T> validation : validations) {
			validation.setDependencies(fakeValidator, fakeMessageHelper);
		}
		return this;
	}

}
