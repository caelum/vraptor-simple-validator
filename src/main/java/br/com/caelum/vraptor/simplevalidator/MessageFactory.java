package br.com.caelum.vraptor.simplevalidator;

import java.util.ResourceBundle;

import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Message;

@Component
public class MessageFactory {

	private ResourceBundle bundle;

	public MessageFactory(Localization localization) {
		bundle = localization.getBundle();
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
