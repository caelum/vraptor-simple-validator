package br.com.caelum.vraptor.simplevalidator;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.Message;

@Component
public class MessageHelper {

	protected static final String CONFIRMATIONS_KEY = "confirmations";
	protected static final String ALERTS_KEY = "alerts";
	protected static final String ERRORS_KEY = "errors";
	
	private Result result;
	private MessageFactory factory;
	private Validator validator;
	private List<Message> confirmations = new ArrayList<Message>();
	private List<Message> alerts = new ArrayList<Message>();
	private List<Message> errors = new ArrayList<Message>();

	public MessageHelper(Validator validator, Result result, MessageFactory factory) {
		this.validator = validator;
		this.result = result;
		this.factory = factory;
	}

	public MessageHelper addConfirmation(String message, Object...parameters) {
		confirmations.add(factory.i18nConfirmation(message, parameters));
		return this;
	}

	public MessageHelper addAlert(String message, Object...parameters) {
		alerts.add(factory.i18nAlert(message, parameters));
		return this;
	}
	
	public MessageHelper addError(String message, Object...parameters) {
		errors.add(factory.i18nError(message, parameters));
		return this;
	}
	
	public void onResult() {
		result.include(CONFIRMATIONS_KEY, copy(confirmations));
		result.include(ALERTS_KEY, copy(alerts));
		result.include(ERRORS_KEY, copy(errors));
		clean();
	}

	public void onValidator() {
		validator.addAll(copy(confirmations));
		validator.addAll(copy(alerts));
		validator.addAll(copy(errors));
		clean();
	}
	
	private List<Message> copy(List<Message> list) {
		return new ArrayList<>(list);
	}
	
	private void clean() {
		confirmations.clear();
		alerts.clear();
		errors.clear();
	}
	
}
