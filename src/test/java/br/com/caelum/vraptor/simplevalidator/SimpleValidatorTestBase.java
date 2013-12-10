package br.com.caelum.vraptor.simplevalidator;

import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.mockito.Mock;

import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.util.test.MockValidator;

public class SimpleValidatorTestBase {
	SimpleValidator validator;
	MockValidator vraptorValidator;
	@Mock
	Container container;
	@Mock
	DefaultValidationStrategyHelper strategy;
	@Mock
	DefaultValidationStrategyHelper validationStrategyHelper;
	
	@Before
	public void setUp() {
		vraptorValidator = spy(new MockValidator());
		validator = new SimpleValidator(vraptorValidator, container, validationStrategyHelper);
	}

}
