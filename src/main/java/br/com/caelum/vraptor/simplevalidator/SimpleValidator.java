package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.view.Results.page;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;

@Component
public class SimpleValidator{
	
	private final Container container;
	private Validator validator;
	private DefaultMessageHelper messageHelper;
	private ValidationStrategy<?> currentStrategy;

	public SimpleValidator(Validator validator, Container container, DefaultMessageHelper messageHelper) {
		this.validator = validator;
		this.container = container;
		this.messageHelper = messageHelper;
	}
	
	public <T> SimpleValidator validate(T t, Class<? extends ValidationStrategy<T>> validationStrategy) {
		return validate(t, container.instanceFor(validationStrategy));
	}
	
	public <T> SimpleValidator validate(T t, final ValidationStrategy<T>...validationStrategies) {
		return validate(t, new ValidationStrategy<T>() {
			@Override
			public void addErrors(T t) {
				for (ValidationStrategy<T> validationStrategy : validationStrategies) {
					validationStrategy.setDependencies(validator, messageHelper);
					validationStrategy.addErrors(t);
				}
			}
		});
	}

	public <T> SimpleValidator validate(T t, ValidationStrategy<T> validationStrategy) {
		currentStrategy = validationStrategy;
		validationStrategy.setDependencies(validator, messageHelper).process(t);
		return this;
	}
	
	public <T> SimpleValidator onSuccessAddConfirmation(String message, Object...parameters) {
		if(!validator.hasErrors()) currentStrategy.addConfirmation(message, parameters);
		return this;
	}
	
	public <C> C onErrorRedirectTo(Class<C> controller) {
		return validator.onErrorRedirectTo(controller);
	}

	public <C> C onErrorRedirectTo(C controller) {
		return validator.onErrorRedirectTo(controller);
	}
	
	public void onErrorRedirectTo(String path) {
		validator.onErrorUse(page()).redirectTo(path);
	}
	
	public void onErrorForwardTo(String path) {
		validator.onErrorUse(page()).forwardTo(path);
	}
	
	public <C> C onErrorForwardTo(Class<C> controller) {
		return validator.onErrorForwardTo(controller);
	}
	
	public <C> C onErrorForwardTo(C controller) {
		return validator.onErrorForwardTo(controller);
	}

	public <C> C onErrorUsePageOf(C controller) {
		return validator.onErrorUsePageOf(controller);
	}
}
