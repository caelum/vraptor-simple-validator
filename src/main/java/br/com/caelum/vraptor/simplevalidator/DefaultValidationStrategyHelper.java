package br.com.caelum.vraptor.simplevalidator;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class DefaultValidationStrategyHelper implements ValidationStrategyHelper{
	private MessageHelper messages;

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
