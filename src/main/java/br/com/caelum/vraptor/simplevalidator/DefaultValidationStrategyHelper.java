package br.com.caelum.vraptor.simplevalidator;

import javax.inject.Inject;


public class DefaultValidationStrategyHelper implements ValidationStrategyHelper{
	private final MessageHelper messages;

	/**
	 * @deprecated CDI eyes only
	 */
	public DefaultValidationStrategyHelper() {
		this(null);
	}
	
	@Inject
	public DefaultValidationStrategyHelper(MessageHelper messages) {
		this.messages = messages;
	}
	
	public void addError(String message, Object...parameters) {
		messages.addError(message, parameters).onValidator();
	}
	
	public void addAlert(String message, Object...parameters) {
		messages.addAlert(message, parameters).onValidator();
	}
	
	public void addConfirmation(String message, Object...parameters) {
		messages.addConfirmation(message, parameters).onResult();		
	}

}
