package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.Validator;

public class AndValidationStrategy<T> extends AbstractValidationStrategy<T> {

	
	private final AbstractValidationStrategy<T>[] validations;
	private FakeValidator fakeValidator;
	private FakeMessageHelper fakeMessageHelper;

	public AndValidationStrategy(AbstractValidationStrategy<T>[] validations) {
		this.validations = validations;
	}

	@Override
	public boolean shouldAddError(T obj) {
		for (AbstractValidationStrategy<T> validation : validations) {
			validation.addErrors(obj);
		}
		boolean thereAreValidationErrors = fakeValidator.isInvalid() || fakeMessageHelper.isInvalid();
		return thereAreValidationErrors;
	}
		
	@Override
	public AbstractValidationStrategy<T> key(String message,
			Object... parameters) {
		super.key(message, parameters);
		for (AbstractValidationStrategy<T> validation : validations) {
			validation.key(message, parameters);
		}
		return this;
	}
	
	@Override
	protected ValidationStrategy<T> setDependencies(Validator validator, MessageHelper messageHelper) {
		super.setDependencies(validator, messageHelper);
		fakeValidator = new FakeValidator(validator);
		fakeMessageHelper = new FakeMessageHelper();
		for (AbstractValidationStrategy<T> validation : validations) {
			validation.setDependencies(fakeValidator, fakeMessageHelper);
		}
		return this;
	}

}
