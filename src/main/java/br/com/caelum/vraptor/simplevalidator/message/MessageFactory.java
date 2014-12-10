package br.com.caelum.vraptor.simplevalidator.message;

import java.util.ResourceBundle;

import javax.inject.Inject;

import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Message;

public class MessageFactory {

	private final ResourceBundle bundle;
	
	/**
	 *@deprecated CDI eyes only 
	 */
	public MessageFactory() {
		this(null);
	}
	
	@Inject
	public MessageFactory(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
	public Message i18nError(String message, Object... parameters) {
		return i18nMessage(message, "error", parameters);
	}
	
	public Message i18nAlert(String message, Object... parameters) {
		return i18nMessage(message, "alert", parameters);
	}
	
	public Message i18nConfirmation(String message, Object... parameters) {
		return i18nMessage(message, "confirmation", parameters);
	}

	private I18nMessage i18nMessage(String message, String category, Object... parameters) {
		I18nMessage i18nMessage = new I18nMessage(category, message, parameters);
		i18nMessage.setBundle(bundle);
		return i18nMessage;
	}
	
}
