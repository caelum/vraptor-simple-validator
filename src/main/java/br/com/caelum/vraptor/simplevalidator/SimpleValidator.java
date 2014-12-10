package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.strategy.ValidationStrategies.getDefaultKey;
import static br.com.caelum.vraptor.view.Results.page;

import javax.inject.Inject;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.simplevalidator.strategy.CustomValidationStrategy;
import br.com.caelum.vraptor.simplevalidator.strategy.DefaultValidationStrategyHelper;
import br.com.caelum.vraptor.simplevalidator.strategy.ValidationStrategyHelper;
import br.com.caelum.vraptor.validator.Validator;

public class SimpleValidator{
	
	private final Container container;
	private final Validator validator;
	private final DefaultValidationStrategyHelper strategy;

	/**
	 * @deprecated CDI eyes only
	 */
	protected SimpleValidator() {
		this(null, null, null);
	}
	
	@Inject
	public SimpleValidator(Validator validator, Container container, DefaultValidationStrategyHelper strategy) {
		this.validator = validator;
		this.container = container;
		this.strategy = strategy;
	}
	
	public <T> SimpleValidator validate(T t, Class<? extends CustomValidationStrategy<T>> validationStrategy) {
		return validate(t, container.instanceFor(validationStrategy));
	}
	
	public <T> SimpleValidator validate(T t, final CustomValidationStrategy<T>...validationStrategies) {
		return validate(t, new CustomValidationStrategy<T>() {
			@Override
			public void addErrors(T t) {
				for (CustomValidationStrategy<T> validationStrategy : validationStrategies) {
					validationStrategy.addErrors(t);
				}
			}
		});
	}

	public <T> SimpleValidator validate(T t, CustomValidationStrategy<T> validationStrategy) {
		validationStrategy.addErrors(t);
		validator.validate(t);
		return this;
	}
	
	public <T> SimpleValidator validate(T t, SimpleValidationStrategy<T> validationStrategy) {
		validationStrategy.addErrors(t, strategy);
		return this;
	}
	
	@SuppressWarnings ("all")
	public <T> SimpleValidator validate(T t, final SimpleValidationStrategy<T>... validationStrategy) {
		return validate(t, new SimpleValidationStrategy<T>(getDefaultKey("varargs"), validationStrategy) {

			@Override
			public void addErrors(T t, ValidationStrategyHelper strategy) {
				for (SimpleValidationStrategy<T> simpleValidationStrategy : validationStrategy) {
					simpleValidationStrategy.addErrors(t, strategy);
				}
			}
			
			protected boolean shouldAddError(T t) {
				throw new UnsupportedOperationException("This method should never be invocated");
			}
		});
	}
	
	public <T> SimpleValidator onSuccessAddConfirmation(String message, Object...parameters) {
		if(!validator.hasErrors()) strategy.addConfirmation(message, parameters);
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
	
	public <T extends View> T onErrorUse(Class<T> view) {
		return validator.onErrorUse(view);
	}
}
