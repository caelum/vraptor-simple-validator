package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.Validator;

public class AndValidationStrategy<T> extends DefaultValidationStrategy<T> {

	
	private DefaultValidationStrategy<T>[] validations;

	public AndValidationStrategy(DefaultValidationStrategy<T>[] validations) {
		this.validations = validations;
	}

	@Override
	public void addErrors(T obj) {
		for (DefaultValidationStrategy<T> validation : validations) {
			validation.addErrors(obj);
		}
	}
	
	@Override
	public DefaultValidationStrategy<T> key(String message,
			Object... parameters) {
		for (DefaultValidationStrategy<T> validation : validations) {
			validation.key(message, parameters);
		}
		return this;
	}
	
	@Override
	protected ValidationStrategy<T> setDependencies(Validator validator, MessageHelper messageHelper) {
		super.setDependencies(validator, messageHelper);
		for (DefaultValidationStrategy<T> validation : validations) {
			validation.setDependencies(validator, messageHelper);
		}
		return this;
	}

}
