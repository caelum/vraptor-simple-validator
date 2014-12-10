package br.com.caelum.vraptor.simplevalidator.strategy;

import br.com.caelum.vraptor.simplevalidator.SimpleValidationStrategy;


public class AndValidationStrategy<T> extends SimpleValidationStrategy<T> {
	
	private final SimpleValidationStrategy<T>[] validations;

	@SuppressWarnings ("all")
	public AndValidationStrategy(SimpleValidationStrategy<T>[] validations) {
		super("", validations);
		this.validations = validations;
	}

	@Override
	public boolean shouldAddError(T obj) {
		return false;
	}
	
	@Override
	public void addErrors(T obj, ValidationStrategyHelper strategy) {
		for (SimpleValidationStrategy<T> validation : validations) {
			validation.addErrors(obj, strategy);
		}
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
