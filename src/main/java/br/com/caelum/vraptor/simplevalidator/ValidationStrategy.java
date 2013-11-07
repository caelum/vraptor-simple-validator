package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.Validator;

public abstract class ValidationStrategy<T> {
	private MessageHelper messages;
	
	public abstract void addErrors(T t);
	
	public ValidationStrategy<T> process(T t, Validator validator, MessageHelper messageHelper) {
		this.messages = messageHelper;
		addErrors(t);
		validator.validate(t);
		return this;
	}
	
	protected void addError(String message, Object...parameters) {
		messages.addError(message, parameters).onValidator();
	}
	
	protected void addAlert(String message, Object...parameters) {
		messages.addAlert(message, parameters).onValidator();
	}
	
	public void addConfirmation(String message, Object[] parameters) {
		messages.addConfirmation(message, parameters).onResult();		
	}
}
