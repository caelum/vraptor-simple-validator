package br.com.caelum.vraptor.simplevalidator;

public abstract class DefaultValidationStrategy<T> extends ValidationStrategy<T> {
	
	private String message;
	private Object[] parameters;

	public DefaultValidationStrategy<T> key(String message, Object...parameters){
		this.message = message;
		this.parameters = parameters;
		return this;
	}
	
	protected void addError() {
		if(message == null) throw new IllegalStateException("Please, give me the message.properties key so I can tell you the error. Syntax: validator.validate(obj, strategy().key(\"my.key\"))");
		super.addError(message, parameters);
	}
	
	protected void addAlert() {
		super.addAlert(message, parameters);
	}
	
}
