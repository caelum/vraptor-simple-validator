package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.simplevalidator.strategy.ValidationStrategy;
import br.com.caelum.vraptor.simplevalidator.strategy.ValidationStrategyHelper;

public abstract class SimpleValidationStrategy<T> implements ValidationStrategy<T> {
	
	private String messageKey;
	private Object[] parameters;
	
	public SimpleValidationStrategy(String defaultKey, Object...defaultParameters) {
		messageKey = defaultKey;
		parameters = defaultParameters;
	}

	public SimpleValidationStrategy<T> key(String message, Object...parameters){
		this.messageKey = message;
		this.parameters = parameters;
		return this;
	}
	
	public SimpleValidationStrategy<T> parameters(Object...parameters){
		this.parameters = parameters;
		return this;
	}
	
	public void addErrors(T t, ValidationStrategyHelper strategy) {
		if(shouldAddError(t)) strategy.addError(messageKey, parameters);
	}
	
	protected abstract boolean shouldAddError(T t);
}
