package br.com.caelum.vraptor.simplevalidator;

public abstract class SimpleValidationStrategy<T> implements ValidationStrategy<T> {
	
	private String message;
	private Object[] parameters;
	
	public SimpleValidationStrategy<T> key(String message, Object...parameters){
		this.message = message;
		this.parameters = parameters;
		return this;
	}
	
	public void addErrors(T t, ValidationStrategyHelper strategy) {
		verifyPresenceOfKey();
		if(shouldAddError(t)) strategy.addError(message, parameters);
	}
	
	private void verifyPresenceOfKey() {
		if(message == null) throw new IllegalStateException("Please, give me the message.properties key so I can tell you the error. Syntax: validator.validate(obj, strategy().key(\"my.key\"))");
	}

	
	protected abstract boolean shouldAddError(T t);
}
