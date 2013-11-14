package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.Validator;

public abstract class ValidationStrategy<T> {
	private MessageHelper messages;
	private Validator validator;
	
	public abstract void addErrors(T t);
	
	protected ValidationStrategy<T> setDependencies(Validator validator, MessageHelper messageHelper) {
		this.validator = validator;
		this.messages = messageHelper;
		return this;
	}
	
	protected void process(T t) {
		addErrors(t);
		validator.validate(t);
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
