package br.com.caelum.vraptor.simplevalidator;

import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.mockito.Mock;

import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.simplevalidator.strategy.DefaultValidationStrategyHelper;
import br.com.caelum.vraptor.util.test.MockValidator;

public class SimpleValidatorTestBase {
	
	public SimpleValidator validator;
	public MockValidator vraptorValidator;
	@Mock
	public Container container;
	@Mock
	public DefaultValidationStrategyHelper strategy;
	@Mock
	public DefaultValidationStrategyHelper validationStrategyHelper;
	
	@Before
	public void setUp() {
		vraptorValidator = spy(new MockValidator());
		validator = new SimpleValidator(vraptorValidator, container, validationStrategyHelper);
	}

}
