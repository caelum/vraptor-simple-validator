package br.com.caelum.vraptor.simplevalidator;

import java.util.Collection;
import java.util.List;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validations;

public class FakeValidator implements Validator {

	private Validator validator;

	public FakeValidator(Validator validator) {
		this.validator = validator;
	}

	private boolean invalid;

	@Override
	public void add(Message message) {
		validator.add(message);
		invalid = true;
	}
	
	public boolean isInvalid() {
		return invalid;
	}

	@Override
	public void checking(Validations rules) {
		validator.checking(rules);
	}

	@Override
	public void validate(Object object, Class<?>... groups) {
		validator.validate(object, groups);
	}

	@Override
	public void validateProperties(Object object, String... properties) {
		validator.validateProperties(object, properties);
	}

	@Override
	public <T extends View> T onErrorUse(Class<T> view) {
		return validator.onErrorForwardTo(view);
	}

	@Override
	public void addAll(Collection<? extends Message> message) {
		this.invalid = true;
		validator.addAll(message);
	}

	@Override
	public List<Message> getErrors() {
		return validator.getErrors();
	}

	@Override
	public boolean hasErrors() {
		return validator.hasErrors();
	}

	@Override
	public <T> T onErrorForwardTo(Class<T> controller) {
		return validator.onErrorForwardTo(controller);
	}

	@Override
	public <T> T onErrorForwardTo(T controller) {
		return validator.onErrorForwardTo(controller);
	}

	@Override
	public <T> T onErrorRedirectTo(Class<T> controller) {
		return validator.onErrorRedirectTo(controller);
	}

	@Override
	public <T> T onErrorRedirectTo(T controller) {
		return validator.onErrorRedirectTo(controller);
	}

	@Override
	public <T> T onErrorUsePageOf(Class<T> controller) {
		return validator.onErrorUsePageOf(controller);
	}

	@Override
	public <T> T onErrorUsePageOf(T controller) {
		return validator.onErrorUsePageOf(controller);
	}

	@Override
	public void onErrorSendBadRequest() {
		validator.onErrorSendBadRequest();
	}
}