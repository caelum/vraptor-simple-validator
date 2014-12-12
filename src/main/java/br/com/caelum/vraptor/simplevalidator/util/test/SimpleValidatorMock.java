package br.com.caelum.vraptor.simplevalidator.util.test;

import java.util.ResourceBundle;

import javax.enterprise.inject.Vetoed;

import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.simplevalidator.CustomValidationStrategy;
import br.com.caelum.vraptor.simplevalidator.DefaultMessageHelper;
import br.com.caelum.vraptor.simplevalidator.DefaultValidationStrategyHelper;
import br.com.caelum.vraptor.simplevalidator.MessageFactory;
import br.com.caelum.vraptor.simplevalidator.SimpleValidator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validator;

@Vetoed
public class SimpleValidatorMock extends SimpleValidator {

	private SimpleValidatorMock(Validator validator, Container container, DefaultValidationStrategyHelper strategy) {
		super(validator, container, strategy);
	}
	
	public static SimpleValidatorMock getInstance() {
		MockValidator validator = new MockValidator();
		MessageFactory messageFactory = new MessageFactory(ResourceBundle.getBundle("messages"));
		DefaultMessageHelper defaultMessageHelper = new DefaultMessageHelper(validator, new MockResult(), messageFactory);
		validator = new MockValidator();
		
		return new SimpleValidatorMock(validator, null, new DefaultValidationStrategyHelper(defaultMessageHelper));
	}
	
	public <T> SimpleValidator validate(T t, Class<? extends CustomValidationStrategy<T>> validationStrategy) {
		return this;
	}
	
	public boolean containsMessage(String messageKey, Object... messageParameters) {
		I18nMessage expectedMessage = new I18nMessage("validation", messageKey, messageParameters);
		expectedMessage.setBundle(ResourceBundle.getBundle("messages"));
		for(Message error : getErrors()) {
			if(expectedMessage.getMessage().equals(error.getMessage())) {
				return true;
			}
		}

		return false;
	}

}
